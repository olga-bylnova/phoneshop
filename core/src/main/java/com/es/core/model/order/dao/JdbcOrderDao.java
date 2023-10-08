package com.es.core.model.order.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
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
import java.util.List;
import java.util.Optional;

import static com.es.core.model.phone.util.StringUtilSqlQuery.*;

@Component
public class JdbcOrderDao implements OrderDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private OrderItemDao orderItemDao;

    private final RowMapper<Order> orderRowMapper = new BeanPropertyRowMapper<>(Order.class);

    @Override
    @Transactional
    public void save(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SAVE_ORDER_SQL, Statement.RETURN_GENERATED_KEYS);
            setStatementParameters(statement, order);
            return statement;
        }, keyHolder);

        order.setId((Long) keyHolder.getKey());
        order.getOrderItems().forEach(orderItem -> orderItemDao.save(orderItem));
    }

    @Override
    @Transactional
    public Optional<Order> getOrderBySecureId(String secureId) {
        List<Order> orders = jdbcTemplate.query(GET_ORDER_BY_SECURE_ID_SQL, new Object[]{secureId}, orderRowMapper);
        orders.stream()
                .findAny()
                .ifPresent(order -> order.setOrderItems(orderItemDao.getOrderItemsByOrderId(order.getId(), order)));
        return orders.stream().findAny();
    }

    @Transactional
    public List<Order> getOrders() {
        List<Order> orders = jdbcTemplate.query(GET_ORDERS_SQL, orderRowMapper);
        orders.forEach(order -> order.setOrderItems(orderItemDao.getOrderItemsByOrderId(order.getId(), order)));
        return orders;
    }

    @Transactional
    public Optional<Order> getOrderById(Long id) {
        List<Order> orders = jdbcTemplate.query(GET_ORDER_BY_ID_SQL, new Object[]{id}, orderRowMapper);
        orders.stream()
                .findAny()
                .ifPresent(order -> order.setOrderItems(orderItemDao.getOrderItemsByOrderId(order.getId(), order)));
        return orders.stream().findAny();
    }

    public void updateOrderStatusByOrderId(Long id, OrderStatus status) {
        jdbcTemplate.update(UPDATE_ORDER_STATUS_SQL, status.name(), id);
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
            statement.setString(9, order.getSecureId());
            statement.setString(10, order.getAdditionalInfo());
            statement.setObject(11, order.getOrderDate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
