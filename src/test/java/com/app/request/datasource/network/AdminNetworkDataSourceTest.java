package com.app.request.datasource.network;

import com.app.request.entity.BhangarTypeAndPrice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminNetworkDataSourceTest {

    @Autowired
    com.app.request.datasource.network.client.AdminClient AdminClient;

    @Test
    void test_getBhangarItemInfo() {
        try {
            BhangarTypeAndPrice bhangarTypeAndPrice = AdminClient.getBhangarItemInfo(454L);
            System.out.println(bhangarTypeAndPrice);
        }catch (Exception e){
            System.out.println(e);
        }

    }

}