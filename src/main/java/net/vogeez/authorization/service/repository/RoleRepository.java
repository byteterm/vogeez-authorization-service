package net.vogeez.authorization.service.repository;

import net.vogeez.authorization.service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Short> {
}
