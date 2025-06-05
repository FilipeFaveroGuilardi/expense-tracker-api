package ffg.fillipe.expense_tracker.exceptions.user;

import lombok.Getter;
import org.springframework.security.authentication.AuthenticationServiceException;

@Getter
public class UserInvalidCredentialsException extends AuthenticationServiceException {
  private final String message;

  public UserInvalidCredentialsException() {
    super("Invalid credentials");
    message = "Invalid credentials";
  }

  public UserInvalidCredentialsException(String message) {
        super(message);
        this.message = message;
    }
}
