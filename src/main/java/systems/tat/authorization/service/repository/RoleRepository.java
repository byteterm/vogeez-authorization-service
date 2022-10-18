package systems.tat.authorization.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import systems.tat.authorization.service.entity.Role;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
public interface RoleRepository extends JpaRepository<Role, Short> {
}
