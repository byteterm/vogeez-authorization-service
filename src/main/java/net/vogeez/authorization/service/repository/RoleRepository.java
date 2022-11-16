package net.vogeez.authorization.service.repository;

import net.vogeez.authorization.service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This Repository is used to access the {@link Role} table in the database.
 * It extends the {@link JpaRepository} and provides all the methods to access the database.
 * The {@link Role} class is the entity class for the {@link Role} table.
 * The {@link Long} class is the type of the primary key of the {@link Role} table.
 * The {@link RoleRepository} class is annotated with {@link Repository} to make it a Spring Bean.
 *
 * @see Repository
 * @see JpaRepository
 * @see Role
 * @see Long
 *
 * @author : Niklas Tat
 * @since : 0.1
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Short> {
}
