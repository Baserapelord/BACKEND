package pe.com.utp.service.support.auth.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.utp.service.support.auth.account.domain.dto.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer> {
}
