package ie.ali.taskmanager.user;

import ie.ali.taskmanager.auth.ApplicationUser;
import ie.ali.taskmanager.auth.ApplicationUserDao;
import ie.ali.taskmanager.security.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service()
public class UserService implements ApplicationUserDao {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ApplicationUser selectApplicationUserByUsername(String username) {

        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) throw new UsernameNotFoundException(String.format("user %s not found", username));
        Set<SimpleGrantedAuthority> grantedAuthorities = ApplicationUserRole.valueOf(user.get().getRole()).getGrantedAuthorities();

        ApplicationUser applicationUser = new ApplicationUser(user.get().getId(), user.get(),
                user.get().username,user.get().password,
                grantedAuthorities,true,true,true,true);
        return applicationUser;
    }

    public User selectUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) throw new UsernameNotFoundException(String.format("user %s not found", username));
        return user.get();
    }

    public Boolean checkIfExist(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void registerUser(UserRequest user){
        if (userRepository.findById(user.username).isPresent()) throw new HttpClientErrorException(HttpStatus.CONFLICT, "User Already Exist");

        user.password = passwordEncoder.encode(user.password);

        User newUser = new User();

        newUser.username = user.username;
        newUser.password = user.password;
        userRepository.save(newUser);
    }


    public List<User> selectAllUsers() {
        return userRepository.findAll();
    }
}
