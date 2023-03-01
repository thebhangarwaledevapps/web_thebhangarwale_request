package com.app.request.datasource.local.dao;

import com.app.request.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ItemDao extends JpaRepository<Item, Long> {

    @Modifying
    @Transactional
    @Query(value = "update item inner join request_item on item.item_id = request_item.item_item_id\n" +
            "inner join customer_request_items on customer_request_items.request_items_request_item_id = request_item.request_item_id\n" +
            "inner join customer on customer.customer_id = customer_request_items.customer_customer_id\n" +
            "set item.item_quantity = :itemQuantity, item.total_item_price_for_user_as_per_quantity = :itemQuantity * item.item_price\n" +
            "where customer.customer_id = :customerId and request_item.request_item_id = :requestItemId", nativeQuery = true)
    int updateQuantity(@Param("itemQuantity") long itemQuantity, @Param("customerId") String customerId, @Param("requestItemId") long requestItemId);

}
