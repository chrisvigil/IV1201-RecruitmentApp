package se.kth.iv1201.iv1201recruitmentapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.kth.iv1201.iv1201recruitmentapp.model.Role;

public interface RoleRepository  extends JpaRepository<Role, Integer> {
    Role getRoleByName(String name);
    Role getRoleById(int id);
}
