package in.joshbetz.careproject.request;

import in.joshbetz.careproject.user.UserPhoto;
import lombok.Data;

@Data
public class RegisterRequest {
    private final String username;
    private final String password;
    private final String userRole;
}
