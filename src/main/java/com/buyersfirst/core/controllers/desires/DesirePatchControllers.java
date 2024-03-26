package com.buyersfirst.core.controllers.desires;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.buyersfirst.core.interfaces.CreateDesiresRqB;
import com.buyersfirst.core.models.DesireTags;
import com.buyersfirst.core.models.DesireTagsRepository;
import com.buyersfirst.core.models.Desires;
import com.buyersfirst.core.models.DesiresRepository;
import com.buyersfirst.core.services.TokenParser;

import jakarta.security.auth.message.AuthException;

@RestController
@RequestMapping(path = "/desires")
public class DesirePatchControllers {

    @Autowired
    TokenParser tokenParser;
    @Autowired
    DesiresRepository desiresRepository;
    @Autowired
    DesireTagsRepository desireTagsRepository;

    @PatchMapping(path = "/{id}")
    @ResponseBody
    Desires updateDesire(@RequestHeader("Authorization") String auth, @PathVariable String id,
            @RequestBody CreateDesiresRqB body) {
        try {
            String userId = tokenParser.getUserId(auth);
            /* Check if user owns the desire */
            List<String> desires = desiresRepository.listDesiresByOwner(userId);
            if (!desires.contains(id))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not owner");
            /* Update the desire */
            desiresRepository.updateDesire(id, body.title, body.description, body.price, body.picture);
            /* Update the desire tags */
            if (body.tags_id != null && body.tags_id.length != 0) {
                desireTagsRepository.deleteByDesireId(id);
                ArrayList<DesireTags> dTags = new ArrayList<DesireTags>();
                for (String tag : body.tags_id) {
                    dTags.add(new DesireTags(tag, id));
                }
                desireTagsRepository.saveAll(dTags);
            }
            return desiresRepository.findById(UUID.fromString(id)).get();
        } catch (AuthException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Auth Header Issue");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
}
