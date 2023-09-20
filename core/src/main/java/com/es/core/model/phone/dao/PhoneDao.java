package com.es.core.model.phone.dao;

import com.es.core.model.phone.entity.Phone;
import com.es.core.model.phone.entity.SortField;
import com.es.core.model.phone.entity.SortOrder;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);

    void save(Phone phone);

    List<Phone> findAll(int offset, int limit,
                        SortField sortField,
                        SortOrder sortOrder,
                        String searchQuery);

    int getStockByPhoneId(Long phoneId);
}
