package no.linska.webapp.repository;

import no.linska.webapp.entity.Retailer;
import org.springframework.data.repository.CrudRepository;

public interface RetailerRepository  extends CrudRepository<Retailer, Long> {
}
