package com.app.request.datasource.network.google;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GoogleMediaDataSourceImplTest {

    @Autowired
    GoogleMediaDataSourceImpl googleImageUploadDataSource;

    @Test
    void deleteMediaByMediaId() {
        googleImageUploadDataSource.deleteMediaByMediaId(Arrays.asList("AOqewoKN9VcLo8xq_wKOb0COJ4fMMGwF-jUEJ-TbEbWZS0sfpn8JI8bVvtWNDazCcBZSJK91Uod4O5BXsvXp1pVoKn6cuqPwrg"));

    }

}