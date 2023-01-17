package com.app.request.datasource.local.dao;

import com.app.request.entity.RequestItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RequestItemDao extends JpaRepository<RequestItem, Long> {

    @Query(value = "SELECT medias_media_id FROM \n" +
            "request_item_medias LEFT JOIN request_item ON request_item_medias.item_item_id = request_item.item_item_id\n" +
            "LEFT JOIN customer_request_items ON request_item.request_item_id = customer_request_items.request_items_request_item_id\n" +
            "WHERE customer_request_items.customer_customer_id = ?1", nativeQuery = true)
    List<String> findAllMediaIdsByCustomerId(String customer_customer_id);

    @Query(value = "SELECT request_item.* FROM request_item LEFT JOIN customer_request_items\n" +
            " on request_item.request_item_id=customer_request_items.request_items_request_item_id\n" +
            " where customer_request_items.customer_customer_id = ?1", nativeQuery = true)
    List<RequestItem> findAllByCustomerId(String customer_customer_id);

    //@Transactional
    //Integer deleteByCustomerIdAndRequestItemIdIn(String customerId, List<Long> ids);


}
