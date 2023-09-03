package com.es.core.model.phone.handler;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
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
        results = new HashMap<>();
    }

    @Override
    public void processRow(ResultSet resultSet) throws SQLException {
        Long phoneId = resultSet.getLong(PHONE_ID);
        Phone phone = results.computeIfAbsent(phoneId, (key) ->
        {
            try {
                Phone result = phoneRowMapper.mapRow(resultSet, resultSet.getRow());
                result.setColors(new HashSet<>());
                return result;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        Color color = new Color(resultSet.getLong(ID), resultSet.getString(CODE));
        phone.getColors().add(color);
    }

    @Override
    public List<Phone> getResults() {
        return new ArrayList<>(results.values());
    }
}
