package com.es.core.model.order.dao;

import com.es.core.model.order.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static com.es.core.model.phone.util.StringUtil.SAVE_ORDER_SQL;

@Component
public class JdbcOrderDao implements OrderDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SAVE_ORDER_SQL, Statement.RETURN_GENERATED_KEYS);
            setStatementParameters(statement, order);
            return statement;
        }, keyHolder);

        order.setId((Long) keyHolder.getKey());
    }

    private void setStatementParameters(PreparedStatement statement, Order order) {
        try {
            statement.setObject(1, order.getSubtotal());
            statement.setObject(2, order.getDeliveryPrice());
            statement.setObject(3, order.getTotalPrice());
            statement.setString(4, order.getFirstName());
            statement.setString(5, order.getLastName());
            statement.setString(6, order.getDeliveryAddress());
            statement.setString(7, order.getContactPhoneNo());
            statement.setString(8, order.getStatus().name());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
