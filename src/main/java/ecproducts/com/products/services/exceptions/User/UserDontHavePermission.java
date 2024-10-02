package ecproducts.com.products.services.exceptions.User;

public class UserDontHavePermission extends RuntimeException {
	public UserDontHavePermission(String message) {
		super(message);
	}
}
