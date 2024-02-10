package com.buyersfirst.core.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyersfirst.core.interfaces.Alert;
import com.buyersfirst.core.models.NotifyTagsRepository;

@Service
public class AlertUsers {
    @Autowired
    NotifyTagsRepository notifyTagsRepository;
    @Autowired
    NotificationService notificationService;

    public boolean alertForTags(String desireName, Integer tags[]) {
        try {
            String templateString = "Someone wants \"" + desireName + ", Go to buyers first to check it out. ";
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

    private ArrayList<Alert> transformAlerts(String[][] input) {
        ArrayList<Alert> finalList = new ArrayList<Alert>();
        for (String[] row : input) {
            finalList.add(new Alert(row[0], row[1]));
        }
        return finalList;
    }
}
