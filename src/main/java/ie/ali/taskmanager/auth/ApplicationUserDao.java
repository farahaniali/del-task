package ie.ali.taskmanager.auth;

public interface ApplicationUserDao {

    ApplicationUser selectApplicationUserByUsername(String username);

}
