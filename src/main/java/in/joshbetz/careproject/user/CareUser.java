package in.joshbetz.careproject.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

@NoArgsConstructor
@Data
public class CareUser {

    @Id
    private String id;
    private String username;
    private String password;
    private String userRole;
    private HashMap<String, ArrayList<UserPhoto>> userPhotos;

    public CareUser(String username, String password, String userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        userPhotos = new HashMap<String, ArrayList<UserPhoto>>();
    }

    public void AddPhoto(UserPhoto photo) {
        String type = photo.getPhotoType();

        if (!userPhotos.containsKey(type))
            userPhotos.put(type, new ArrayList<UserPhoto>());

        userPhotos.get(type).add(photo);
    }
}
