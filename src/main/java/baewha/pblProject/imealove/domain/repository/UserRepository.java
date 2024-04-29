package baewha.pblProject.imealove.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import baewha.pblProject.imealove.domain.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}