package ecproducts.com.products.services.exceptions;

public class CartItemNotFoundException extends RuntimeException {
	public CartItemNotFoundException(String message) {
		super(message);
	}
}