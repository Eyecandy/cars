package no.linska.webapp.repository;

import no.linska.webapp.entity.Buyer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BuyerRepository extends CrudRepository<Buyer, Integer> {

    @Query(value = "SELECT * FROM buyer WHERE user_id = :userId", nativeQuery = true)
    Optional<Buyer> findByUser(@Param("userId") Long userId);
}
