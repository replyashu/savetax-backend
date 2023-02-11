package com.ashu.filepdf.filemytax.controller;

import com.ashu.filepdf.filemytax.data.Note;
import com.ashu.filepdf.filemytax.data.NoteWithToken;
import com.ashu.filepdf.filemytax.entity.User;
import com.ashu.filepdf.filemytax.model.user.RegisterUserRequest;
import com.ashu.filepdf.filemytax.model.user.RegistrationResponse;
import com.ashu.filepdf.filemytax.model.user.TokenRequest;
import com.ashu.filepdf.filemytax.service.user.UserService;
import com.ashu.filepdf.filemytax.utils.Constants;
import com.ashu.filepdf.filemytax.utils.GoogleUserToken;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationController notificationController;

    @PostMapping("/user/save")
    public ResponseEntity<RegistrationResponse> registerUser(@Autowired NetHttpTransport transport, @Autowired GsonFactory factory, @RequestBody RegisterUserRequest registerUserRequest) {

        String token = registerUserRequest.getToken();

        // Deserialize token
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        // validate token
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, factory)
                .setAudience(Collections.singletonList(Constants.API_CLIENT_KEY))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        JsonFactory jsonFactory = new JacksonFactory();
//        try {
//            GoogleIdToken googleIdToken = GoogleIdToken.parse(jsonFactory, token);
//
//            if (!verifier.verify(googleIdToken)) {
//                return new ResponseEntity<>(new RegistrationResponse(), HttpStatus.UNAUTHORIZED);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(new RegistrationResponse(), HttpStatus.BAD_REQUEST);
//        }

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        Gson gson = new Gson();
        GoogleUserToken userToken = gson.fromJson(payload, GoogleUserToken.class);

        User user = new User();
        user.setEmail(userToken.getEmail());
        user.setName(userToken.getName());
        user.setImageUrl(userToken.getPicture());

        if (userToken.getAud().contains("google")) {
            user.setSource("android_google_signin");
        }

        UUID uuid = UUID.randomUUID();
        user.setUserId(uuid.toString());
        HttpStatus status = HttpStatus.OK;

        if (checkForUniqueEmail(user.getEmail())) {
            userService.saveUser(user);
        } else {
            user = userService.findUserByEmail(user.getEmail());
            status = HttpStatus.ALREADY_REPORTED;
        }

        RegistrationResponse response = new RegistrationResponse();
        response.setUserUid(user.getUserId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setProfilePhoto(user.getImageUrl());
//        response.setPhoneNumber(user.get());

        return new ResponseEntity<>(response, status);
    }

    boolean checkForUniqueEmail(String email) {
        if (email == null) {
            return true;
        }

        User user = userService.findUserByEmail(email);

        return user == null;
    }

    @PostMapping("/user/enable-push")
    public ResponseEntity<Boolean> saveNotificationToken(@RequestBody TokenRequest tokenRequest) {
        String userId = tokenRequest.getUserId();

        User user = userService.findByUserId(userId);

        System.out.println(tokenRequest.getUserId());

        user.setPushNotificationToken(tokenRequest.getToken());

        try {
            userService.saveUser(user);

            sendNotificationToSingleUser(user);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.EXPECTATION_FAILED);
        }
    }

//    @PostMapping(value = "/user/edit-profile",  consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
//    public ResponseEntity<RegistrationResponse> editProfile(@RequestPart EditProfileRequest editProfileRequest) {
//        User user = userService.findByUserId(editProfileRequest.getUserId());
//
//        try {
////            byte[] decodedURLBytes = Base64.getDecoder().decode(editProfileRequest.getProfilePhoto());
////            int width = 1;
////            int height = 2;
////
////            DataBuffer buffer = new DataBufferByte(decodedURLBytes, decodedURLBytes.length);
////
////            //3 bytes per pixel: red, green, blue
////            WritableRaster raster = Raster.createInterleavedRaster(buffer, width, height, 3 * width, 3, new int[] {0, 1, 2}, null);
////            ColorModel cm = new ComponentColorModel(ColorModel.getRGBdefault().getColorSpace(), false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
////            BufferedImage image = new BufferedImage(cm, raster, true, null);
////
////            ImageIO.write(image, "png", new File("./src/main/resources/static/image.png"));
////
////            File file = new ClassPathResource("static/image.png").getFile();
////
////            System.out.println(file);
//
//            String uri = "https://api.publit.io/v1/files/create?&api_signature=a4bc52c5373653b0df6a73d6725a9e843bd74e9c&api_key=jfnhlanf6dD4VbbuSVnD&api_nonce=49917088&api_timestamp=1675966792&api_test=false";
//
//            UserPhoto userPhoto = new UserPhoto();
//            userPhoto.setPublicId(editProfileRequest.getUserId());
//            userPhoto.setFile(editProfileRequest.profilePhoto);
//
//            HttpEntity<UserPhoto> request = new HttpEntity<>(userPhoto);
//            RestTemplate restTemplate = new RestTemplate();
//            restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
//
//            user.setImageBytes(null);
//        } catch (Exception e) {
//            user.setImageUrl(user.getImageUrl());
//        }
//
//        user.setName(editProfileRequest.getName());
//        user.setEmail(editProfileRequest.getEmail());
//        user.setUserPhone(editProfileRequest.getPhoneNumber());
//
//        userService.saveUser(user);
//
//        RegistrationResponse rp = new RegistrationResponse();
//        rp.setName(user.getName());
//        rp.setEmail(user.getEmail());
//        rp.setProfilePhoto(user.getImageUrl());
//        rp.setPhoneNumber(user.getUserPhone());
//        System.out.println(rp);
//        return new ResponseEntity<>(rp, HttpStatus.OK);
//    }

    @PostMapping("/user/notify")
    @ResponseBody
    public Boolean sendNotificationToAll() throws FirebaseMessagingException {
        List<User> users = userService.findAllUsers();

        for (User user: users) {
            if (user.getPushNotificationToken() != null) {
                Note note = new Note();
                note.setSubject("Welcome Onboard " + user.getName());
                note.setContent("Excited to see you here " + user.getAddress());
                Map<String, String> data = new LinkedHashMap<>();
                data.put("key1", "1");
                data.put("key2", user.getSource());
                data.put("key3", "Value c");
                note.setData(data);
                note.setImage(user.getImageUrl());
                sendNotificationToUser(note, user.getPushNotificationToken());
            }
        }

        return true;
    }

    public Boolean sendNotificationToSingleUser(User user) throws FirebaseMessagingException {
        if (user.getPushNotificationToken() != null) {
            Note note = new Note();
            note.setSubject("Welcome Onboard " + user.getName());
            note.setContent("Excited to see you here " + user.getEmail());
            Map<String, String> data = new LinkedHashMap<>();
            data.put("key1", "1");
            data.put("key2", user.getSource());
            data.put("key3", "Value c");
            note.setData(data);
            note.setImage(user.getImageUrl());
            sendNotificationToUser(note, user.getPushNotificationToken());
        }

        return true;
    }

    public void sendNotificationToUser(Note note, String token) throws FirebaseMessagingException {

        NoteWithToken noteWithToken = new NoteWithToken();
        noteWithToken.setToken(token);
        noteWithToken.setNote(note);

        notificationController.sendNotification(noteWithToken);
    }

}
