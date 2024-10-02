package ecproducts.com.products.services.exceptions;

public class CartNotFoundException extends RuntimeException {
	public CartNotFoundException(String message) {
		super(message);
	}
}
