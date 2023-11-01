package com.es.core.model.phone.dao;

import com.es.core.model.phone.entity.Phone;
import com.es.core.model.phone.entity.SortField;
import com.es.core.model.phone.entity.SortOrder;
import com.es.core.model.phone.handler.PhoneRowCallbackHandler;
import com.es.core.model.phone.handler.ProductRowCallbackHandler;
import com.es.core.model.phone.util.StringUtil;
import com.es.core.model.phone.util.StringUtilSqlQuery;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.es.core.model.phone.util.StringUtil.*;
import static com.es.core.model.phone.util.StringUtilSqlQuery.*;

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

    public Optional<Phone> getByModel(String modelName) {
        ProductRowCallbackHandler<Phone> handler = new PhoneRowCallbackHandler();
        jdbcTemplate.query(FIND_BY_MODEL_SQL, new Object[]{modelName}, handler);
        return handler.getResults().stream().findAny();
    }

    public void save(final Phone phone) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SAVE_PHONE_SQL, Statement.RETURN_GENERATED_KEYS);
            setStatementParameters(statement, phone);
            return statement;
        }, keyHolder);

        phone.setId((Long) keyHolder.getKey());
    }

    public List<Phone> findAll(int offset, int limit,
                               SortField sortField,
                               SortOrder sortOrder,
                               String searchQuery) {
        String querySql = getSqlQuery(sortField, sortOrder, searchQuery, false);
        List<Phone> phones = jdbcTemplate.query(querySql, new Object[]{offset, limit}, phoneRowMapper);

        return getPhoneColors(phones);
    }

    public int getStockByPhoneId(Long phoneId) {
        return jdbcTemplate.queryForObject(GET_STOCK_BY_PHONE_ID, Integer.class, phoneId);
    }

    public int getProductCount(String searchQuery) {
        String query = getSqlQuery(null, null, searchQuery, true);
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    @Transactional
    public void updateProductStock(Long phoneId, int stock) {
        int originalStock = getStockByPhoneId(phoneId);
        jdbcTemplate.update(UPDATE_STOCKS_SQL, originalStock - stock, phoneId);
    }

    private List<Phone> getPhoneColors(List<Phone> phones) {
        ProductRowCallbackHandler<Phone> handler = new PhoneRowCallbackHandler();
        for (Phone phone : phones) {
            jdbcTemplate.query(JOIN_COLORS_TABLE_SQL, new Object[]{phone.getId()}, handler);
        }
        return handler.getResults();
    }

    private String getSqlQuery(SortField sortField,
                               SortOrder sortOrder,
                               String searchQuery,
                               boolean getCount) {
        StringBuilder query;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            query = getSqlQueryWithSearch(sortField, sortOrder, searchQuery, getCount);
        } else {
            if (getCount) {
                query = new StringBuilder(GET_COUNT_FIND_ALL_SQL);
            } else {
                query = new StringBuilder(FIND_ALL_SQL);
            }
            if (sortField != null) {
                query.append(String.format(ORDER_BY_SQL, sortField.getColumnName(), sortOrder.name()));
            }
        }
        return query.toString();
    }

    private StringBuilder getSqlQueryWithSearch(SortField sortField,
                                                SortOrder sortOrder,
                                                String searchQuery,
                                                boolean getCount) {
        StringBuilder query;
        if (getCount) {
            query = new StringBuilder(GET_COUNT_FIND_ALL_SQL);
        } else {
            query = new StringBuilder(FIND_ALL_WITH_FILTER_SQL);
        }
        List<String> splitQuery = Arrays.stream(searchQuery
                        .toLowerCase()
                        .split("\\s+"))
                .toList();
        if (!splitQuery.isEmpty()) {
            query.append("WHERE ");
        }
        for (String word : splitQuery) {
            if (isNumeric(word)) {
                Object[] args = new Object[4];
                Arrays.fill(args, word);
                query.append(String.format(FILTER_WITH_NUMBER_PARAMETER_SQL, args));
            } else {
                Object[] args = new Object[3];
                Arrays.fill(args, escapeString(word));
                query.append(String.format(FILTER_WITH_STRING_PARAMETER_SQL, args));
            }
            query.append("OR ");
        }
        query.replace(query.lastIndexOf("OR"), query.length(), "");
        if (sortField != null) {
            query.append(String.format(ORDER_BY_SQL, sortField.getColumnName(), sortOrder.name()));
        }
        if (!getCount) {
            query.append(OFFSET_LIMIT_SQL);
        }
        return query;
    }

    private String escapeString(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("'", "''");
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return PATTERN.matcher(strNum).matches();
    }

    private void setStatementParameters(PreparedStatement statement, Phone phone) {
        try {
            statement.setString(1, phone.getBrand());
            statement.setString(2, phone.getModel());
            statement.setObject(3, phone.getPrice());
            statement.setObject(4, phone.getDisplaySizeInches());
            statement.setInt(5, phone.getWeightGr());
            statement.setObject(6, phone.getLengthMm());
            statement.setObject(7, phone.getWidthMm());
            statement.setObject(8, phone.getHeightMm());
            statement.setObject(9, phone.getAnnounced());
            statement.setString(10, phone.getDeviceType());
            statement.setString(11, phone.getOs());
            statement.setString(12, phone.getDisplayResolution());
            statement.setInt(13, phone.getPixelDensity());
            statement.setString(14, phone.getDisplayTechnology());
            statement.setObject(15, phone.getBackCameraMegapixels());
            statement.setObject(16, phone.getFrontCameraMegapixels());
            statement.setObject(17, phone.getRamGb());
            statement.setObject(18, phone.getInternalStorageGb());
            statement.setObject(19, phone.getBatteryCapacityMah());
            statement.setObject(20, phone.getTalkTimeHours());
            statement.setObject(21, phone.getStandByTimeHours());
            statement.setString(22, phone.getBluetooth());
            statement.setString(23, phone.getPositioning());
            statement.setString(24, phone.getImageUrl());
            statement.setString(25, phone.getDescription());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
