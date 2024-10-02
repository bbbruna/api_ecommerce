package ecproducts.com.products.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ecproducts.com.products.entities.Cart;
import ecproducts.com.products.entities.CartItem;
import ecproducts.com.products.entities.Product;
import ecproducts.com.products.entities.User;
import ecproducts.com.products.enums.CartStatusEnum;
import ecproducts.com.products.repositories.CartItemRepository;
import ecproducts.com.products.repositories.CartRepository;
import ecproducts.com.products.repositories.ProductRepository;
import ecproducts.com.products.repositories.UserRepository;
import ecproducts.com.products.services.exceptions.CartAlreadyExistsException;
import ecproducts.com.products.services.exceptions.CartItemNotFoundException;
import ecproducts.com.products.services.exceptions.CartNotFoundException;
import ecproducts.com.products.services.exceptions.ProductNotFoundException;
import ecproducts.com.products.services.exceptions.User.UserNotFoundException;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	

	public Cart create(Long userId) {
		User user = validateUser(userId);

		Cart existingCart = cartRepository.findByUser(user);

		if (existingCart != null && existingCart.getStatus() == CartStatusEnum.ACTIVE) {
			throw new CartAlreadyExistsException("Carrinho já existe para esse usuário");
		}

		Cart newCart = new Cart();
		newCart.setUser(user);
		newCart.setStatus(CartStatusEnum.ACTIVE);

		return cartRepository.save(newCart);
	}

	public void addItem(Long cartId, Long productId, int quantity) {
		Cart cart = validateCart(cartId);
		Product product = validateProduct(productId);

		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantidade deve ser maior que zero");
		}

		CartItem cartItem = findCartItem(cart, productId);

		if (cartItem != null) {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		} else {
			CartItem newItem = new CartItem();
			newItem.setProduct(product);
			newItem.setQuantity(quantity);
			newItem.setCartId(cart.getId());
			cart.getItems().add(newItem);
		}

		cart.setTotalQuantity(cart.getItems().stream().mapToInt(CartItem::getQuantity).sum());

		cartRepository.save(cart);
	}
	
	public void updateProductQuantity(Long cartId, Long productId, int quantity) {
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cartId, productId);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        } else {
            throw new RuntimeException("Item not found in cart");
        }
    }
	
	public void removeProductFromCart(Long cartId, Long productId) {
	    List<CartItem> items = cartItemRepository.findAllByCartIdAndProductId(cartId, productId);
	    if (!items.isEmpty()) {
	        cartItemRepository.deleteAll(items);
	    } else {
	        throw new RuntimeException("Item not found in cart");
	    }
	}

	public void removeItem(Long cartId, Long productId) {
		Cart cart = validateCart(cartId);
		List<CartItem> items = cart.getItems();
		CartItem itemToRemove = null;

		for (CartItem item : items) {
			if (item.getProduct().getId().equals(productId)) {
				itemToRemove = item;
				break;
			}
		}

		if (itemToRemove != null) {
			if (itemToRemove.getQuantity() > 1) {
				itemToRemove.setQuantity(itemToRemove.getQuantity() - 1);
			} else {
				items.remove(itemToRemove);
			}

			updateTotalQuantity(cart);
			cartRepository.save(cart);
		} else {
			throw new CartItemNotFoundException("Item não encontrado no carrinho para o produto ID: " + productId);
		}
	}
	

	public void clearCart(Long cartId) {
		Cart cart = validateCart(cartId);

		cart.getItems().clear();

		cartRepository.save(cart);
	}

	public List<CartItem> getItems(Long cartId) {
		Cart cart = validateCart(cartId);
		return cart.getItems();
	}

	public Cart getCartByUserId(Long userId) {
		return cartRepository.findByUserId(userId)
				.orElseThrow(() -> new CartNotFoundException("Carrinho não encontrado para o usuário ID: " + userId));
	}

	/// Validations

	private void updateTotalQuantity(Cart cart) {
		int totalQuantity = cart.getItems().stream().mapToInt(CartItem::getQuantity).sum();
		cart.setTotalQuantity(totalQuantity);
	}

	private Cart validateCart(Long cartId) {
		return cartRepository.findById(cartId)
				.orElseThrow(() -> new CartNotFoundException("Carrinho não encontrado com ID: " + cartId));
	}

	private User validateUser(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("O usuário não foi encontrado"));
	}

	private Product validateProduct(Long productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Produto não encontrado com ID: " + productId));
	}

	private CartItem findCartItem(Cart cart, Long productId) {
		return cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst()
				.orElse(null);
	}
}
