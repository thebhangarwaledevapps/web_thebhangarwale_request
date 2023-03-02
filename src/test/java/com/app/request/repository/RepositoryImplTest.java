package com.app.request.repository;

import com.app.request.datasource.network.NetworkDataSource;
import com.app.request.entity.BhangarTypeAndPrice;
import com.app.request.entity.Media;
import com.app.request.entity.RequestItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class RepositoryImplTest {

    @Autowired
    private RepositoryImpl repository;

    @MockBean
    private NetworkDataSource networkDataSourceImpl;

    @Test
    void addRequestInBucket() throws Exception{
        given(networkDataSourceImpl.getBhangarItemInfo(1L)).willReturn(new BhangarTypeAndPrice(
                1L,
                "loha",
                "kg",
                33.3
        ));

        ClassLoader classLoader = getClass().getClassLoader();

        String resourceName1 = "dr._A.P.J.Abdul_Kalam.jpeg";
        File file1 = new File(classLoader.getResource(resourceName1).getFile());
        MultipartFile multipartFile1 = new MockMultipartFile(resourceName1, resourceName1, "image/jpeg", new FileInputStream(file1));

        String resourceName2 = "mahatma_gandhi.jpeg";
        File file2 = new File(classLoader.getResource(resourceName2).getFile());
        MultipartFile multipartFile2 = new MockMultipartFile(resourceName2, resourceName2, "image/jpeg", new FileInputStream(file2));

        String resourceName3 = "shree_ram.jpeg";
        File file3 = new File(classLoader.getResource(resourceName3).getFile());
        MultipartFile multipartFile3 = new MockMultipartFile(resourceName3, resourceName3, "image/jpeg", new FileInputStream(file3));

        Boolean requestedAdded = repository.addRequestInBucket(
                1L,
                20,
                (MultipartFile[]) Arrays.asList(multipartFile1, multipartFile2, multipartFile3).toArray(),
                "user1"
        );

        Assertions.assertTrue(requestedAdded);

    }

    @Test
    void deleteMedia() {
        List<RequestItem> requestItems = repository.getCustomerRequests("user1");
        RequestItem requestItem = requestItems.get(0);
        List<String> mediaIds = requestItem.getItem().getMedias().subList(1,2).stream().map(media -> media.getMediaId()).collect(Collectors.toList());
        List<Media> mediaList = repository.deleteMedia("user1",requestItem.getRequestItemId(),mediaIds);
        Assertions.assertEquals(2,mediaList.size());
    }
}