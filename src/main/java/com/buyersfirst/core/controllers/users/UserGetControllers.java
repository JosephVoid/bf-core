package com.buyersfirst.core.controllers.users;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.buyersfirst.core.dto.BidResponse;
import com.buyersfirst.core.dto.DesireListRsp;
import com.buyersfirst.core.dto.UserRsp;
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
    @ResponseBody
    UserRsp getUser(@PathVariable String id) {
        String[][] dbResponse = usersRepository.findOneById(id);
        if (dbResponse.length < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
        Users user = new Users(
                UUID.fromString(dbResponse[0][0]),
                dbResponse[0][1],
                dbResponse[0][2],
                dbResponse[0][3],
                null,
                dbResponse[0][5],
                dbResponse[0][6],
                dbResponse[0][7],
                Timestamp.valueOf(dbResponse[0][8]));

        List<String> userDesires = desiresRepository.listDesiresByOwner(id);
        List<String> userBids = bidsRepository.listBidsByOwner(id);

        return new UserRsp(user, userDesires, userBids);
    }

    @GetMapping(path = "/desires/{id}")
    List<DesireListRsp> getDesiresByUserId(@PathVariable String id) {
        try {
            if (!usersRepository.findById(UUID.fromString(id)).isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
            /* From DB */
            String[][] dbResponse = desiresRepository.desiresByUser(id);
            /* Empty ArrayList for responding */
            ArrayList<DesireListRsp> desires = new ArrayList<DesireListRsp>();
            /*
             * Empty ArrayList to store the created desires id, used to remove duplicate
             * rows
             */
            ArrayList<String> createdDesires = new ArrayList<String>();
            /* Loop through the 2*2 array for response */
            for (int i = 0; i < dbResponse.length; i++) {
                final String[] Row = dbResponse[i];
                /* If the desire is already created, just append the tag name the desire */
                if (!createdDesires.contains(Row[0])) {
                    DesireListRsp dsr = new DesireListRsp(
                            Row[0],
                            Row[3],
                            Row[4],
                            Row[1] + " " + Row[2],
                            "",
                            Double.parseDouble(Row[5]),
                            Integer.parseInt(Row[12]),
                            Integer.parseInt(Row[11]),
                            Integer.parseInt(Row[10]),
                            Boolean.parseBoolean(Row[8]),
                            Row[6],
                            Timestamp.valueOf(Row[7]),
                            // For tags
                            new ArrayList<String>(),
                            Double.parseDouble(Row[14]),
                            Double.parseDouble(Row[15]),
                            Row[13]);
                    dsr.tags.add(Row[9]);
                    desires.add(dsr);
                    createdDesires.add(dsr.id);
                } else {
                    /* Append the tag name in the desire last desire */
                    desires.get(desires.size() - 1).tags.add(Row[9]);
                }
            }

            return desires;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @GetMapping(path = "/bids/{id}")
    List<BidResponse> getBidsByUserId(@PathVariable String id) {
        try {
            /* Array list for response */
            ArrayList<BidResponse> bids = new ArrayList<>();
            /* The response from the DB */
            String[][] dbResponse = bidsRepository.findBidsByUser(id);
            /* Loop through create the desire and append to response list */
            for (int i = 0; i < dbResponse.length; i++) {
                final String[] Row = dbResponse[i];
                bids.add(new BidResponse(
                        Row[0],
                        Double.parseDouble(Row[4]),
                        Row[3],
                        Row[5],
                        Row[8] + " " + Row[9],
                        Timestamp.valueOf(Row[6]),
                        Boolean.parseBoolean(Row[7])));
            }

            return bids;

        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
}
