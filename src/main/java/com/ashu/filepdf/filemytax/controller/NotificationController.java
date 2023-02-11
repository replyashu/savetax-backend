package com.ashu.filepdf.filemytax.controller;

import com.ashu.filepdf.filemytax.data.NoteWithToken;
import com.ashu.filepdf.filemytax.service.FirebaseMessagingService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    private FirebaseMessagingService firebaseService;

    @RequestMapping("/send-notification")
    @ResponseBody
    public String sendNotification(@RequestBody NoteWithToken note) throws FirebaseMessagingException {
        System.out.println("notewa" + note.toString());
        return firebaseService.sendNotification(note.getNote(), note.getToken());
    }
}
