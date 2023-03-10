package com.app.request.repository;

import com.app.request.entity.Media;
import com.app.request.entity.RequestItem;
import com.app.request.exception.NoRequestItemFoundException;
import com.app.request.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface Repository {

    Boolean addRequestInBucket(long itemId, int itemQuantity, MultipartFile[] mediaList, String customerId);

    List<RequestItem> getCustomerRequests(String customerId);

    List<Media> deleteMedia(String customerId, long requestId, List<String> mediaIds);

    int getAvailableMediaListSizeForRequestItem(long requestItemId);

    RequestItem updateRequestInBucket(long requestItemId, int itemQuantity, String customerId);

    List<RequestItem> deleteRequestsInBucket(String customerId,List<Long> requestIds);

}
