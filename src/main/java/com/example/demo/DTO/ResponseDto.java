package com.example.demo.DTO;

public class ResponseDto<T> {
    private boolean result;
    private T data;
    private String message;

    public ResponseDto(boolean result, T data) {
        this.result = result;
        this.data = data;
    }

    public ResponseDto(boolean result, T data, String message) {
        this.result = result;
        this.data = data;
        this.message = message;
    }

    public static <T> ResponseDto<T> setSuccess(T data) {
        return new ResponseDto<>(true, data);
    }

    public static <T> ResponseDto<T> setFailed(String message) {
        return new ResponseDto<>(false, null, message);
    }

    public static <T> ResponseDto<T> setSuccessData(String message, T data) {
        return new ResponseDto<>(true, data, message);
    }

    public boolean isResult() {
        return result;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
