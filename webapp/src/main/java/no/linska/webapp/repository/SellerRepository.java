package no.linska.webapp.repository;

import no.linska.webapp.entity.Seller;
import org.springframework.data.repository.CrudRepository;

public interface SellerRepository extends CrudRepository<Seller, Long> {
}
