package ie.ali.taskmanager.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {


    @Query(value = "select u from User u where u.username = :uname")
    public Optional<User> findByUsername(@Param("uname") String username);
}
