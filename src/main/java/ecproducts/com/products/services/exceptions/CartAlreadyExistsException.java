package ecproducts.com.products.services.exceptions;

public class CartAlreadyExistsException extends RuntimeException {
	public CartAlreadyExistsException(String message) {
		super(message);
	}
}