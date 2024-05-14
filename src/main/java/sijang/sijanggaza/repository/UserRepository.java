package sijang.sijanggaza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sijang.sijanggaza.domain.SiteUser;

public interface UserRepository extends JpaRepository<SiteUser, Integer> {

}
