package reserva_api.utils;

public class ApiSuccess extends ApiError {

    private String message;

    public ApiSuccess() {
        super();
    }

    public ApiSuccess(String message) {
        this();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
