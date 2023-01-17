package com.app.request.controller;

import com.app.request.result.ClientError;
import com.app.request.result.Result;
import com.app.request.result.ServerError;
import com.app.request.result.Success;
import com.app.request.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/addRequestInBucket")
    public ResponseEntity<Result> addRequestInBucket(
            @RequestParam long itemId,
            @RequestParam int itemQuantity,
            @RequestParam(name = "media", required = false) MultipartFile[] mediaList,
            @RequestParam String customerId
    ) {
        final Result result = requestService.addRequestInBucket(itemId, itemQuantity, mediaList, customerId);
        return getResultResponseEntity(result);
    }

    @GetMapping("/getCustomersRequestInBucket")
    public ResponseEntity<Result> getCustomersRequestInBucket(
            @RequestParam String customerId
    ) {
        final Result result = requestService.getCustomersRequestInBucket(customerId);
        return getResultResponseEntity(result);
    }

    @GetMapping("/deleteCustomerRequestMedia")
    public ResponseEntity<Result> deleteCustomerRequestMedia(
            @RequestParam String customerId,
            @RequestParam long requestId,
            @RequestParam List<String> mediaIds
    ) {
        final Result result = requestService.deleteMediaById(customerId, requestId, mediaIds);
        return getResultResponseEntity(result);
    }

    @GetMapping("/updateCustomersRequestInBucket")
    public ResponseEntity<Result> updateCustomersRequestInBucket(
            @RequestParam long requestItemId,
            @RequestParam int itemQuantity,
            @RequestParam MultipartFile[] mediaList,
            @RequestParam String customerId
    ) {
        final Result result = requestService.updateRequestInBucket(requestItemId, itemQuantity, mediaList, customerId);
        return getResultResponseEntity(result);
    }

    @GetMapping("/deleteRequestsInBucket")
    public ResponseEntity<Result> deleteRequestsInBucket(
            @RequestParam String customerId,
            @RequestParam List<Long> requestIds
    ) {
        final Result result = requestService.deleteRequestsInBucket(customerId, requestIds);
        return getResultResponseEntity(result);
    }

    private ResponseEntity<Result> getResultResponseEntity(final Result result) {
        if (result instanceof Success) {
            Success success = (Success) result;
            return ResponseEntity.ok(success);
        } else if (result instanceof ClientError) {
            ClientError clientError = (ClientError) result;
            return ResponseEntity.badRequest().body(clientError);
        } else if (result instanceof ServerError) {
            ServerError serverError = (ServerError) result;
            return ResponseEntity.internalServerError().body(serverError);
        } else return null;
    }
}
