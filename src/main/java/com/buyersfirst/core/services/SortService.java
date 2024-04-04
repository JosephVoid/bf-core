package com.buyersfirst.core.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.buyersfirst.core.dto.DesireListRsp;

/**
 * SortService
 */
@Service
public class SortService {

    public List<DesireListRsp> sort(List<DesireListRsp> desires, String sortDir, String sortBy) {
        if (sortDir.equals("ASC")) {
            switch (sortBy) {
                case "desired_price":
                    desires.sort((ds1, ds2) -> ds1.price.compareTo(ds2.price));
                    return desires;
                case "wants":
                    desires.sort((ds1, ds2) -> ds1.wants.compareTo(ds2.wants));
                    return desires;
                case "created":
                    desires.sort((ds1, ds2) -> ds1.postedOn.compareTo(ds2.postedOn));
                    return desires;
                default:
                    return desires;
            }
        } else {
            switch (sortBy) {
                case "desired_price":
                    desires.sort((ds1, ds2) -> ds2.price.compareTo(ds1.price));
                    return desires;
                case "wants":
                    desires.sort((ds1, ds2) -> ds2.wants.compareTo(ds1.wants));
                    return desires;
                case "created":
                    desires.sort((ds1, ds2) -> ds2.postedOn.compareTo(ds1.postedOn));
                    return desires;
                default:
                    return desires;
            }
        }
    }
}