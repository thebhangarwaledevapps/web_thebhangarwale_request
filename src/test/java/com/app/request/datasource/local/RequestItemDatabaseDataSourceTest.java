package com.app.request.datasource.local;

import com.app.request.datasource.local.dao.CustomerDao;
import com.app.request.datasource.local.dao.RequestItemDao;
import com.app.request.entity.Customer;
import com.app.request.entity.Item;
import com.app.request.entity.Media;
import com.app.request.entity.RequestItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
class RequestItemDatabaseDataSourceTest {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    RequestItemDao requestItemDao;

    @Test
    public void test_init(){}

    @Test
    public void test_save_request() {
        customerDao.deleteAll();
        requestItemDao.deleteAll();
        customerDao.save(new Customer(
                "customerId",
                Arrays.asList(
                        new RequestItem(
                                new Item(
                                        "itemName1",
                                        3,
                                        200,
                                        "kg",
                                        3*200,
                                        Arrays.asList(
                                                new Media(
                                                        "mediaId_1",
                                                        "mediaPath_1",
                                                        "mimeType_1"
                                                ),
                                                new Media(
                                                        "mediaId_2",
                                                        "mediaPath_2",
                                                        "mimeType_2"
                                                ),
                                                new Media(
                                                        "mediaId_3",
                                                        "mediaPath_3",
                                                        "mimeType_3"
                                                )
                                        )

                                )
                        )
                )

        ));
        customerDao.save(new Customer(
                "customerId2",
                Arrays.asList(
                        new RequestItem(
                                new Item(
                                        "itemName2",
                                        3,
                                        200,
                                        "kg",
                                        3*300,
                                        Arrays.asList(
                                                new Media(
                                                        "mediaId_21",
                                                        "mediaPath_21",
                                                        "mimeType_21"
                                                ),
                                                new Media(
                                                        "mediaId_22",
                                                        "mediaPath_22",
                                                        "mimeType_22"
                                                ),
                                                new Media(
                                                        "mediaId_23",
                                                        "mediaPath_23",
                                                        "mimeType_23"
                                                )
                                        )
                                )
                        )
                )
        ));
    }

    /*@Test
    public void test_findAllRequestItemBasedOnCustomerId() {
        List<RequestItem> requestItem = requestItemDao.findAllByCustomerId(
                "customerId"
        );
        System.out.println(requestItem);
    }*/

    /*@Test
    public void test_findByRequestIdCustomerId() {
        RequestItem requestItem = requestItemDao.findByCustomerIdAndRequestItemId(
                "customerId",
                2
        );
        System.out.println(requestItem);
    }

    @Test
    public void test_findByCustomerId() {
        List<String> requestItem = requestItemDao.findByCustomerId(
                "customerId"
        );
        System.out.println(requestItem);
    }*/


}