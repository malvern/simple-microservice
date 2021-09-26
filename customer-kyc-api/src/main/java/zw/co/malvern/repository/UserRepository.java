package zw.co.malvern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.malvern.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
