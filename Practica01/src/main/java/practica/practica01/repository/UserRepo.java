package practica.practica01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import practica.practica01.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

  User findByName(String name);
  
}
