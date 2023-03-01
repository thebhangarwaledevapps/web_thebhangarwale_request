package com.app.request.repository;

import com.app.request.datasource.local.DatabaseDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;

@SpringBootTest
class RepositoryImplTest {

    @Autowired
    private DatabaseDataSource databaseDataSourceImpl;

    @Test
    void test_deleteMedia(){
        try {
            long count= databaseDataSourceImpl.deleteMediaByCustomerIdRequestIdAndMediaId(
                    "Bhangarwale_00001",
                    3,
                    Arrays.asList(
                            "AOqewoI1PbFp9l9iSwA5KOLZQ2F4AIIn5qvM2P39dIhDw_UrO7JZMrTytoz6gFANFtBVM0NW1xeGXHYD4Irr6fyeQJQN_XMSFg",
                            "AOqewoJ_DJv0I0Y7X0yHM6Yl7B5jOigm64PpZ0MIOeDOTBPPEyR-HzVOsc23sI2m90Sg_t3whuAq-cEVKwtGPWoeCSo4ObgjSQ"
                    )
            );
            System.out.println(count);

        }catch (Exception exception){
            System.out.println(exception);
        }

    }

}