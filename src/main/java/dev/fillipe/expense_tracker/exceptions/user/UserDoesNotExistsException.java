package dev.fillipe.expense_tracker.exceptions.user;

import lombok.Getter;

@Getter
public class UserDoesNotExistsException extends RuntimeException {
    private final String message;

    public UserDoesNotExistsException() {
        message = "User does not exists";
    }

    public UserDoesNotExistsException(String message) {
        super(message);
        this.message = message;
    }
}
