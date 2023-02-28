package in.joshbetz.careproject.user;

import lombok.Data;

import java.util.Base64;

@Data
public class ResponsePhoto  {
    private String photoType;
    private String filename;
    private String description;
    private String base64;
    public ResponsePhoto(UserPhoto source) {
        photoType = source.getPhotoType();
        filename = source.getFilename();
        description = source.getDescription();
        base64 = source.getData();
    }
}
