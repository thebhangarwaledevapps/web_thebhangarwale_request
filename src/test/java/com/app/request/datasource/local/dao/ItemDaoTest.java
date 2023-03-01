package com.app.request.datasource.local.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemDaoTest {

    @Autowired
    ItemDao itemDao;

    @Test
    void test_updateQuantity(){
        int result = itemDao.updateQuantity(5,"customerId2",4);
        assertEquals(1,result);
    }

}