package com.es.core.model.phone;

import com.es.core.model.phone.dao.JdbcPhoneDao;
import com.es.core.model.phone.entity.Phone;
import com.es.core.model.phone.entity.SortField;
import com.es.core.model.phone.entity.SortOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

@ContextConfiguration("classpath*:/applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcPhoneDaoIntTest {
    @Resource
    private JdbcPhoneDao jdbcPhoneDao;
    private final static int OFFSET = 0;
    private final static int LIMIT = 10;
    private final static int PHONE_LIST_SIZE = 10;
    private final static Long PHONE_ID = 1000L;
    private Phone phone;

    @Before
    public void setUp() {
        phone = new Phone("Sams", "Sams Access",
                null, null, 156, null, null, null,
                null, null, null,
                null, "320 x  240", 174, "TFT", null, null, null,
                null, 1300, null, null, "2.0", null,
                "manufacturer/Samsung/Samsung Access.jpg",
                "Samsung Access is TV-capable candybar with 2.3\" QVGA display. It supports MediaFlo which AT&T will use for its mobile TV service.");
    }

    @Test
    public void testFindAll() {
        List<Phone> phones = jdbcPhoneDao.findAll(OFFSET, LIMIT, null, null,
                null);
        Assert.assertEquals(PHONE_LIST_SIZE, phones.size());
    }

    @Test
    public void whenFindAllThenReturnProductsWithNotNullPrice() {
        assertTrue(jdbcPhoneDao.findAll(OFFSET, LIMIT, null, null,
                        null)
                .stream()
                .allMatch(phone -> phone.getPrice() != null));
    }

    @Test
    public void whenGetPhone() {
        Optional<Phone> optional = jdbcPhoneDao.get(PHONE_ID);

        assertNotNull(optional);
    }

    @Test
    public void givenPhoneWithNoIdWhenSaveThenSavePhoneWithId() {
        jdbcPhoneDao.save(phone);

        assertNotNull(phone.getId());
    }

    @Test
    public void givenPriceAndAscReturnProductListSorted() {
        List<Phone> searchResult = jdbcPhoneDao.findAll(OFFSET, LIMIT, SortField.PRICE, SortOrder.ASC, null);

        assertTrue(isListSortedByPriceAsc(searchResult));
    }

    @Test
    public void givenBrandAndAscReturnProductListSorted() {
        List<Phone> searchResult = jdbcPhoneDao.findAll(OFFSET, LIMIT, SortField.BRAND, SortOrder.ASC, null);

        assertTrue(isListSortedByBrandAsc(searchResult));
    }

    @Test
    public void givenPriceAndDescReturnProductListSorted() {
        List<Phone> searchResult = jdbcPhoneDao.findAll(OFFSET, LIMIT, SortField.PRICE, SortOrder.DESC, null);

        assertTrue(isListSortedByPriceDesc(searchResult));
    }

    @Test
    public void givenBrandAndDescReturnProductListSorted() {
        List<Phone> searchResult = jdbcPhoneDao.findAll(OFFSET, LIMIT, SortField.BRAND, SortOrder.DESC, null);

        assertTrue(isListSortedByBrandDesc(searchResult));
    }

    private boolean isListSortedByPriceAsc(List<Phone> phones) {
        for (int i = 0; i < phones.size() - 1; i++) {
            if (phones.get(i).getPrice()
                    .compareTo(phones.get(i + 1).getPrice()) > 0)
                return false;
        }
        return true;
    }

    private boolean isListSortedByBrandAsc(List<Phone> phones) {
        for (int i = 0; i < phones.size() - 1; i++) {
            if (phones.get(i).getBrand().
                    compareTo(phones.get(i + 1).getBrand()) > 0)
                return false;
        }
        return true;
    }

    private boolean isListSortedByPriceDesc(List<Phone> phones) {
        for (int i = 0; i < phones.size() - 1; i++) {
            if (phones.get(i).getPrice()
                    .compareTo(phones.get(i + 1).getPrice()) < 0)
                return false;
        }
        return true;
    }

    private boolean isListSortedByBrandDesc(List<Phone> phones) {
        for (int i = 0; i < phones.size() - 1; i++) {
            if (phones.get(i).getBrand().
                    compareTo(phones.get(i + 1).getBrand()) < 0)
                return false;
        }
        return true;
    }
}
