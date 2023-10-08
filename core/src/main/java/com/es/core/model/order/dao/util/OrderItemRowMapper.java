package com.es.core.model.order.dao.util;

import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.dao.PhoneDao;
import com.es.core.model.phone.entity.Phone;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class OrderItemRowMapper implements RowMapper<OrderItem> {
    @Resource
    private PhoneDao phoneDao;

    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItem orderItem = new OrderItem();
        long phoneId = resultSet.getLong("phoneId");

        Optional<Phone> phone = phoneDao.get(phoneId);
        phone.ifPresent(orderItem::setPhone);

        orderItem.setQuantity(resultSet.getLong("quantity"));
        return orderItem;
    }
}
