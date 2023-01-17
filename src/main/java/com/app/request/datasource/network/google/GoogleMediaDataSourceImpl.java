package com.app.request.datasource.network.google;

import com.app.request.datasource.network.MediaUploadDataSource;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.Details;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.UserCredentials;
import com.google.photos.library.v1.PhotosLibraryClient;
import com.google.photos.library.v1.PhotosLibrarySettings;
import com.google.photos.library.v1.proto.NewMediaItem;
import com.google.photos.library.v1.upload.UploadMediaItemRequest;
import com.google.photos.library.v1.upload.UploadMediaItemResponse;
import com.google.photos.library.v1.util.NewMediaItemFactory;
import com.google.photos.types.proto.MediaItem;
import com.google.rpc.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("dev")
public class GoogleMediaDataSourceImpl implements MediaUploadDataSource {

    @Autowired
    private Environment environment;

    @Autowired
    private GooglePhotosClient googlePhotosClient;

    @Override
    public List<MediaItem> uploadMedia(List<MultipartFile> multipartFiles) {
        try {
            PhotosLibraryClient client = getPhotosLibraryClient();
            List<NewMediaItem> medias = multipartFiles.stream().map(multipartFile -> {
                RandomAccessFile file = null;
                NewMediaItem newMediaItem = null;
                try {
                    file = new RandomAccessFile(multipartFile.getName(), "rw");
                    file.write(multipartFile.getBytes());
                    UploadMediaItemRequest uploadRequest = UploadMediaItemRequest.newBuilder()
                            .setMimeType(multipartFile.getContentType())
                            .setDataFile(file)
                            .build();
                    UploadMediaItemResponse uploadResponse = client.uploadMediaItem(uploadRequest);
                    file.close();
                    if (!uploadResponse.getError().isPresent()) {
                        String uploadToken = uploadResponse.getUploadToken().get();
                        newMediaItem = NewMediaItemFactory
                                .createNewMediaItem(uploadToken, multipartFile.getName(), multipartFile.getName());
                    }
                } catch (Exception e) {}
                return newMediaItem;
            }).collect(Collectors.toList());
            return getCustomerMediaByMediaId(
                    client.batchCreateMediaItems(
                            environment.getProperty("google.photo.album-id"),
                            medias
                    ).getNewMediaItemResultsList()
                            .stream()
                            .filter(newMediaItemResult -> newMediaItemResult.getStatus().getCode() == Code.OK_VALUE)
                            .map(newMediaItemResult -> newMediaItemResult.getMediaItem().getId())
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            return null;
        }
    }

    private PhotosLibraryClient getPhotosLibraryClient() throws IOException {
        final Details details = GoogleClientSecrets.load(
                GsonFactory.getDefaultInstance(),
                new InputStreamReader(new FileInputStream(getClass()
                        .getClassLoader()
                        .getResource("credentials.json")
                        .getPath()))
        ).getDetails();
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("client_id", details.getClientId());
        hashMap.put("client_secret", details.getClientSecret());
        hashMap.put("refresh_token", environment.getProperty("google.photo.refresh-token"));
        hashMap.put("grant_type", "refresh_token");
        return PhotosLibraryClient.initialize(PhotosLibrarySettings.newBuilder()
                .setCredentialsProvider(
                        FixedCredentialsProvider.create(
                                UserCredentials.newBuilder()
                                        .setClientId(details.getClientId())
                                        .setClientSecret(details.getClientSecret())
                                        .setRefreshToken(
                                                googlePhotosClient
                                                        .getRefreshToken(
                                                                hashMap
                                                        )
                                                        .getAccess_token()
                                        )
                                        .build()
                        ))
                .build());
    }

    public List<MediaItem> getCustomerMediaByMediaId(List<String> mediaIds) {
        try {
            return getPhotosLibraryClient()
                    .batchGetMediaItems(mediaIds)
                    .getMediaItemResultsList()
                    .stream()
                    .filter(newMediaItemResult -> newMediaItemResult.getStatus().getCode() == Code.OK_VALUE)
                    .map(newMediaItemResult -> newMediaItemResult.getMediaItem())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean isMediaDeletedByMediaId(List<String> mediaIds) {
        try {
            getPhotosLibraryClient()
                    .batchRemoveMediaItemsFromAlbum(
                            environment.getProperty("google.photo.album-id"),
                            mediaIds
                    );
            return true;
        } catch (Exception e) {
            return null;
        }
    }

}
