package no.linska.webapp.repository;


import no.linska.webapp.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<User, Integer> {

}
