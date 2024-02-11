package com.buyersfirst.core.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.buyersfirst.core.interfaces.Alert;
import com.buyersfirst.core.models.Desires;
import com.buyersfirst.core.models.DesiresRepository;
import com.buyersfirst.core.models.NotifyTagsRepository;
import com.buyersfirst.core.models.UserWantsRepository;
import com.buyersfirst.core.models.Users;
import com.buyersfirst.core.models.UsersRepository;

@Service
public class AlertUsers {
    @Autowired
    NotifyTagsRepository notifyTagsRepository;
    @Autowired
    NotificationService notificationService;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    DesiresRepository desiresRepository;
    @Autowired
    UserWantsRepository userWantsRepository;

    public boolean alertForTags(String desireName, Integer tags[]) {
        try {
            String templateString = "Someone wants \"" + truncateStr(desireName)
                    + "\", Go to Buyer's First to check it out. ";
            String[][] userTagList = notifyTagsRepository.findContactByTag(tags);
            ArrayList<Alert> alertList = transformAlerts(userTagList);
            for (int i = 0; i < alertList.size(); i++) {
                Alert alert = alertList.get(i);
                System.out.println(alert.phone + " : " + templateString + alert.message);
                if (!notificationService.sendSMS(alert.phone, templateString + alert.message))
                    return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean alertDesireOwnerForbid(Integer desireOwner, String desireTitle, Double price) {
        String templateString = "Someone just bid Br " + String.valueOf(price) + " for your desire \""
                + truncateStr(desireTitle)
                + "\", Go to Buyer's First to check it out. ";
        String userPhone = usersRepository.findById(desireOwner).get().getPhone();
        System.out.println(userPhone + " : " + templateString);
        if (!notificationService.sendSMS(userPhone, templateString))
            return false;
        return true;
    }

    public boolean alertUsersWhoWantedTheDesire(Integer desireId, String desireTitle, Double price) {
        String templateString = "Someone just bid Br " + String.valueOf(price) + " for \""
                + truncateStr(desireTitle)
                + "\", which you also wanted, Go to Buyer's First to check it out. ";
        List<Users> users = usersRepository.findUserWhoWantDesires(desireId);
        users.forEach((user) -> {
            System.out.println(user.getPhone() + " : " + templateString);
            notificationService.sendSMS(user.getPhone(), templateString);
        });
        return true;
    }

    public boolean alertOnBidAccept(Integer bidOwnerId, Integer desireId) {
        Desires desire = desiresRepository.findById(desireId).get();
        Users desireOwner = usersRepository.findById(desire.getOwnerId()).get();
        Users bidOwner = usersRepository.findById(bidOwnerId).get();
        String templateString = "Your bid for \"" + truncateStr(desire.getTitle())
                + "\" has been accepted. Contact "
                + desireOwner.getFirst_name()
                + " at " + desireOwner.getPhone();
        System.out.println(bidOwner.getPhone() + " : " + templateString);
        if (!notificationService.sendSMS(bidOwner.getPhone(), templateString))
            return false;
        return true;
    }

    private ArrayList<Alert> transformAlerts(String[][] input) {
        ArrayList<Alert> finalList = new ArrayList<Alert>();
        for (String[] row : input) {
            finalList.add(new Alert(row[0], row[1]));
        }
        return finalList;
    }

    private String truncateStr(String str) {
        if (str.length() > 80)
            return str.substring(0, 80) + "...";
        return str;
    }
}
