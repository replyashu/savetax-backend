package com.ashu.filepdf.filemytax.data;

import lombok.Data;

@Data
public class NoteWithToken {
    private Note note;
    private String token;
}
