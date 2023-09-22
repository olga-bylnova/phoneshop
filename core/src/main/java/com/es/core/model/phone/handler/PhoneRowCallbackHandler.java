package com.es.core.model.phone.handler;

import com.es.core.model.phone.entity.Color;
import com.es.core.model.phone.entity.Phone;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.es.core.model.phone.util.TableColumnName.*;

public class PhoneRowCallbackHandler implements ProductRowCallbackHandler<Phone> {
    private Map<Long, Phone> results;
    private final RowMapper<Phone> phoneRowMapper = new BeanPropertyRowMapper<>(Phone.class);

    public PhoneRowCallbackHandler() {
        results = new LinkedHashMap<>();
    }

    @Override
    public void processRow(ResultSet resultSet) throws SQLException {
        Long phoneId = resultSet.getLong(ID);
        Long colorId = resultSet.getLong(COLOR_ID);

        Phone phone = results.computeIfAbsent(phoneId, (key) ->
        {
            try {
                Phone mappedPhone = phoneRowMapper.mapRow(resultSet, resultSet.getRow());
                mappedPhone.setId(phoneId);
                return mappedPhone;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        if (colorId != 0) {
            if (phone.getColors() == Collections.EMPTY_SET) {
                phone.setColors(new LinkedHashSet<>());
            }
            phone.getColors().add(new Color(resultSet.getLong(COLOR_ID), resultSet.getString(CODE)));
        }
    }

    @Override
    public List<Phone> getResults() {
        return new ArrayList<>(results.values());
    }
}
