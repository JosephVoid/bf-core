package com.buyersfirst.core.controllers.bids;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.buyersfirst.core.interfaces.CreateBidRqB;
import com.buyersfirst.core.models.AcceptedBids;
import com.buyersfirst.core.models.AcceptedBidsRepository;
import com.buyersfirst.core.models.Bids;
import com.buyersfirst.core.models.BidsRepository;
import com.buyersfirst.core.models.Desires;
import com.buyersfirst.core.models.DesiresRepository;
import com.buyersfirst.core.services.AlertUsers;
import com.buyersfirst.core.services.TokenParser;

import jakarta.security.auth.message.AuthException;

@RestController
@RequestMapping(path = "/bids")
public class BidPostController {

    @Autowired
    TokenParser tokenParser;
    @Autowired
    DesiresRepository desiresRepository;
    @Autowired
    BidsRepository bidsRepository;
    @Autowired
    AcceptedBidsRepository acceptedBidsRepository;
    @Autowired
    AlertUsers alertUsers;

    @PostMapping(path = "/{desireId}/create")
    @ResponseBody
    Bids createBids(@RequestHeader("Authorization") String auth, @PathVariable String desireId,
            @RequestBody CreateBidRqB body) {
        if (body.description == null || body.price == null || desireId == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Input");
        try {
            String userId = tokenParser.getUserId(auth);
            /* Check if the desire is there */
            Optional<Desires> desire = desiresRepository.findById(UUID.fromString(desireId));
            if (!desire.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Desire does not exist");
            /* Check if the desire is owned by the user */
            List<String> desireList = desiresRepository.listDesiresByOwner(userId);
            if (desireList.contains(desireId))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't bid for own desire");
            /* Check if the desire is not closed */
            if (desire.get().getIsClosed() == 1)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Desire is closed");

            Bids bid = new Bids(desireId, userId, body.description, body.price, body.picture,
                    new Timestamp(System.currentTimeMillis()), 0);

            bidsRepository.save(bid);

            alertUsers.alertDesireOwnerForbid(desire.get().getOwnerId(), desire.get().getTitle(), bid.getBidPrice());
            alertUsers.alertUsersWhoWantedTheDesire(desireId, desire.get().getTitle(), bid.getBidPrice());
            return bid;

        } catch (AuthException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Auth Header Issue");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @PostMapping(path = "/accept/{bidId}")
    @ResponseBody
    Bids acceptBid(@RequestHeader("Authorization") String auth, @PathVariable String bidId) {
        try {
            String userId = tokenParser.getUserId(auth);
            /* Check if the bid is there */
            Optional<Bids> bid = bidsRepository.findById(UUID.fromString(bidId));
            if (!bid.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bid does not exist");
            /* Check if the bid is not accepted by the user */
            if (bid.get().getOwnerId() == userId)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't accept your own bid");
            /* Check if the bid is accepted before */
            List<AcceptedBids> acceptedBids = acceptedBidsRepository.findByBidIdAndUserId(bidId, userId);
            if (acceptedBids.size() > 0)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already accepted bid");

            acceptedBidsRepository.save(new AcceptedBids(userId, bidId, new Timestamp(System.currentTimeMillis())));

            /* If the bid is accepted by the desire owner, close the desire */
            Desires desire = desiresRepository.findById(UUID.fromString(bid.get().getDesireId())).get();
            if (desire.getOwnerId() == userId)
                desiresRepository.UpdateIsClosedStatus(desire.getId().toString(), 1);

            alertUsers.alertOnBidAccept(bid.get().getOwnerId(), bid.get().getDesireId());

            return bid.get();

        } catch (AuthException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Auth Header Issue");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @PostMapping(path = "/close/{bidId}")
    @ResponseBody
    Bids closeBid(@RequestHeader("Authorization") String auth, @PathVariable String bidId) {
        try {
            String userId = tokenParser.getUserId(auth);
            /* Check if the bid is there */
            Optional<Bids> bid = bidsRepository.findById(UUID.fromString(bidId));
            if (!bid.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bid does not exist");
            /* Check if the bid is not accepted by the user */
            if (bid.get().getOwnerId() != userId)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't close others bid");
            if (bid.get().getIsClosed() == 0)
                bidsRepository.UpdateIsClosedStatus(bidId, 1);
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bid already closed");

            return bidsRepository.findById(UUID.fromString(bidId)).get();

        } catch (AuthException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Auth Header Issue");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
}
