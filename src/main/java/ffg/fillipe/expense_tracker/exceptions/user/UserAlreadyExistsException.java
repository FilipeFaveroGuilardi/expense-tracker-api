package ffg.fillipe.expense_tracker.exceptions.user;

import lombok.Getter;
import org.springframework.security.authentication.AuthenticationServiceException;

@Getter
public class UserAlreadyExistsException extends AuthenticationServiceException {
    private final String message;

    public UserAlreadyExistsException() {
        super("User already exists");
        message = "User already exists";
    }

    public UserAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}
