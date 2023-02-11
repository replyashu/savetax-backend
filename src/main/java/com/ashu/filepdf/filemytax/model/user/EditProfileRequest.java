package com.ashu.filepdf.filemytax.model.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class EditProfileRequest {
    public String userUid;
    public String name;
    public String email;
    public String phoneNumber;
    public MultipartFile profilePhoto;
    public String userId;
}
