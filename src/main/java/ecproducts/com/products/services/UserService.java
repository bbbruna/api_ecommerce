package ecproducts.com.products.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ecproducts.com.products.dto.UserRegisterDTO;
import ecproducts.com.products.entities.User;
import ecproducts.com.products.repositories.UserRepository;
import ecproducts.com.products.services.exceptions.User.UserAlreadyExistsException;
import ecproducts.com.products.services.exceptions.User.UserNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User registerUser(UserRegisterDTO userRegisterDTO) {

		validateEmail(userRegisterDTO.email());

		User newUser = new User();
		newUser.setEmail(userRegisterDTO.email());
		newUser.setPassword(passwordEncoder.encode(userRegisterDTO.password()));
		newUser.setName(userRegisterDTO.name());

		return repository.save(newUser);
	}
	
	public User findById(Long id) {
	    return repository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
	}

	/// Validations

	private void validateEmail(String email) {
		User existingUser = repository.findByEmail(email);
		if (existingUser != null) {
			throw new UserAlreadyExistsException("Email já está em uso");
		}
	}
}
