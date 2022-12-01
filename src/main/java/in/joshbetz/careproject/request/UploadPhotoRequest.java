package in.joshbetz.careproject.request;
import lombok.Data;

@Data
public class UploadPhotoRequest {
    private String username;
    private String photoType;
    private String filename;
    private String description;
    private String base64;
}
