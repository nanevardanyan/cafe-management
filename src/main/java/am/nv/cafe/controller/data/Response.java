package am.nv.cafe.controller.data;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class Response<T> {

    private HttpStatus status;

    private String message;

    private Map<String, String> fieldErrors;

    private T data;

    public Response() {}

    public Response(HttpStatus status) {
        this.status = status;
    }

    public Response(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Response<T> setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Response<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public T getData() {
        return data;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }

    public void addFieldError(String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new HashMap<>();
        }
        fieldErrors.put(field, message);
    }
}
