package com.app.request;

import com.app.request.datasource.local.dao.CustomerDao;
import com.app.request.datasource.local.dao.ItemDao;
import com.app.request.datasource.local.dao.MediaDao;
import com.app.request.datasource.local.dao.RequestItemDao;
import com.app.request.entity.Customer;
import com.app.request.entity.Item;
import com.app.request.entity.RequestItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class TestTables {

    @Autowired
    RequestItemDao requestItemDao;

    @Autowired
    MediaDao mediaDao;

    @Autowired
    ItemDao itemDao;

    @Autowired
    CustomerDao customerDao;

    @Test
    void test() {
        customerDao.save(
                new Customer("Bhangarwale_00001",
                        Arrays.asList(
                                new RequestItem(new Item("loha", 20, 20, "kg", 400, null))
                        )
                ));
    }

}
