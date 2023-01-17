package com.app.request.service;

import com.app.request.entity.RequestItem;
import com.app.request.exception.*;
import com.app.request.repository.Repository;
import com.app.request.result.ClientError;
import com.app.request.result.Result;
import com.app.request.result.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class RequestService {

    @Autowired
    private Repository repositoryImpl;

    public Result addRequestInBucket(long itemId, int itemQuantity, MultipartFile[] mediaList, String customerId) {
        try {
            if (itemQuantity <= 0)
                throw new InvalidItemQuantityException();
            if (mediaList != null && mediaList.length > 3)
                throw new InvalidMediaSizeException();
            return Optional.ofNullable(
                    repositoryImpl.addRequestInBucket(itemId, itemQuantity, mediaList, customerId)
            ).map(requestItem -> new Success("Request is Saved Successfully.")
            ).get();
        } catch (InvalidItemQuantityException e) {
            return new ClientError(e.getMessage());
        } catch (InvalidMediaSizeException e) {
            return new ClientError(e.getMessage());
        }
    }

    public Result getCustomersRequestInBucket(String customerId) {
        try {
            List<RequestItem> requestItems = repositoryImpl.getCustomerRequests(customerId);
            if (requestItems == null || requestItems.isEmpty())
                throw new NoRequestFoundException();
            return new Success(requestItems);
        } catch (NoRequestFoundException e) {
            return new ClientError(e.getMessage());
        }
    }

    public Result deleteMediaById(String customerId, long requestId, List<String> mediaIds) {
        try {
            return Optional.ofNullable(repositoryImpl.deleteMediaById(customerId, requestId, mediaIds))
                    .map((Function<Boolean, Result>) isTrue -> new Success("Media is Deleted Successfully."))
                    .orElseThrow(UnableToDeleteMediaException::new);
        } catch (UnableToDeleteMediaException e) {
            return new ClientError(e.getMessage());
        }
    }

    public Result updateRequestInBucket(long requestItemId, int itemQuantity, MultipartFile[] mediaList, String customerId) {
        try {
            if (itemQuantity <= 0)
                throw new InvalidItemQuantityException();
            int availableMediaSize = repositoryImpl.getAvailableMediaListSizeForRequestItem(requestItemId);
            if (mediaList != null && (mediaList.length + availableMediaSize) > 3)
                throw new InvalidMediaSizeException();
            return Optional.ofNullable(
                    repositoryImpl.updateRequestInBucket(requestItemId, itemQuantity, mediaList, customerId)
            ).map(requestItem -> new Success("Request is Updated Successfully.")
            ).get();
        } catch (InvalidItemQuantityException e) {
            return new ClientError(e.getMessage());
        } catch (InvalidMediaSizeException e) {
            return new ClientError(e.getMessage());
        } catch (NoRequestItemFoundException e) {
            return new ClientError(e.getMessage());
        }
    }

    public Result deleteRequestsInBucket(String customerId, List<Long> requestIds) {
        return new Success(repositoryImpl.deleteRequestsInBucket(customerId, requestIds));
    }

}
