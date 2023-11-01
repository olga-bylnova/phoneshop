package com.es.core.quickorderentry.dto;

public class InputErrorDto {
    private String modelNameErrorMessage;
    private String quantityErrorMessage;

    public String getModelNameErrorMessage() {
        return modelNameErrorMessage;
    }

    public void setModelNameErrorMessage(String modelNameErrorMessage) {
        this.modelNameErrorMessage = modelNameErrorMessage;
    }

    public String getQuantityErrorMessage() {
        return quantityErrorMessage;
    }

    public void setQuantityErrorMessage(String quantityErrorMessage) {
        this.quantityErrorMessage = quantityErrorMessage;
    }
}
