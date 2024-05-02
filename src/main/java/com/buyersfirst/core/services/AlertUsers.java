package com.buyersfirst.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.buyersfirst.core.dto.Alert;
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
    @Value("${domain.host}")
    private String domain;

    public boolean alertForTags(String desireId, String desireName, String tagIds[]) {
        try {
            String templateString = String.format("Someone wants '%s', Go to %s/%s to check it out",
                    truncateStr(desireName), domain, desireId);
            String[][] userTagList = notifyTagsRepository.findContactByTag(tagIds);
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

    public boolean alertDesireOwnerForbid(String desireId, String desireOwner, String desireTitle, Double price) {
        String templateString = String.format(
                "Someone just offered an item for Br %s for your desire '%s' Go to %s/%s to check it out",
                String.valueOf(price), truncateStr(desireTitle), domain, desireId);
        String userPhone = usersRepository.findById(UUID.fromString(desireOwner)).get().getPhone();
        System.out.println(userPhone + " : " + templateString);
        if (!notificationService.sendSMS(userPhone, templateString))
            return false;
        return true;
    }

    public boolean alertUsersWhoWantedTheDesire(String desireId, String desireTitle, Double price) {
        String templateString = String.format(
                "Someone just offered an item for Br %s for '%s', which you also wanted, Go to %s/%s to check it out",
                String.valueOf(price), truncateStr(desireTitle), domain, desireId);
        List<Users> users = usersRepository.findUserWhoWantDesires(desireId);
        users.forEach((user) -> {
            System.out.println(user.getPhone() + " : " + templateString);
            notificationService.sendSMS(user.getPhone(), templateString);
        });
        return true;
    }

    public boolean alertOnBidAccept(String bidOwnerId, String desireId) {
        Desires desire = desiresRepository.findById(UUID.fromString(desireId)).get();
        Users desireOwner = usersRepository.findById(UUID.fromString(desire.getOwnerId())).get();
        Users bidOwner = usersRepository.findById(UUID.fromString(bidOwnerId)).get();
        String templateString = String.format(
                "Your offer for '%s', has been accepted. Contact %s [%s]",
                truncateStr(desire.getTitle()), desireOwner.getFirst_name(), desireOwner.getPhone());
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
