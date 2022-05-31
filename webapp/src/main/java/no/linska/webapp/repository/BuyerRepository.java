package no.linska.webapp.repository;

import no.linska.webapp.entity.Buyer;
import org.springframework.data.repository.CrudRepository;

public interface BuyerRepository extends CrudRepository<Buyer, Integer> {
}
