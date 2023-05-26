package reserva_api.utils;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class ApiError extends ApiBaseReturn {
    private List<CustomError> errors;

    public ApiError() {
        this.errors = new ArrayList<CustomError>();
    }

    public ApiError(String error) {
        this();
        this.errors.add(new CustomError(error));
    }

    public List<CustomError> getErrors() {
        return errors;
    }

    public void setError(String error) {
        this.errors.add(new CustomError(error));
    }
}
