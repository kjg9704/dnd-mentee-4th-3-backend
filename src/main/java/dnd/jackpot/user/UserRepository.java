package dnd.jackpot.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmailAndLogintype(String email, String loginType);
  Optional<User> findById(Long id);
  Boolean existsByEmail(String email);
  Boolean existsByName(String email);
  void deleteByEmailAndLogintype(String email, String loginType);
  Optional<User> findByEmail(String email);
  Page<User> findAll(Pageable pageable);
}