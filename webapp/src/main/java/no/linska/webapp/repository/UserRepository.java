package no.linska.webapp.repository;


import no.linska.webapp.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {
    @Query(value = "SELECT * FROM user_account WHERE EMAIL = ?", nativeQuery = true)
    User findByEmail(String email);

    @Query(value = "SELECT * FROM user_account WHERE EMAIL = ?", nativeQuery = true)
    Optional<User> findOptionalUserByEmail(String email);

}
