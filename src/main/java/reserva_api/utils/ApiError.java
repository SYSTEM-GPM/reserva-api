package reserva_api.utils;

import java.util.ArrayList;
import java.util.List;

public class ApiError extends ApiBaseReturn {
    private List<String> errors;

    public ApiError() {
        this.errors = new ArrayList<String>();
    }

    public ApiError(String error) {
        this();
        this.errors.add(error);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setError(String error) {
        this.errors.add(error);
    }
}
