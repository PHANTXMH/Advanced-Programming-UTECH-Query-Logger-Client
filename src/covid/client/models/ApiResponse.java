package covid.client.models;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ApiResponse<T> {
    private HttpStatus status;
    private String message;
    private List<String> errors;
    private T data;
    private boolean success;
    public ApiResponse(HttpStatus status, String message, List<String> errors){
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiResponse(HttpStatus status, String message, List<String> errors, T data, boolean success) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.data = data;
        this.success = success;
    }

    public ApiResponse() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}