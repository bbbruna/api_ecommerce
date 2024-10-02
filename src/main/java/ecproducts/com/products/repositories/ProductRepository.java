package ecproducts.com.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ecproducts.com.products.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}