package in.joshbetz.careproject.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<CareUser, String>  {

    List<CareUser> getUserByUserRole(String userRole);

    Optional<CareUser> findByUsername(String username);

}
