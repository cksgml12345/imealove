package baewha.pblProject.imealove.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import baewha.pblProject.imealove.domain.repository.UserRepository;
import baewha.pblProject.imealove.domain.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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

	public Optional<User> login(String username, String password, HttpServletRequest request) {
		Optional<User> userOpt = userRepository.findByUsername(username)
			.filter(user -> user.getPassword().equals(password));

		if (userOpt.isPresent()) {
			// Create or retrieve an existing session
			HttpSession session = request.getSession();
			session.setAttribute("user", userOpt.get());
		}

		return userOpt;
	}

	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false); // Get the current session if any
		if (session != null) {
			session.invalidate(); // Invalidate the session to log out
		}
	}
}