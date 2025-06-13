package ffg.fillipe.expense_tracker.exceptions.requests;

import lombok.Getter;

@Getter
public class RequestWithoutRequiredParamException extends RuntimeException {
    private String message;
    public RequestWithoutRequiredParamException(String message) {
        super(message);
        this.message = message;
    }
}
