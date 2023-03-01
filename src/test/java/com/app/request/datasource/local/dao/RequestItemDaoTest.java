package com.app.request.datasource.local.dao;

import com.app.request.entity.RequestItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RequestItemDaoTest {

    @Autowired
    RequestItemDao requestItemDao;

    @Test
    void test_deleteMediaByCustomerIdAndMediaId(){

        try {

            int data = requestItemDao.deleteMediaByCustomerIdAndMediaId(
                    "customerId",
                    4,
                    Arrays.asList(
                            "mediaId_2",
                            "mediaId_3"
                    )
            );

            System.out.println(data);

        }catch (Exception exception){
            System.out.println(exception);
        }


    }


    @Test
    void test_deleteRequestByCustomerIdAndRequestItemId(){

        try {

            /*int data = requestItemDao.deleteRequestByCustomerIdAndRequestItemId(
                    "customerId",
                    Arrays.asList(
                        2L
                    )
            );

            System.out.println(data);*/

        }catch (Exception exception){
            System.out.println(exception);
        }


    }

    @Test
    void test_findAllMediaIdsByCustomerId(){
        List<String> mediaIds = requestItemDao.findAllMediaIdsByCustomerId("customerId2");
        Assertions.assertEquals(3,mediaIds.size());
    }

    @Test
    void test_findAllByCustomerId(){
        //Set<RequestItem> requestItems = requestItemDao.findAllByCustomerId("customerId");
        //System.out.println(requestItems);
    }

}