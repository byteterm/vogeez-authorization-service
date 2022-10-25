package systems.tat.authorization.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import systems.tat.authorization.service.entity.Role;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Short> {
}
