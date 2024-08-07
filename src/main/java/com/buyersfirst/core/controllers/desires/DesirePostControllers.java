package com.buyersfirst.core.controllers.desires;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.buyersfirst.core.dto.CreateDesiresRqB;
import com.buyersfirst.core.models.DesireTags;
import com.buyersfirst.core.models.DesireTagsRepository;
import com.buyersfirst.core.models.Desires;
import com.buyersfirst.core.models.DesiresRepository;
import com.buyersfirst.core.models.UserWantsRepository;
import com.buyersfirst.core.services.AlertUsers;
import com.buyersfirst.core.services.TokenParser;

import jakarta.security.auth.message.AuthException;

@RestController
@RequestMapping(path = "/desires", method = RequestMethod.POST)
public class DesirePostControllers {
    @Autowired
    TokenParser tokenParser;
    @Autowired
    DesiresRepository desiresRepository;
    @Autowired
    DesireTagsRepository desireTagsRepository;
    @Autowired
    UserWantsRepository userWantsRepository;
    @Autowired
    AlertUsers alertUsers;

    @PostMapping(path = "/create")
    public @ResponseBody Desires createDesires(@RequestHeader("Authorization") String auth,
            @RequestBody CreateDesiresRqB request) {
        if (request.title == null || request.description == null || request.metric == null || request.minPrice == null
                || request.maxPrice == null || request.tags_id == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Input");
        try {
            String userId = tokenParser.getUserId(auth);
            /* Validate input */
            if (request.title.length() > 100 || request.description.length() > 500)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input size limit exceed");

            /* Save desire */
            Desires desire = new Desires();
            desire.setCreated(new Timestamp(System.currentTimeMillis()));
            desire.setTitle(request.title);
            desire.setDescription(request.description);
            desire.setDesiredPrice((request.maxPrice + request.minPrice) / 2);
            desire.setMinPrice(request.minPrice);
            desire.setMaxPrice(request.maxPrice);
            desire.setMetric(request.metric);
            desire.setIsClosed(0);
            desire.setOwnerId(userId);
            if (request.picture != null)
                desire.setPicture(request.picture);
            Desires savedDesire = desiresRepository.save(desire);

            /* Save Desire Tag relation */
            ArrayList<DesireTags> desireTags = new ArrayList<DesireTags>();
            for (int i = 0; i < request.tags_id.length; i++) {
                desireTags.add(new DesireTags(request.tags_id[i], savedDesire.getId().toString()));
            }
            desireTagsRepository.saveAll(desireTags);

            alertUsers.alertForTags(savedDesire.getId().toString(), savedDesire.getTitle(), request.tags_id);
            return savedDesire;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error or Header Missing");
        }
    }

    @PostMapping(path = "/{id}/recreate")
    public @ResponseBody Desires reCreateDesires(@RequestHeader("Authorization") String auth,
            @PathVariable String id) {
        try {
            String userId = tokenParser.getUserId(auth);
            /* Get the desire to recreate from */
            Optional<Desires> oldDesire = desiresRepository.findById(UUID.fromString(id));
            if (!oldDesire.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Desire does not exist");
            /* Close the previous desire */
            desiresRepository.UpdateIsClosedStatus(oldDesire.get().getId(), 1);
            /* Save desire */
            Desires desire = new Desires();
            desire.setCreated(new Timestamp(System.currentTimeMillis()));
            desire.setTitle(
                    oldDesire.get().getTitle().contains("[Repost]") ? oldDesire.get().getTitle()
                            : "[Repost] " + oldDesire.get().getTitle());
            desire.setDescription(oldDesire.get().getDescription());
            desire.setDesiredPrice(oldDesire.get().getDesiredPrice());
            desire.setIsClosed(0);
            desire.setOwnerId(userId);
            desire.setPicture(oldDesire.get().getPicture());

            Desires savedDesire = desiresRepository.save(desire);

            /* Save Desire Tag relation */
            ArrayList<DesireTags> desireTags = new ArrayList<DesireTags>();
            List<DesireTags> oldDesireTags = desireTagsRepository.findByDesireId(oldDesire.get().getId().toString());
            for (int i = 0; i < oldDesireTags.size(); i++) {
                desireTags.add(new DesireTags(oldDesireTags.get(i).getTagId(), savedDesire.getId().toString()));
            }
            desireTagsRepository.saveAll(desireTags);

            return savedDesire;

        } catch (AuthException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Auth Header Issue");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error");
        }
    }

    @PostMapping(path = "/{id}/close")
    public @ResponseBody Desires closeDesires(@RequestHeader("Authorization") String auth, @PathVariable String id) {
        try {
            tokenParser.getUserId(auth);
            /* Check if the desire is there */
            System.out.println(UUID.fromString(id).toString());
            Optional<Desires> desire = desiresRepository.findById(UUID.fromString(id));
            if (!desire.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Desire does not exist");
            /* Close the desire if not closed already */
            if (desire.get().getIsClosed() == 0)
                desiresRepository.UpdateIsClosedStatus(UUID.fromString(id), 1);
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Desire already closed");
            return desiresRepository.findById(UUID.fromString(id)).get();

        } catch (AuthException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Auth Header Issue");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error");
        }
    }

    @PostMapping(path = "/want/{id}")
    public @ResponseBody Desires wantDesires(@RequestHeader("Authorization") String auth, @PathVariable String id) {
        try {
            String userId = tokenParser.getUserId(auth);
            /* Check if the desire is there */
            Optional<Desires> desire = desiresRepository.findById(UUID.fromString(id));
            if (!desire.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Desire does not exist");
            /* Check if there is a Want before */
            if (userWantsRepository.findByDesireUserId(id, userId).size() > 0)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Desire already wanted by user");
            /* Check if the user is not the owner of the desire */
            if (desire.get().getOwnerId().equals(userId))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't want your own desire");
            /* Want the desire */
            userWantsRepository.addUserWants(id, userId, new Timestamp(System.currentTimeMillis()));

            return desire.get();

        } catch (AuthException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Auth Header Issue");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error");
        }
    }

    @PostMapping(path = "/not-want/{id}")
    public @ResponseBody Desires notWantDesires(@RequestHeader("Authorization") String auth, @PathVariable String id) {
        try {
            String userId = tokenParser.getUserId(auth);
            /* Check if the desire is there */
            Optional<Desires> desire = desiresRepository.findById(UUID.fromString(id));
            if (!desire.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Desire does not exist");
            /* Check if there is a Want before */
            if (userWantsRepository.findByDesireUserId(id, userId).size() == 0)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Desire was never wanted by user before");
            /* Want the desire */
            userWantsRepository.removeUserWants(id, userId);

            return desire.get();

        } catch (AuthException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Auth Header Issue");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error");
        }
    }
}
