package in.joshbetz.careproject.request;

import in.joshbetz.careproject.user.ProfilePic;
import lombok.Data;
import org.springframework.data.mongodb.gridfs.*;

@Data
public class RegisterRequest {
    private final String username;
    private final String password;
    private final String userRole;
    private final ProfilePic profilePic;
}
