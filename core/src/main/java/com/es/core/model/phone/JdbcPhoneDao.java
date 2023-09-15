package com.es.core.model.phone;

import com.es.core.model.phone.handler.PhoneRowCallbackHandler;
import com.es.core.model.phone.handler.ProductRowCallbackHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    private static final String FIND_ALL_SQL =
            "SELECT * FROM (SELECT * FROM phones OFFSET %d LIMIT %d) as phones_partial " +
                    "LEFT JOIN phone2color on phones_partial.id = phone2color.phoneId " +
                    "LEFT JOIN colors on colors.id = phone2color.colorId";

    public Optional<Phone> get(final Long key) {
        throw new UnsupportedOperationException("TODO");
    }

    public void save(final Phone phone) {
        throw new UnsupportedOperationException("TODO");
    }

    public List<Phone> findAll(int offset, int limit) {
        ProductRowCallbackHandler<Phone> handler = new PhoneRowCallbackHandler();
        String query = String.format(FIND_ALL_SQL, offset, limit);
        jdbcTemplate.query(query, handler);
        return handler.getResults();
    }
}
