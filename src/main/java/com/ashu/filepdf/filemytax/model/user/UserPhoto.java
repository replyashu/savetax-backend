package com.ashu.filepdf.filemytax.model.user;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserPhoto {
    String publicId;
    MultipartFile file;
}
