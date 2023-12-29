package com.buyersfirst.core.controllers.desires;

import java.sql.Timestamp;
import java.util.ArrayList;

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

import com.buyersfirst.core.interfaces.CreateDesiresRqB;
import com.buyersfirst.core.models.DesireTags;
import com.buyersfirst.core.models.DesireTagsRepository;
import com.buyersfirst.core.models.Desires;
import com.buyersfirst.core.models.DesiresRepository;
import com.buyersfirst.core.services.TokenParser;

@RestController
@RequestMapping(path = "/desires", method = RequestMethod.POST)
public class DesirePostControllers {
    @Autowired
    TokenParser tokenParser;
    @Autowired
    DesiresRepository desiresRepository;
    @Autowired
    DesireTagsRepository desireTagsRepository;

    @PostMapping(path = "/create")
    public @ResponseBody Desires createDesires (@RequestHeader("Authorization") String auth, @RequestBody CreateDesiresRqB request) {
        if (request.title == null || request.description == null || request.price == null || request.tags_id == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Input");
        try {
            Integer userId = tokenParser.getUserId(auth);
            /* Validate input */
            if (request.title.length() > 100 || request.description.length() > 500)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input size limit exceed");
            
            /* Save desire */
            Desires desire = new Desires();
            desire.setCreated(new Timestamp(System.currentTimeMillis()));
            desire.setTitle(request.title);
            desire.setDescription(request.description);
            desire.setDesiredPrice(request.price);
            desire.setIsClosed(0);
            desire.setOwnerId(userId);
            if (request.picture != null)
                desire.setPicture(request.picture);
            Desires savedDesire =  desiresRepository.save(desire);
            
            /* Save Desire Tag relation */
            ArrayList<DesireTags> desireTags = new ArrayList<DesireTags>();
            for (int i = 0; i < request.tags_id.length; i++) {
                desireTags.add(new DesireTags(request.tags_id[i], savedDesire.getId()));
            }
            desireTagsRepository.saveAll(desireTags);
            
            return savedDesire;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error or Header Missing");
        }
    }

    @PostMapping(path = "/{id}/recreate")
    public @ResponseBody Desires reCreateDesires (@PathVariable Integer id) {
        return new Desires();
    }

    @PostMapping(path = "/{desire}/close")
    public @ResponseBody Desires closeDesires (@PathVariable Integer desire) {
        return new Desires();
    }

    @PostMapping(path = "/want/{desire}")
    public @ResponseBody Desires wantDesires (@PathVariable Integer desire) {
        return new Desires();
    }

    @PostMapping(path = "/not-want/{desire}")
    public @ResponseBody Desires notWantDesires (@PathVariable Integer desire) {
        return new Desires();
    }
}
