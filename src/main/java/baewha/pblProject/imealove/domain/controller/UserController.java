package baewha.pblProject.imealove.domain.controller;

import java.util.Map;
import java.util.Optional;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import baewha.pblProject.imealove.domain.dto.LoginRequestDto;
import baewha.pblProject.imealove.domain.service.UserService;
import baewha.pblProject.imealove.domain.domain.User;

@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Map<String, String> user) {
		try {
			User newUser = userService.register(user.get("username"), user.get("password"), user.get("email"));
			return ResponseEntity.ok(newUser);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest, HttpServletRequest request) {
		Optional<User> foundUser = userService.login(loginRequest.getUsername(), loginRequest.getPassword(), request);

		if (foundUser.isPresent()) {
			HttpSession session = request.getSession();
			session.setAttribute("user", foundUser.get());

			return ResponseEntity.ok(foundUser.get());
		} else {
			return ResponseEntity.badRequest().body("Invalid username or password.");
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return ResponseEntity.ok("Successfully logged out.");
	}
}