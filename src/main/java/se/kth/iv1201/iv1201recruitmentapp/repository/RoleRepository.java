package se.kth.iv1201.iv1201recruitmentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.kth.iv1201.iv1201recruitmentapp.model.Role;

import java.util.Optional;

/**
 * Defines database access for the role.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * Gets the role from the database by name.
     *
     * @param name The name.
     * @return Returns optional, contains type role on success,
     *          otherwise contains null.
     */
    Optional<Role> getRoleByName(String name);

    /**
     * Gets the role from the database by id.
     *
     * @param id The id.
     * @return Returns optional, contains type role on success,
     *          otherwise contains null.
     */
    Optional<Role> getRoleById(int id);
}
