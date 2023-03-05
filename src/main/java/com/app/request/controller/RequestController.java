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
    public ResponseEntity addRequestInBucket(
            @RequestParam long itemId,
            @RequestParam int itemQuantity,
            @RequestParam(name = "media") MultipartFile[] mediaList,
            @RequestParam String customerId
    ) {
        return getResultResponseEntity(requestService.addRequestInBucket(itemId, itemQuantity, mediaList, customerId));
    }

    @GetMapping("/getCustomersRequestInBucket")
    public ResponseEntity getCustomersRequestInBucket(
            @RequestParam String customerId
    ) {
        return getResultResponseEntity(requestService.getCustomersRequestInBucket(customerId));
    }

    @GetMapping("/deleteCustomerRequestMedia")
    public ResponseEntity deleteCustomerRequestMedia(
            @RequestParam String customerId,
            @RequestParam long requestId,
            @RequestParam List<String> mediaIds
    ) {
        return getResultResponseEntity(requestService.deleteMediaById(customerId, requestId, mediaIds));
    }

    @GetMapping("/updateCustomersRequestInBucket")
    public ResponseEntity updateCustomersRequestInBucket(
            @RequestParam long requestItemId,
            @RequestParam(required = false) int itemQuantity,
            @RequestParam(name = "media", required = false) MultipartFile[] mediaList,
            @RequestParam String customerId
    ) {
        return getResultResponseEntity(requestService.updateRequestInBucket(requestItemId, itemQuantity, mediaList, customerId));
    }

    @GetMapping("/deleteRequestsInBucket")
    public ResponseEntity deleteRequestsInBucket(
            @RequestParam String customerId,
            @RequestParam List<Long> requestIds
    ) {
        return getResultResponseEntity(requestService.deleteRequestsInBucket(customerId, requestIds));
    }

    private ResponseEntity getResultResponseEntity(final Result result) {
        ResponseEntity responseEntity = null;
        if (result instanceof Success) {
            Success success = (Success) result;
            responseEntity = ResponseEntity.ok(success.getData());
        } else if (result instanceof ClientError) {
            ClientError clientError = (ClientError) result;
            responseEntity = ResponseEntity.badRequest().body(clientError.getException().getMessage());
        } else if (result instanceof ServerError) {
            ServerError serverError = (ServerError) result;
            responseEntity = ResponseEntity.internalServerError().body(serverError.getException().getMessage());
        }
        return responseEntity;
    }
}
