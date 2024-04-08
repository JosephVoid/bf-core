package com.buyersfirst.core.controllers.bids;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.buyersfirst.core.dto.BidResponse;
import com.buyersfirst.core.models.BidsRepository;

@RestController
@RequestMapping(path = "/bids", method = RequestMethod.GET)
public class BidGetController {

    @Autowired
    BidsRepository bidsRepository;

    @GetMapping("/all/{desireId}")
    public @ResponseBody List<BidResponse> getAllBids(@PathVariable String desireId) {
        try {
            /* Array list for response */
            ArrayList<BidResponse> bids = new ArrayList<>();
            /* The response from the DB */
            String[][] dbResponse = bidsRepository.findAllBidsJoined(desireId);
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

    @GetMapping("/{id}")
    public @ResponseBody BidResponse getABid(@PathVariable String id) {
        try {
            /* The response from the DB */
            String[][] dbResponse = bidsRepository.findABidJoined(id);
            /* Create the bid response */
            BidResponse bid = new BidResponse(
                    dbResponse[0][0],
                    Double.parseDouble(dbResponse[0][4]),
                    dbResponse[0][3],
                    dbResponse[0][5],
                    dbResponse[0][9] + " " + dbResponse[0][10],
                    dbResponse[0][15], //
                    dbResponse[0][11],
                    dbResponse[0][14],
                    dbResponse[0][13],
                    Timestamp.valueOf(dbResponse[0][6]),
                    Boolean.parseBoolean(dbResponse[0][7]));
            return bid;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
}
