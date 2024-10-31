package pe.com.utp.service.support.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.utp.service.support.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
