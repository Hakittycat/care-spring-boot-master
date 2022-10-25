package in.joshbetz.careproject.security;

import in.joshbetz.careproject.user.CareUser;
import in.joshbetz.careproject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CareUser careUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unable to find user with username: " + username));
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + careUser.getUserRole());
        List<GrantedAuthority> authorities = Collections.singletonList(authority);
        return new User(careUser.getUsername(), careUser.getPassword(), authorities);
    }
}
