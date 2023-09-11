package com.es.core.model.phone;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@ContextConfiguration("classpath*:/applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcPhoneDaoIntTest {
    @Resource
    private JdbcPhoneDao jdbcPhoneDao;

    @Test
    public void testFindAll() {
        List<Phone> phones = jdbcPhoneDao.findAll(0, 10);
        Assert.assertEquals(10, phones.size());
    }
}
