package com.buyersfirst.core.controllers.desires;

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
import com.buyersfirst.core.models.Desires;
import com.buyersfirst.core.services.TokenParser;

@RestController
@RequestMapping(path = "/desires", method = RequestMethod.POST)
public class DesirePostControllers {
    @Autowired
    TokenParser tokenParser;

    @PostMapping(path = "/create")
    public @ResponseBody Desires createDesires (@RequestHeader("Authorization") String auth, @RequestBody CreateDesiresRqB request) {
        if (request.title == null || request.description == null || request.price == null || request.tags_id == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Input");
        try {
            Integer userId = tokenParser.getUserId(auth);
            
            // TODO: Implement logic

            return new Desires();

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing Header");
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
