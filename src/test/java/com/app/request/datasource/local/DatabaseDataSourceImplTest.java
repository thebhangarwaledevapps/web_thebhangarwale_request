package com.app.request.datasource.local;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DatabaseDataSourceImplTest {

    @Autowired
    private DatabaseDataSourceImpl databaseDataSourceImpl;

    @Test
    void test_saveCustomerRequest(){

        String customerId = "customerId";
        List<Long> requestIds = Arrays.asList(2l);

        databaseDataSourceImpl.saveCustomerRequest(
                customerId,
                databaseDataSourceImpl.findByCustomerId(customerId)
                        .stream()
                        .filter(requestItem -> !requestIds.contains(requestItem.getRequestItemId()))
                        .collect(Collectors.toList())
        );

    }

}