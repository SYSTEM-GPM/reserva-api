package reserva_api.utils;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private List<CustomError> errors;

    public ApiError() {
        this.timestamp = LocalDateTime.now();
        this.errors = new ArrayList<CustomError>();
    }

    public ApiError(String error) {
        this();
        this.errors = new ArrayList<CustomError>();
        this.errors.add(new CustomError(error));
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CustomError> getErrors() {
        return errors;
    }

    public void setError(String error) {
        if(this.errors == null) {
            this.errors = Arrays.asList(new CustomError(error));
        } else {
            this.errors.add(new CustomError(error));
        }
    }
}
