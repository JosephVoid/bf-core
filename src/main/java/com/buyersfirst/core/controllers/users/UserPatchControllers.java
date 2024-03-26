package com.buyersfirst.core.controllers.users;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.buyersfirst.core.interfaces.UserModify;
import com.buyersfirst.core.models.Users;
import com.buyersfirst.core.models.UsersRepository;
import com.buyersfirst.core.services.TokenParser;

import jakarta.security.auth.message.AuthException;

@RestController
@RequestMapping(path = "/users")
public class UserPatchControllers {

    @Autowired
    TokenParser tokenParser;
    @Autowired
    UsersRepository usersRepository;

    @PatchMapping(path = "/")
    @ResponseBody
    Users updateUsers(@RequestHeader("Authorization") String auth, @RequestBody UserModify body) {
        try {
            String userId = tokenParser.getUserId(auth);

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
            /* Update the user */
            usersRepository.updateUsers(
                    userId,
                    body.first_name,
                    body.last_name,
                    body.email,
                    body.password == null ? null : bCryptPasswordEncoder.encode(body.password),
                    body.description,
                    body.phone,
                    body.picture);
            Users responseUser = usersRepository.findById(UUID.fromString(userId)).get();
            responseUser.setPassword("");
            return responseUser;
        } catch (AuthException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Auth Header Issue");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
}
