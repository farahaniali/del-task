package ie.ali.taskmanager.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Checks at startup if the admin user exists.
 * If not, creates one with a default password.
 */
@Component
public class UsersInitializer {
    private final UserService userService;
    private final PasswordEncoder encoder;

    @Autowired
    public UsersInitializer(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @PostConstruct
    public void init() {

        if (!userService.checkIfExist("admin")) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(encoder.encode("adminadmin"));
            user.setRole("ADMIN");

            userService.save(user);
        }
        if (!userService.checkIfExist("user1")) {
            User user = new User();
            user.setUsername("user1");
            user.setPassword(encoder.encode("123"));
            user.setRole("USER");

            userService.save(user);
        }
    }
}
