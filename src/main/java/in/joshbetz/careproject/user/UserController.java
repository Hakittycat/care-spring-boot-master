package in.joshbetz.careproject.user;

import in.joshbetz.careproject.exception.RequestException;
import in.joshbetz.careproject.jwt.JsonWebTokenService;
import in.joshbetz.careproject.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
