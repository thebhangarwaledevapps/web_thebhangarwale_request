package com.app.request.datasource.local;

import com.app.request.datasource.local.dao.MediaDao;
import com.app.request.datasource.network.MediaUploadDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class MediaDatabaseDataSourceTest {

    @Autowired
    MediaDao mediaDao;

    @Autowired
    MediaUploadDataSource mediaUploadDataSource;

    @Test
    void test_findAllMediaId() {
        /*try {
            List<String> medias = mediaDao.findAllMediaIdByCustomerId("Bhangarwale_00001");
            System.out.println(medias);
        } catch (Exception ex) {
            System.out.println(ex);
        }*/
    }

    @Test
    void test_delete_media() {
        try {
            List<String> mediaIds = Arrays.asList("7823");
            Optional.ofNullable(mediaUploadDataSource.isMediaDeletedByMediaId(mediaIds))
                    .map(booleanResult -> {
                        mediaDao.deleteAllById(mediaIds);
                        return true;
                    })
                    .orElseThrow(() -> new Exception("Hello"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}