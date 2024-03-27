package com.buyersfirst.core.controllers.misc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.buyersfirst.core.dto.SetAlert;
import com.buyersfirst.core.models.AcceptedBidsRepository;
import com.buyersfirst.core.models.NotifyTags;
import com.buyersfirst.core.models.NotifyTagsRepository;
import com.buyersfirst.core.models.Tags;
import com.buyersfirst.core.models.TagsRepository;
import com.buyersfirst.core.models.UserWantsRepository;
import com.buyersfirst.core.models.Users;
import com.buyersfirst.core.models.UsersRepository;
import com.buyersfirst.core.models.Views;
import com.buyersfirst.core.models.ViewsRepository;
import com.buyersfirst.core.services.TokenParser;
import jakarta.security.auth.message.AuthException;

@RestController
public class OtherController {

    @Autowired
    ViewsRepository viewsRepository;
    @Autowired
    TokenParser tokenParser;
    @Autowired
    NotifyTagsRepository notifyTagsRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    TagsRepository tagsRepository;
    @Autowired
    AcceptedBidsRepository acceptedBidsRepository;
    @Autowired
    UserWantsRepository userWantsRepository;

    @PostMapping(path = "/view/{type}/{itemId}")
    void viewItem(@RequestHeader("Authorization") String auth, @PathVariable String type,
            @PathVariable String itemId) {
        try {
            String userId = tokenParser.getUserId(auth);
            if (!type.equals("desire") && !type.equals("bid"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Input");
            viewsRepository.save(
                    new Views(
                            userId,
                            type.equals("desire") ? itemId : null,
                            type.equals("bid") ? itemId : null,
                            new Timestamp(System.currentTimeMillis())));

        } catch (AuthException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Auth Header Issue");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @PostMapping(path = "/set-alert")
    void setAlert(@RequestHeader("Authorization") String auth, @RequestBody SetAlert body) {
        try {
            String userId = tokenParser.getUserId(auth);

            if (body.tag_ids.length > 3)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "More than 3 tags not allowed");

            Users alertedUser = usersRepository.findById(UUID.fromString(userId)).get();
            notifyTagsRepository.deleteNotifyTags(userId);

            List<NotifyTags> notifTags = new ArrayList<NotifyTags>();

            for (String tagId : body.tag_ids) {
                notifTags.add(new NotifyTags(tagId, userId, alertedUser.getEmail(), alertedUser.getPhone()));
            }

            notifyTagsRepository.saveAll(notifTags);
        } catch (AuthException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Auth Header Issue");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @GetMapping(path = "/tags")
    List<Tags> getTags() {
        try {
            return (List<Tags>) tagsRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @GetMapping(path = "/activity/{id}")
    List<String> getUserActivity(@RequestParam String type, @PathVariable String id) {
        switch (type) {
            case "wanted":
                return userWantsRepository.findWantsByUser(id);
            case "accepted":
                return acceptedBidsRepository.findAcceptedBidByUser(id);
            case "viewed-desire":
                return viewsRepository.findDesireViewsByUser(id);
            case "viewed-bid":
                return viewsRepository.findBidViewsByUser(id);
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Type");
        }
    }
}
