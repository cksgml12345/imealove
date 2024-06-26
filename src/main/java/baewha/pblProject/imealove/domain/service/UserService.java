package baewha.pblProject.imealove.domain.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import baewha.pblProject.imealove.domain.repository.UserRepository;
import baewha.pblProject.imealove.domain.domain.User;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public User register(String username, String password, String email) throws Exception {
		if (userRepository.findByUsername(username).isPresent()) {
			throw new Exception("Username already exists. Please choose another one.");
		}

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);

		return userRepository.save(user);
	}

	public Optional<User> login(String username, String password) {
		return userRepository.findByUsername(username)
			.filter(user -> user.getPassword().equals(password));
	}

	public void logout() {
		// 현재 세션을 무효화함
		SecurityContextHolder.clearContext();
	}
}