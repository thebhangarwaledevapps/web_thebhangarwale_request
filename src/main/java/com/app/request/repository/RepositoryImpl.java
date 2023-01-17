package com.app.request.repository;

import com.app.request.datasource.local.DatabaseDataSource;
import com.app.request.datasource.network.NetworkDataSource;
import com.app.request.datasource.network.MediaUploadDataSource;
import com.app.request.datasource.network.google.GoogleMediaDataSourceImpl;
import com.app.request.entity.*;
import com.app.request.exception.NoRequestItemFoundException;
import com.app.request.exception.SomethingWentWrongException;
import com.app.request.result.Result;
import com.app.request.result.ServerError;
import com.app.request.result.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class RepositoryImpl implements Repository {

    @Autowired
    private NetworkDataSource networkDataSourceImpl;

    @Autowired
    private MediaUploadDataSource mediaUploadDataSource;

    @Autowired
    private DatabaseDataSource databaseDataSourceImpl;

    @Override
    public Result<Boolean> addRequestInBucket(long itemId, int itemQuantity, MultipartFile[] mediaList, String customerId) {
        final BhangarTypeAndPrice bhangarTypeAndPrice = networkDataSourceImpl.getBhangarItemInfo(itemId);
        final List<Media> mediaItems = mediaUploadDataSource
                .uploadMedia(Arrays.asList(mediaList))
                .stream()
                .map(mediaItem -> new Media(
                        mediaItem.getId(),
                        mediaItem.getBaseUrl(),
                        mediaItem.getMimeType()
                )).collect(Collectors.toList());
        return Optional.ofNullable(databaseDataSourceImpl.saveCustomerRequest(
                customerId,
                bhangarTypeAndPrice.getBhangarType(),
                itemQuantity,
                bhangarTypeAndPrice.getBhangarPrice(),
                bhangarTypeAndPrice.getBhangarUnit(),
                bhangarTypeAndPrice.getBhangarPrice() * itemQuantity,
                mediaItems
        )).map((Function<Customer, Result<Boolean>>) customer -> new Success<>(true))
                .orElseGet(() -> new ServerError(new SomethingWentWrongException()));
    }

    @Override
    public List<RequestItem> getCustomerRequests(String customerId) {
        if (mediaUploadDataSource instanceof GoogleMediaDataSourceImpl) {
            List<String> mediaIds = databaseDataSourceImpl.findAllMediaIdByCustomerId(customerId);
            if (mediaIds != null && !mediaIds.isEmpty()) {
                GoogleMediaDataSourceImpl googleMediaUploadDataSource
                        = (GoogleMediaDataSourceImpl) mediaUploadDataSource;
                databaseDataSourceImpl.saveAll(
                        googleMediaUploadDataSource
                                .getCustomerMediaByMediaId(mediaIds)
                                .stream()
                                .map(mediaItem -> new Media(
                                        mediaItem.getId(),
                                        mediaItem.getBaseUrl(),
                                        mediaItem.getMimeType()
                                ))
                                .collect(Collectors.toList())
                );
            }
        }
        return databaseDataSourceImpl.findByCustomerId(customerId);
    }

    @Override
    public Boolean deleteMediaById(String customerId, long requestId, List<String> mediaIds) {
        /*Optional.ofNullable(mediaUploadDataSource.isMediaDeletedByMediaId(mediaIds))
                .map(booleanResult -> {
                    requestItemDao
                            .findByCustomerIdAndRequestItemId(customerId, requestId)
                            .map(request -> {
                                request.getItem().getMedias().stream().map(media -> {
                                    if (mediaIds.contains(media.getMediaId())) {
                                        media.setMediaPath(null);
                                        media.setMimeType(null);
                                    }
                                    return media;
                                });
                                return request;
                            });
                    return true;
                });*/
        return true;
    }

    @Override
    public int getAvailableMediaListSizeForRequestItem(long requestItemId) {
        /*final AtomicInteger size = new AtomicInteger(0);
        requestItemDao
                .findById(requestItemId)
                .ifPresent(requestItem -> {
                    size.set(requestItem.getItem().getMedias().size());
                });
        return size.get();*/
        return 0;
    }

    @Override
    public RequestItem updateRequestInBucket(long requestItemId, int itemQuantity, MultipartFile[] mediaList, String customerId) throws NoRequestItemFoundException {
        /*return requestItemDao
                .findById(requestItemId)
                .map(requestItem -> {
                    requestItem.getItem().setItemQuantity(itemQuantity);
                    List<Media> mediaItems = mediaUploadDataSource
                            .uploadMedia(Arrays.asList(mediaList))
                            .stream()
                            .map(mediaItem -> new Media(
                                    mediaItem.getId(),
                                    mediaItem.getBaseUrl(),
                                    mediaItem.getMimeType()
                            )).collect(Collectors.toList());
                    if (requestItem.getItem().getMedias() != null) {
                        requestItem.getItem().getMedias().addAll(mediaItems);
                    } else {
                        requestItem.getItem().setMedias(mediaItems);
                    }
                    return requestItemDao.save(requestItem);
                })
                .orElseThrow(() -> new NoRequestItemFoundException());*/
        return null;
    }

    @Override
    public List<RequestItem> deleteRequestsInBucket(String customerId, List<Long> requestIds) {
        /*requestItemDao.deleteByCustomerIdAndRequestItemIdIn(customerId,requestIds);
        return getCustomerRequests(customerId);*/
        return null;
    }
}
