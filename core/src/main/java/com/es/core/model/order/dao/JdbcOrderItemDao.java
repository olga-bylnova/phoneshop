package com.es.core.model.order.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.dao.util.OrderItemRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.es.core.model.phone.util.StringUtilSqlQuery.GET_ORDER_ITEMS_BY_ORDER_ID_SQL;
import static com.es.core.model.phone.util.StringUtilSqlQuery.SAVE_ORDER_ITEM_SQL;

@Component
public class JdbcOrderItemDao implements OrderItemDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private OrderItemRowMapper rowMapper;

    @Override
    @Transactional
    public void save(OrderItem orderItem) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SAVE_ORDER_ITEM_SQL, Statement.RETURN_GENERATED_KEYS);
            setStatementParameters(statement, orderItem);
            return statement;
        }, keyHolder);

        orderItem.setId((Long) keyHolder.getKey());
    }

    @Override
    @Transactional
    public List<OrderItem> getOrderItemsByOrderId(Long orderId, Order order) {
        List<OrderItem> orderItems = jdbcTemplate.query(GET_ORDER_ITEMS_BY_ORDER_ID_SQL, new Object[]{orderId}, rowMapper);
        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        return orderItems;
    }

    private void setStatementParameters(PreparedStatement statement, OrderItem orderItem) {
        try {
            statement.setLong(1, orderItem.getPhone().getId());
            statement.setLong(2, orderItem.getOrder().getId());
            statement.setLong(3, orderItem.getQuantity());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
