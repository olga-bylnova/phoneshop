package com.es.core.model.phone.entity;

import com.es.core.model.phone.util.TableColumnName;

public enum SortField {
    MODEL(TableColumnName.MODEL),
    BRAND(TableColumnName.BRAND),
    DISPLAY_SIZE(TableColumnName.DISPLAY_SIZE_INCHES),
    PRICE(TableColumnName.PRICE);
    private String columnName;

    SortField(String columnName) {
        this.columnName = columnName;
    }
    public String getColumnName() {
        return columnName;
    }
}
