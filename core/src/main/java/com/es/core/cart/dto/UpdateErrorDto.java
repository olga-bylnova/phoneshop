package com.es.core.cart.dto;

public class UpdateErrorDto {
    private String message;
    private Long input;

    public UpdateErrorDto() {
    }

    public UpdateErrorDto(String message, Long input) {
        this.message = message;
        this.input = input;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getInput() {
        return input;
    }

    public void setInput(Long input) {
        this.input = input;
    }
}
