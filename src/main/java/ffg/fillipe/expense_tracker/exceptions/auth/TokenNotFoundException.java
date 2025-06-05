package ffg.fillipe.expense_tracker.exceptions.auth;

import lombok.Getter;

@Getter
public class TokenNotFoundException extends RuntimeException {
    private final String message;

    public TokenNotFoundException() {
        message = "Token not found";
    }

    public TokenNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
