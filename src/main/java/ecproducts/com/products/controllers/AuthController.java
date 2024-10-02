package ecproducts.com.products.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.authentication.AuthenticationManager;

import ecproducts.com.products.dto.UserLoginDTO;
import ecproducts.com.products.entities.User;
import ecproducts.com.products.services.TokenService;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserLoginDTO userLogin) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				userLogin.email(), userLogin.password());

		Authentication authentication = authenticationManager.authenticate(authenticationToken);

		var user = (User) authentication.getPrincipal();

		String token = tokenService.generateToken(user);

		return ResponseEntity.ok(token);
	}

	@GetMapping("/protected")
	public ResponseEntity<String> getProtectedResource() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			return ResponseEntity.ok("Você está autenticado como: " + authentication.getName());
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
		}
	}
}
