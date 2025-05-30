package dev.fillipe.expense_tracker.exceptions.exceptionhandler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.fillipe.expense_tracker.exceptions.auth.TokenNotFoundException;
import dev.fillipe.expense_tracker.exceptions.expenses.ExpenseDoesNotExistsException;
import dev.fillipe.expense_tracker.exceptions.expenses.ExpenseInvalidAmountException;
import dev.fillipe.expense_tracker.exceptions.expenses.ExpenseInvalidDateException;
import dev.fillipe.expense_tracker.exceptions.expenses.ExpenseNotOwnedByUserException;
import dev.fillipe.expense_tracker.exceptions.user.UserAlreadyExistsException;
import dev.fillipe.expense_tracker.exceptions.user.UserDoesNotExistsException;
import dev.fillipe.expense_tracker.exceptions.user.UserInvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage())
                );
    }

    @ExceptionHandler(UserDoesNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleUserDoesNotExistsException(UserDoesNotExistsException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage())
                );
    }

    @ExceptionHandler(UserInvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleUserInvalidCredentialsException(UserInvalidCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage())
                );
    }

    @ExceptionHandler(ExpenseDoesNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleExpenseDoesNotExistsException(ExpenseDoesNotExistsException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage())
                );
    }

    @ExceptionHandler(ExpenseNotOwnedByUserException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleExpenseNotOwnedByUserException(ExpenseNotOwnedByUserException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        new ErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage())
                );
    }

    @ExceptionHandler(ExpenseInvalidDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleExpenseInvalidDateException(ExpenseInvalidDateException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage())
                );
    }

    @ExceptionHandler(ExpenseInvalidAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleExpenseInvalidAmountException(ExpenseInvalidAmountException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage())
                );
    }

    @ExceptionHandler(TokenNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleTokenNotFoundException(TokenNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage())
        );
    }

    @ExceptionHandler(JWTVerificationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleJWTVerificationException(JWTVerificationException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), exception.getMessage())
                );
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorResponse(exception.getMessage())
                );
    }
}
