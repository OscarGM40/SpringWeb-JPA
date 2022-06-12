package practica.practica01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import practica.practica01.models.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

  Role findByName(String name);

}
