package com.app.request.service;

import com.app.request.entity.Media;
import com.app.request.entity.RequestItem;
import com.app.request.exception.*;
import com.app.request.util.internalization.Translator;
import com.app.request.repository.Repository;
import com.app.request.result.ClientError;
import com.app.request.result.Result;
import com.app.request.result.ServerError;
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

    @Autowired
    private Translator translator;

    public Result addRequestInBucket(long itemId, int itemQuantity, MultipartFile[] mediaList, String customerId) {
        Result result = null;
        try {
            if (itemQuantity <= 0)
                throw new InvalidItemQuantityException(translate("error_valid_address"));
            if (mediaList != null && mediaList.length > 3)
                throw new InvalidMediaSizeException(translate("error_valid_upload"));
            result = Optional.ofNullable(repositoryImpl.addRequestInBucket(itemId, itemQuantity, mediaList, customerId)
                    ? true
                    : null
            ).map((Function<Boolean, Result<String>>) aBoolean -> new Success<>(translate("success_request_save")))
                    .orElse(new ServerError(new Exception(translate("error_something_went_wrong"))));
        } catch (InvalidItemQuantityException | InvalidMediaSizeException e) {
            result = new ClientError(e);
        }
        return result;
    }

    public Result getCustomersRequestInBucket(String customerId) {
        List<RequestItem> requestItems = repositoryImpl.getCustomerRequests(customerId);
        return Optional.ofNullable(requestItems != null && !requestItems.isEmpty()
                ? requestItems
                : null)
                .map((Function<List<RequestItem>, Result<List<RequestItem>>>) Success::new)
                .orElse(new ClientError(new Exception(translate("error_no_request_found"))));
    }

    public Result deleteMediaById(String customerId, long requestId, List<String> mediaIds) {
       return Optional.ofNullable(repositoryImpl.deleteMedia(customerId, requestId, mediaIds))
               .map((Function<List<Media>, Result<List<Media>>>) mediaList -> new Success<>(mediaList))
               .orElse(new ServerError(new Exception(translate("error_something_went_wrong"))));
    }

    public Result updateRequestInBucket(long requestItemId, int itemQuantity, MultipartFile[] mediaList, String customerId) {
        /*try {
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
        }*/
        return null;
    }

    public Result deleteRequestsInBucket(String customerId, List<Long> requestIds) {
        List<RequestItem> requestItems = repositoryImpl.deleteRequestsInBucket(customerId, requestIds);
        return Optional.ofNullable(requestItems != null && !requestItems.isEmpty()
                ? requestItems
                : null)
                .map((Function<List<RequestItem>, Result<List<RequestItem>>>) Success::new)
                .orElse(new ClientError(new Exception(translate("error_no_request_found"))));
    }

    private String translate(String msg) {
        return translator.toLocale(msg);
    }

}
