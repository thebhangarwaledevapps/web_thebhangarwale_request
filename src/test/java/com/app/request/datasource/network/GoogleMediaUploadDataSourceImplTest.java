package com.app.request.datasource.network;

import com.app.request.datasource.network.google.GoogleMediaDataSourceImpl;
import com.google.photos.library.v1.PhotosLibraryClient;
import com.google.photos.library.v1.internal.InternalPhotosLibraryClient;
import com.google.photos.types.proto.Album;
import com.google.photos.types.proto.MediaItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class GoogleMediaUploadDataSourceImplTest {

    @Autowired
    GoogleMediaDataSourceImpl googleImageUploadDataSource;

    List<MediaItem> list = null;

    @Test
    public void test_Google_Photo_Client() throws IOException, ExecutionException, InterruptedException {
        ClassLoader classLoader = getClass().getClassLoader();

        String resourceName1 = "dr._A.P.J.Abdul_Kalam.jpeg";
        File file1 = new File(classLoader.getResource(resourceName1).getFile());
        MultipartFile multipartFile1 = new MockMultipartFile(resourceName1,resourceName1,"image/jpeg",new FileInputStream(file1));

        String resourceName2 = "mahatma_gandhi.jpeg";
        File file2 = new File(classLoader.getResource(resourceName2).getFile());
        MultipartFile multipartFile2 = new MockMultipartFile(resourceName2,resourceName2,"image/jpeg",new FileInputStream(file2));

        String resourceName3 = "shree_ram.jpeg";
        File file3 = new File(classLoader.getResource(resourceName3).getFile());
        MultipartFile multipartFile3 = new MockMultipartFile(resourceName3,resourceName3,"image/jpeg",new FileInputStream(file3));

        list = googleImageUploadDataSource.uploadMedia(Arrays.asList(multipartFile1,multipartFile2,multipartFile3));

        Assertions.assertEquals(3,list.size());
        System.out.println(list);

    }

    @Test
    public void test_to_find_proper_image_url_from_google_photos(){

        List<MediaItem> mediaItems = googleImageUploadDataSource.getCustomerMediaByMediaId(Arrays.asList("AOqewoKuERW2pfuBoPvG4RUfQiAHZ99pV7cXtPHcCO65UeSegapq0IXr-uQSAYr-RawNiN1mSj5s3Pt_-KvIy4ucJUzvx0n4pQ"));
        Assertions.assertTrue(!mediaItems.isEmpty());
    }

    @Test
    void getAlbumId(){
        try {
           InternalPhotosLibraryClient.ListAlbumsPagedResponse response = googleImageUploadDataSource.getPhotosLibraryClient().listAlbums();
           System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createAlbum(){
        try {
            PhotosLibraryClient client = googleImageUploadDataSource.getPhotosLibraryClient();
            Album album = client.createAlbum("web_bhangarwale_photos");
            System.out.println(album);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}