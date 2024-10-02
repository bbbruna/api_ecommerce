package ecproducts.com.products.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecproducts.com.products.Configurations.QuantityRequest;
import ecproducts.com.products.entities.Cart;
import ecproducts.com.products.entities.CartItem;
import ecproducts.com.products.services.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@DeleteMapping("/{cartId}/items/all/{productId}")
	public ResponseEntity<Void> removeProduct(@PathVariable Long cartId, @PathVariable Long productId) {
	    try {
	        cartService.removeProductFromCart(cartId, productId);
	        return ResponseEntity.ok().build();
	    } catch (RuntimeException e) {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PutMapping("/{cartId}/items/{productId}")
	public ResponseEntity<Void> updateQuantity(
	        @PathVariable Long cartId,
	        @PathVariable Long productId,
	        @RequestBody QuantityRequest request) {
		    int quantity = request.getQuantity();
		    
		    try {
		        cartService.updateProductQuantity(cartId, productId, quantity);
		        return ResponseEntity.ok().build();
		    } catch (RuntimeException e) {
		        return ResponseEntity.notFound().build();
		    }
		}
	    
	@PostMapping("/create/{userId}")
	public ResponseEntity<Cart> createCart(@PathVariable Long userId) {
		Cart cart = cartService.create(userId);
		return ResponseEntity.ok(cart);
	}

	@PostMapping("/{cartId}/items/{productId}")
	public ResponseEntity<Void> addItem(@PathVariable Long cartId, @PathVariable Long productId,
			@RequestParam int quantity) {
		cartService.addItem(cartId, productId, quantity);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{cartId}/items/{productId}")
	public ResponseEntity<Void> removeItem(@PathVariable Long cartId, @PathVariable Long productId) {
		cartService.removeItem(cartId, productId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{cartId}/items")
	public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
		cartService.clearCart(cartId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{cartId}/items")
	public ResponseEntity<List<CartItem>> getItems(@PathVariable Long cartId) {
		List<CartItem> items = cartService.getItems(cartId);
		return ResponseEntity.ok(items);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
		Cart cart = cartService.getCartByUserId(userId);
		return ResponseEntity.ok(cart);
	}

}

