package in.joshbetz.careproject.user;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfilePic {
    private String filename;
    private byte[] data;

    public ProfilePic(String filename, byte[] data) {
        this.filename = filename;
        this.data = data;
    }
}
