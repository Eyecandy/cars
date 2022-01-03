package no.linska.webapp.repository;


import no.linska.webapp.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(value = "SELECT * FROM user_account WHERE EMAIL = ?1", nativeQuery = true)
    List<User> findByEmail(String email);


}
