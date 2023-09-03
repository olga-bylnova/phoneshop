package com.es.core.model.phone.handler;

import org.springframework.jdbc.core.RowCallbackHandler;

import java.util.List;

public interface ProductRowCallbackHandler<T> extends RowCallbackHandler {
    List<T> getResults();
}
