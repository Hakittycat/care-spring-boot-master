package in.joshbetz.careproject.user;

import in.joshbetz.careproject.exception.RequestException;
import in.joshbetz.careproject.jwt.JsonWebTokenService;
import in.joshbetz.careproject.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final JsonWebTokenService jsonWebTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        CareUser user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RequestException("There was no user found with that username!"));
        String rawPassword = request.getPassword();
        String encodedPassword = user.getPassword();
        if (passwordEncoder.matches(rawPassword, encodedPassword)) {
            String token = jsonWebTokenService.createToken(user.getUsername());
            return ResponseEntity.ok(new LoginResponse(token));
        } else {
            throw new RequestException("Username or password was incorrect.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RequestException("A user with that username already exists!");
        } else {
            CareUser careUser = createUser(request);
            userRepository.insert(careUser);
            return ResponseEntity.ok(new RegisterResponse("Account registration successful", true));
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody CheckAuthRequest request) {
        boolean valid = jsonWebTokenService.isValidToken(request.getToken());
        return ResponseEntity.ok(new CheckAuthResponse(valid));
    }

    @PostMapping("/uploadphoto")
    public ResponseEntity<?> uploadPhoto(@RequestBody UploadPhotoRequest request) {
        UserPhoto photo = new UserPhoto(request.getFilename(), request.getDescription(), request.getPhotoType(), request.getBase64());
        CareUser user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RequestException("There was no user found with that username!"));

        user.AddPhoto(photo);
        userRepository.save(user);

        return ResponseEntity.ok(new UploadPhotoResponse("Photo upload successful!"));
    }

    @PostMapping("/getphotos")
    public ResponseEntity<?> getPhotos(@RequestBody GetPhotosRequest request) {
        CareUser user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RequestException("There was no user found with that username!"));

        if (!user.getUserPhotos().containsKey(request.getPhotoType()))
            throw new RequestException("Photo type invalid :(");

        ArrayList<UserPhoto> photos = user.getUserPhotos().get(request.getPhotoType());
        ArrayList<ResponsePhoto> result = new ArrayList<>();
        for (UserPhoto p : photos) {
            result.add(new ResponsePhoto(p));
        }
        return ResponseEntity.ok(new GetPhotosResponse(result));
    }

    private CareUser createUser(RegisterRequest request) {
        String username = request.getUsername();
        UserRole type = UserRole.getFromName(request.getUserRole());
        if (type == null) {
            type = UserRole.USER;
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return new CareUser(username, encodedPassword, type.name());
    }
}
