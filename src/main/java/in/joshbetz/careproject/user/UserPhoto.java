package in.joshbetz.careproject.user;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserPhoto {
    private String photoType;
    private String filename;
    private String description;
    private String data;

    public UserPhoto(String filename, String description, String photoType, String data) {
        this.filename = filename;
        this.description = description;
        this.data = data;
        this.photoType = photoType;
    }
}
