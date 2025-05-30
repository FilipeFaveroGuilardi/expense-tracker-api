package dev.fillipe.expense_tracker.exceptions.exceptionhandler;


public record ErrorResponse(Integer status, String message) {
    public ErrorResponse (String message) {
        this(500, message);
    }
}
