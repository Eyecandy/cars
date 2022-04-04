package no.linska.webapp.repository;

import no.linska.webapp.entity.Retailer;
import no.linska.webapp.entity.Seller;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface SellerRepository extends CrudRepository<Seller, Long> {
    @Query(value = "SELECT * FROM seller WHERE retailer_id in :retailers", nativeQuery = true)
    Optional<Set<Seller> > findByRetailer(@Param("retailers") Collection<Retailer> retailers);
}
