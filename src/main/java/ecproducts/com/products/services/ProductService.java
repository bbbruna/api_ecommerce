package ecproducts.com.products.services;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecproducts.com.products.entities.Product;
import ecproducts.com.products.repositories.ProductRepository;
import ecproducts.com.products.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public Product create(Product product) {
		validateProduct(product);
		return repository.save(product);
	}

	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Produto não encontrado com ID: " + id);
		}
		repository.deleteById(id);
	}

	public Product getById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
	}

	public List<Product> getAll() {
		return repository.findAll();
	}

	public Product update(Long id, Product updatedProduct) {
		Product existingProduct = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

		if (updatedProduct.getPrice() != null) {
			existingProduct.setPrice(updatedProduct.getPrice());
		}

		if (updatedProduct.getName() != null) {
			existingProduct.setName(updatedProduct.getName());
		}
		if (updatedProduct.getDescription() != null) {
			existingProduct.setDescription(updatedProduct.getDescription());
		}
		if (updatedProduct.getUrlImage() != null) {
			existingProduct.setUrlImage(updatedProduct.getUrlImage());
		}

		if (updatedProduct.getTechnicalInfo() != null) {
			existingProduct.setTechnicalInfo(updatedProduct.getTechnicalInfo());
		}

		if (updatedProduct.getDailyRental() != null) {
			existingProduct.setDailyRental(updatedProduct.getDailyRental());
		}

		if (updatedProduct.getWeeklyRental() != null) {
			existingProduct.setWeeklyRental(updatedProduct.getWeeklyRental());
		}
		if (updatedProduct.getBiweeklyRental() != null) {
			existingProduct.setBiweeklyRental(updatedProduct.getBiweeklyRental());
		}
		if (updatedProduct.getMonthlyRental() != null) {
			existingProduct.setMonthlyRental(updatedProduct.getMonthlyRental());
		}

		return repository.save(existingProduct);
	}

	/// Validations

	private void validateProduct(Product product) {
		if (product == null) {
			throw new IllegalArgumentException("O produto não pode ser nulo.");
		}
		if (product.getName() == null || product.getName().isEmpty()) {
			throw new IllegalArgumentException("O nome do produto é obrigatório.");
		}
		if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("O preço do produto deve ser maior que zero.");
		}
		if (product.getDescription() == null || product.getDescription().isEmpty()) {
			throw new IllegalArgumentException("A descrição do produto é obrigatória.");
		}
		// Adicione mais validações conforme necessário
	}
}
