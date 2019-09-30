package com.zone.zissa.response;

/**
 * The ServiceResponse class
 * 
 * @param <T>
 */
public class PageServiceResponse<T> {

    private int status;

    private String message;

    private String errorMessage;

    private T data;

    private long totalRecords;

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long l) {
        this.totalRecords = l;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
