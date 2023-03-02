package com.app.request.datasource.local.dao;

import com.app.request.entity.Media;
import com.app.request.entity.RequestItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

//https://www.baeldung.com/spring-data-derived-queries
public interface MediaDao extends JpaRepository<Media, String> {

    //@Query(value = "SELECT media_id FROM Media,Request_Item WHERE customer_id = ?1 GROUP BY media_id", nativeQuery = true)
    //List<String> findAllMediaIdByCustomerId(String customerId);

    @Query(value = "select m.* from \n" +
            "customer_request_items as cri left join request_item as ri\n" +
            "on cri.request_items_request_item_id = ri.request_item_id\n" +
            "left join item as i\n" +
            "on ri.item_item_id = i.item_id\n" +
            "left join item_medias as im\n" +
            "on i.item_id = im.item_item_id\n" +
            "left join media as m\n" +
            "on im.medias_media_id = m.media_id\n" +
            "where cri.customer_customer_id = ?1 and cri.request_items_request_item_id = ?2", nativeQuery = true)
    List<Media> findByCustomerIdAndRequestItemId(String customer_customer_id, long requestItemId);


}
