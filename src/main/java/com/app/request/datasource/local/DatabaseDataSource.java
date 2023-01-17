package com.app.request.datasource.local;

import com.app.request.entity.Customer;
import com.app.request.entity.Media;
import com.app.request.entity.RequestItem;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface DatabaseDataSource {
    Customer saveCustomerRequest(String customerId, String itemName, int itemQuantity, double itemPrice, String itemUnit, double totalItemPriceForUserAsPerQuantity, List<Media> medias);

    List<String> findAllMediaIdByCustomerId(String customerId);

    void saveAll(List<Media> collect);

    List<RequestItem> findByCustomerId(String customerId);
}
