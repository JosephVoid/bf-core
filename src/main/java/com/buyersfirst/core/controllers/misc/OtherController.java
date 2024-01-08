package com.buyersfirst.core.controllers.misc;

import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
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

    @PostMapping(path = "/view/{type}/{itemId}")
    void viewItem (@RequestHeader("Authorization") String auth, @PathVariable String type, @PathVariable Integer itemId) {
        try {
            Integer userId = tokenParser.getUserId(auth);
            if (!type.equals("desire") && !type.equals("bid"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Input");
            viewsRepository.save(
                new Views(
                    userId, 
                    type.equals("desire") ? itemId : null, 
                    type.equals("bid") ? itemId : null, 
                    new Timestamp(System.currentTimeMillis()))
            );

        } catch (AuthException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Auth Header Issue");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
}
