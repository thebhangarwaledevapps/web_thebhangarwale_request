package com.app.request.datasource.local;

import com.app.request.entity.Customer;
import com.app.request.entity.Media;
import com.app.request.entity.RequestItem;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface DatabaseDataSource {
    Customer saveCustomerRequest(String customerId, String itemName, int itemQuantity, double itemPrice, String itemUnit, double totalItemPriceForUserAsPerQuantity, List<Media> medias);

    List<String> findAllMediaIdByCustomerId(String customerId);

    void saveAll(List<Media> collect);

    List<RequestItem> findByCustomerId(String customerId);

    RequestItem findByCustomerIdAndRequestItemId(String customerId, long requestId);

    int deleteMediaByCustomerIdRequestIdAndMediaId(String customerId, long requestId, List<String> mediaIds);

    List<Media> findMediaByCustomerIdAndRequestId(String customerId, long requestId);

    Customer saveCustomerRequest(String customerId, List<RequestItem> requestItems);

    int deleteRequestItemByCustomerIdAndRequestItemId(String customerId, List<Long> requestIds);

    Optional<Customer> findCustomerByCustomerId(String customerId);

    int updateCustomerRequest(int itemQuantity, String customerId, long requestItemId);

}
