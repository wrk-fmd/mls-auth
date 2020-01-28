package at.wrk.fmd.mls.auth.config;

import at.wrk.fmd.mls.auth.entity.Role;
import at.wrk.fmd.mls.auth.entity.User;
import at.wrk.fmd.mls.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collections;

/**
 * This class creates default users as specified in the application config
 */
@Slf4j
@Component
public class DefaultUserCreator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DefaultUserProperties properties;

    public DefaultUserCreator(UserRepository userRepository, PasswordEncoder passwordEncoder, DefaultUserProperties properties) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.properties = properties;
    }

    @Autowired
    @Transactional
    public void createDefaultUsers() {
        if (properties.getUsers() == null) {
            log.info("Skipping creation of default users: None given");
        } else if (userRepository.count() > 0) {
            log.info("Skipping creation of default users: Users already exist");
        } else {
            log.info("Creating {} default users", properties.getUsers().size());
            properties.getUsers().forEach(this::createUser);
        }
    }

    private void createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setRoles(Collections.singleton(Role.ROOT));
        userRepository.save(user);
    }
}
