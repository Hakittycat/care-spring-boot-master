package in.joshbetz.careproject.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Data
public class CareUser {

    @Id
    private String id;
    private String username;
    private String password;
    private String userRole;

    public CareUser(String username, String password, String userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }
}
