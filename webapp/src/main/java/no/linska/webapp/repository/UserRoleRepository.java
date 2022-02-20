package no.linska.webapp.repository;

import no.linska.webapp.entity.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
}
