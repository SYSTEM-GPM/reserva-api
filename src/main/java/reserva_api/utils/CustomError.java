package reserva_api.utils;

public class CustomError {

    // mesmo nome que a Validation retorna
    private String defaultMessage;

    public CustomError(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}
