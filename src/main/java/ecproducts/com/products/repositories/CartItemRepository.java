package ecproducts.com.products.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ecproducts.com.products.entities.CartItem;
import ecproducts.com.products.entities.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
    CartItem findByCartIdAndProductId(Long cartId, Long productId);
    
    List<CartItem> findAllByCartIdAndProductId(Long cartId, Long productId);

    void deleteAllByCartIdAndProductId(Long cartId, Long productId);
}
