package com.buyersfirst.core.controllers.bids;

import java.util.List;
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
import com.buyersfirst.core.interfaces.CreateBidRqB;
import com.buyersfirst.core.models.Bids;
import com.buyersfirst.core.models.BidsRepository;
import com.buyersfirst.core.services.TokenParser;
import jakarta.security.auth.message.AuthException;

@RestController
@RequestMapping(path = "/bids")
public class BidPatchController {

    @Autowired
    TokenParser tokenParser;
    @Autowired
    BidsRepository bidsRepository;

    @PatchMapping(path = "/{id}")
    @ResponseBody Bids updateBids (@RequestHeader("Authorization") String auth, @PathVariable Integer id, @RequestBody CreateBidRqB body) {
        try {
            Integer userId = tokenParser.getUserId(auth);
            /* Check if user owns the bid */
            List<Integer> bids = bidsRepository.listBidsByOwner(userId);
            if (!bids.contains(id))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not owner");
            /* Update the bid */
            bidsRepository.updateBid(id, body.description, body.price, body.picture);
            return bidsRepository.findById(id).get();
        } catch (AuthException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Auth Header Issue");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
}
