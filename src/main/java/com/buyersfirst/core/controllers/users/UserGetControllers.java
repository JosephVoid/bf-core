package com.buyersfirst.core.controllers.users;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.buyersfirst.core.interfaces.UserRsp;
import com.buyersfirst.core.models.BidsRepository;
import com.buyersfirst.core.models.DesiresRepository;
import com.buyersfirst.core.models.Users;
import com.buyersfirst.core.models.UsersRepository;

@RestController
@RequestMapping(path = "/users")
public class UserGetControllers {
    
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    DesiresRepository desiresRepository;
    @Autowired
    BidsRepository bidsRepository;

    @GetMapping(path = "/{id}")
    @ResponseBody UserRsp getUser (@PathVariable Integer id) {
        String[][] dbResponse = usersRepository.findOneById(id);
        if (dbResponse.length < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
        Users user = new Users(
            Integer.parseInt(dbResponse[0][0]),
            dbResponse[0][1], 
            dbResponse[0][2], 
            dbResponse[0][3], 
            null, 
            dbResponse[0][5], 
            dbResponse[0][6], 
            dbResponse[0][7], 
            Timestamp.valueOf(dbResponse[0][8]));

        List<Integer> userDesires = desiresRepository.listDesiresByOwner(id);
        List<Integer> userBids = bidsRepository.listBidsByOwner(id);

        return new UserRsp(user, userDesires, userBids);
    }
}
