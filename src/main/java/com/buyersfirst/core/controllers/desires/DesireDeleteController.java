package com.buyersfirst.core.controllers.desires;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.buyersfirst.core.models.DeletedDesires;
import com.buyersfirst.core.models.DeletedDesiresRepository;
import com.buyersfirst.core.models.DesireTagsRepository;
import com.buyersfirst.core.models.Desires;
import com.buyersfirst.core.models.DesiresRepository;
import com.buyersfirst.core.services.TokenParser;

import jakarta.security.auth.message.AuthException;

@RestController
@RequestMapping(path = "/desires")
public class DesireDeleteController {

    @Autowired
    TokenParser tokenParser;
    @Autowired
    DesiresRepository desiresRepository;
    @Autowired
    DeletedDesiresRepository deletedDesiresRepository;
    @Autowired
    DesireTagsRepository desireTagsRepository;

    @DeleteMapping(path = "/{id}")
    @ResponseBody
    Desires deleteDesire(@RequestHeader("Authorization") String auth, @PathVariable String id) {
        try {
            String userId = tokenParser.getUserId(auth);
            /* Check if the desire is there */
            Optional<Desires> desire = desiresRepository.findById(UUID.fromString(id));
            if (!desire.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Desire does not exist");
            /* Check if the user is the owner */
            if (desire.get().getOwnerId() != userId)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't delete desire");
            /* Save desire to deleted desires */
            deletedDesiresRepository.save(new DeletedDesires(desire.get()));
            /* Delete the desire */
            desiresRepository.deleteById(desire.get().getId());
            /* Delete the desire from the tags table */
            desireTagsRepository.deleteByDesireId(id);

            return desire.get();

        } catch (AuthException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Auth Header Issue");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
}
