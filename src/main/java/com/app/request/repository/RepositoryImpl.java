package com.app.request.repository;

import com.app.request.datasource.local.DatabaseDataSource;
import com.app.request.datasource.network.NetworkDataSource;
import com.app.request.datasource.network.MediaUploadDataSource;
import com.app.request.datasource.network.google.GoogleMediaDataSourceImpl;
import com.app.request.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Function;
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
    public Boolean addRequestInBucket(long itemId, int itemQuantity, MultipartFile[] mediaList, String customerId) {
        final BhangarTypeAndPrice bhangarTypeAndPrice = networkDataSourceImpl.getBhangarItemInfo(itemId);
        return Optional.ofNullable(databaseDataSourceImpl.findByCustomerId(customerId))
                .map(requestItems -> {
                    requestItems.add(
                            new RequestItem(
                                    new Item(
                                            bhangarTypeAndPrice.getBhangarType(),
                                            itemQuantity,
                                            bhangarTypeAndPrice.getBhangarPrice(),
                                            bhangarTypeAndPrice.getBhangarUnit(),
                                            bhangarTypeAndPrice.getBhangarPrice() * itemQuantity,
                                            mediaUploadDataSource
                                                    .uploadMedia(Arrays.asList(mediaList))
                                                    .stream()
                                                    .map(mediaItem -> new Media(
                                                            mediaItem.getId(),
                                                            mediaItem.getBaseUrl(),
                                                            mediaItem.getMimeType()
                                                    )).collect(Collectors.toList())
                                    )
                            )
                    );
                    return Optional.ofNullable(
                            databaseDataSourceImpl.saveCustomerRequest(
                                    customerId,
                                    requestItems
                            )
                    ).isPresent();
                })
                .orElseGet(() -> Optional.ofNullable(databaseDataSourceImpl.saveCustomerRequest(
                        customerId,
                        bhangarTypeAndPrice.getBhangarType(),
                        itemQuantity,
                        bhangarTypeAndPrice.getBhangarPrice(),
                        bhangarTypeAndPrice.getBhangarUnit(),
                        bhangarTypeAndPrice.getBhangarPrice() * itemQuantity,
                        mediaUploadDataSource
                                .uploadMedia(Arrays.asList(mediaList))
                                .stream()
                                .map(mediaItem -> new Media(
                                        mediaItem.getId(),
                                        mediaItem.getBaseUrl(),
                                        mediaItem.getMimeType()
                                )).collect(Collectors.toList())
                )).isPresent());
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
    public List<Media> deleteMedia(String customerId, long requestId, List<String> mediaIds) {
        final List<Media> mediaList = databaseDataSourceImpl.findMediaByCustomerIdAndRequestId(
                customerId,
                requestId
        );
        return Optional.ofNullable(mediaList != null && !mediaList.isEmpty() ? mediaList : null)
                .map(mediaList1 -> mediaList1.stream().map(Media::getMediaId).collect(Collectors.toList()))
                .map(mediaIds1 -> mediaUploadDataSource.deleteMediaByMediaId(mediaIds1) ? true : null)
                .map(isMediaDeleted -> {
                    final int rowEffected = databaseDataSourceImpl.deleteMediaByCustomerIdRequestIdAndMediaId(
                            customerId,
                            requestId,
                            mediaIds
                    );
                    return rowEffected > 0 ? rowEffected : null;
                })
                .map(rowEffected -> databaseDataSourceImpl.findMediaByCustomerIdAndRequestId(
                        customerId,
                        requestId
                ))
                .orElse(null);
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
    public RequestItem updateRequestInBucket(long requestItemId, int itemQuantity, String customerId) {
        final int rowEffected = databaseDataSourceImpl.updateCustomerRequest(
                itemQuantity,
                customerId,
                requestItemId
        );
        return Optional.ofNullable(rowEffected > 0 ? rowEffected : null)
                .map(integer -> databaseDataSourceImpl.findByCustomerIdAndRequestItemId(customerId, requestItemId))
                .orElse(null);
    }

    @Override
    public List<RequestItem> deleteRequestsInBucket(String customerId, List<Long> requestIds) {
        return Optional.ofNullable(databaseDataSourceImpl.saveCustomerRequest(
                customerId,
                databaseDataSourceImpl.findByCustomerId(customerId)
                        .stream()
                        .filter(requestItem -> !(requestIds.contains(requestItem.getRequestItemId())))
                        .collect(Collectors.toList())
        )).map(aLong -> getCustomerRequests(customerId)).orElse(null);
    }
}
