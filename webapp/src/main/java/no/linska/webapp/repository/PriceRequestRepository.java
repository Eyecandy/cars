package no.linska.webapp.repository;

import no.linska.webapp.entity.PriceRequest;
import org.springframework.data.repository.CrudRepository;

public interface PriceRequestRepository extends CrudRepository<PriceRequest, Long> {
}
