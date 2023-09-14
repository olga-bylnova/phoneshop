package com.es.core.model.phone.dao;

import com.es.core.model.phone.entity.Phone;
import com.es.core.model.phone.entity.SortField;
import com.es.core.model.phone.entity.SortOrder;
import com.es.core.model.phone.handler.PhoneRowCallbackHandler;
import com.es.core.model.phone.handler.ProductRowCallbackHandler;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.es.core.model.phone.util.StringUtil.*;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    private final RowMapper<Phone> phoneRowMapper = new BeanPropertyRowMapper<>(Phone.class);

    public Optional<Phone> get(final Long key) {
        ProductRowCallbackHandler<Phone> handler = new PhoneRowCallbackHandler();
        jdbcTemplate.query(JOIN_COLORS_TABLE_SQL, new Object[]{key}, handler);
        return handler.getResults().stream().findAny();
    }

    public void save(final Phone phone) {
        throw new UnsupportedOperationException("TODO");
    }

    public List<Phone> findAll(int offset, int limit,
                               SortField sortField,
                               SortOrder sortOrder,
                               String searchQuery) {
        String querySql = getSqlQuery(sortField, sortOrder, searchQuery);
        List<Phone> phones = jdbcTemplate.query(querySql, new Object[]{offset, limit}, phoneRowMapper);

        return getPhoneColors(phones);
    }

    private List<Phone> getPhoneColors(List<Phone> phones) {
        ProductRowCallbackHandler<Phone> handler = new PhoneRowCallbackHandler();
        for (Phone phone : phones) {
            jdbcTemplate.query(JOIN_COLORS_TABLE_SQL, new Object[]{phone.getId()}, handler);
        }
        return handler.getResults();
    }

    private String getSqlQuery(SortField sortField, SortOrder sortOrder, String searchQuery) {
        StringBuilder query;
        if (searchQuery != null) {
            query = getSqlQueryWithSearch(sortField, sortOrder, searchQuery);
        } else {
            query = new StringBuilder(FIND_ALL_SQL);
            if (sortField != null) {
                query.append(String.format(ORDER_BY_SQL, sortField.getColumnName(), sortOrder.name()));
            }
        }
        return query.toString();
    }

    private StringBuilder getSqlQueryWithSearch(SortField sortField, SortOrder sortOrder, String searchQuery) {
        StringBuilder query = new StringBuilder(FIND_ALL_WITH_FILTER_SQL);
        List<String> splitQuery = Arrays.stream(searchQuery
                        .toLowerCase()
                        .split("\\s+"))
                .toList();
        if (!splitQuery.isEmpty()) {
            query.append("WHERE ");
        }
        for (String word : splitQuery) {
            if (isNumeric(word)) {
                Object[] args = new Object[2];
                Arrays.fill(args, word);
                query.append(String.format(FILTER_WITH_NUMBER_PARAMETER_SQL, args));
            } else {
                Object[] args = new Object[3];
                Arrays.fill(args, word);
                query.append(String.format(FILTER_WITH_STRING_PARAMETER_SQL, args));
            }
            query.append("OR ");
        }
        query.replace(query.lastIndexOf("OR"), query.length(), "");
        if (sortField != null) {
            query.append(String.format(ORDER_BY_SQL, sortField.getColumnName(), sortOrder.name()));
        }
        query.append(OFFSET_LIMIT_SQL);
        return query;
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return PATTERN.matcher(strNum).matches();
    }
}
