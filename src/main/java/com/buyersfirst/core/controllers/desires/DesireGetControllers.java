package com.buyersfirst.core.controllers.desires;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.buyersfirst.core.interfaces.DesireCache;
import com.buyersfirst.core.interfaces.DesireListRsp;
import com.buyersfirst.core.interfaces.SingleDesire;
import com.buyersfirst.core.models.Bids;
import com.buyersfirst.core.models.BidsRepository;
import com.buyersfirst.core.models.DesiresRepository;
import com.buyersfirst.core.services.RedisCacheService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(path = "/desires", method = RequestMethod.GET)
public class DesireGetControllers {

    @Autowired
    DesiresRepository desiresRepository;
    @Autowired
    BidsRepository bidsRepository;
    @Autowired
    RedisCacheService redisCacheService;

    @GetMapping(path = "/all")
    public @ResponseBody List<DesireListRsp> listDesires (
        @RequestParam (value = "per-page", defaultValue = "20") Integer perPage,
        @RequestParam (value = "page", defaultValue = "1") Integer page,
        @RequestParam (value = "filter-by", defaultValue = "") String filterBy,
        @RequestParam (value = "sort-by", defaultValue = "wants") String sortBy,
        @RequestParam (value = "sort-dir", defaultValue = "DESC") String sortDir
    ) {
        if (!(sortBy.equals("created") || sortBy.equals("wants") || sortBy.equals("desired_price")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sort by param invalid");
        if (!(sortDir.equals("ASC") || sortDir.equals("DESC")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sort dir param invalid");
        if (page < 1 || perPage < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pagination invalid");
        try {

            ObjectMapper mapper = new ObjectMapper();

            /* ################################################################### */
            /* ---------------------RESPONDING WITH CACHE------------------------- */

            if (redisCacheService.jedis.exists("all-desires")) {
                String cachedString = redisCacheService.jedis.get("all-desires");
                List<DesireListRsp> cachedDesires = mapper.readValue(cachedString, DesireCache.class).allDesires;

                return cachedDesires.subList(perPage*(page - 1), Math.min(cachedDesires.size(), perPage*page));
            }

            /* ################################################################### */

            /* From DB */
            String [][] dbResponse = desiresRepository.findAllDesiresJoined(filterBy, sortBy+":"+sortDir);
            /* Empty ArrayList for responding */
            ArrayList<DesireListRsp> desires = new ArrayList<DesireListRsp>();
            /* Empty ArrayList to store the created desires id, used to remove duplicate rows */
            ArrayList<Integer> createdDesires = new ArrayList<Integer>();
            /* Loop through the 2*2 array for response */
            for (int i = 0; i < dbResponse.length; i++) {
                final String[] Row = dbResponse[i];
                /* If the desire is already created, just append the tag name the desire */
                if (!createdDesires.contains(Integer.parseInt(Row[0]))) {
                    DesireListRsp dsr = new DesireListRsp(
                        Integer.parseInt(Row[0]),
                        Row[3], 
                        Row[4], 
                        Row[1]+" "+Row[2], 
                        Double.parseDouble(Row[5]),
                        Integer.parseInt(Row[12]),
                        Integer.parseInt(Row[11]),
                        Integer.parseInt(Row[10]),
                        Boolean.parseBoolean(Row[8]),
                        Row[6],
                        Timestamp.valueOf(Row[7]),
                        // For tags
                        new ArrayList<String>()
                    );
                    dsr.tags.add(Row[9]);
                    desires.add(dsr);
                    createdDesires.add(dsr.id);
                } 
                else {
                    /* Append the tag name in the desire last desire */
                    desires.get(desires.size() - 1).tags.add(Row[9]);
                }
            }

            /* ################################################################### */
            /* ---------------------CACHING THE RESPONSE-------------------------- */

            DesireCache desireCache = new DesireCache(desires);
            redisCacheService.jedis.setex("all-desires", 3600,mapper.writeValueAsString(desireCache));

            /* ################################################################### */

            return desires.subList(perPage*(page - 1), Math.min(desires.size(), perPage*page));

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody SingleDesire getDesire (@PathVariable Integer id) {
        try {
            /* From DB */
            String [][] dbResponse = desiresRepository.findADesireJoined(id);
            if (dbResponse.length < 1)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Desire doesnt exist");
            /* Create the single desire, without tags or bids */
            SingleDesire dsr = new SingleDesire(
                Integer.parseInt(dbResponse[0][0]),
                dbResponse[0][3], 
                dbResponse[0][4], 
                dbResponse[0][1]+" "+dbResponse[0][2], 
                Double.parseDouble(dbResponse[0][5]),
                Integer.parseInt(dbResponse[0][12]),
                Integer.parseInt(dbResponse[0][11]),
                Integer.parseInt(dbResponse[0][10]),
                Boolean.parseBoolean(dbResponse[0][8]),
                dbResponse[0][6],
                Timestamp.valueOf(dbResponse[0][7]),
                // For Tags
                new ArrayList<String>(),
                // For Bid Ids
                new ArrayList<Integer>()
            );
            /* Add the tags on the desire */
            for (int i = 0; i < dbResponse.length; i++) {
                final String[] Row = dbResponse[i];
                dsr.tags.add(Row[9]);
            }
            /* Add the bids to the desires */
            List<Bids> bids = bidsRepository.findByDesireId(dsr.id);
            bids.iterator().forEachRemaining((bid) -> dsr.bidList.add(bid.getId()));

            return dsr;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
}
