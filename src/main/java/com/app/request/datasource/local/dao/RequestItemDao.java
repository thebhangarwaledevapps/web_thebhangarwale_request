package com.app.request.datasource.local.dao;

import com.app.request.entity.RequestItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RequestItemDao extends JpaRepository<RequestItem, Long> {

    @Query(value = "SELECT medias_media_id FROM \n" +
            "item_medias LEFT JOIN request_item ON item_medias.item_item_id = request_item.item_item_id\n" +
            "LEFT JOIN customer_request_items ON request_item.request_item_id = customer_request_items.request_items_request_item_id\n" +
            "WHERE customer_request_items.customer_customer_id = ?1", nativeQuery = true)
    List<String> findAllMediaIdsByCustomerId(String customer_customer_id);

    @Query(value = "SELECT request_item.* FROM request_item LEFT JOIN customer_request_items\n" +
            " on request_item.request_item_id=customer_request_items.request_items_request_item_id\n" +
            " where customer_request_items.customer_customer_id = ?1", nativeQuery = true)
    List<RequestItem> findAllByCustomerId(String customer_customer_id);

    @Query(value = "select ri.*,i.*,m.* from \n" +
            "customer_request_items as cri left join request_item as ri\n" +
            "on cri.request_items_request_item_id = ri.request_item_id\n" +
            "left join item as i\n" +
            "on ri.item_item_id = i.item_id\n" +
            "left join item_medias as im\n" +
            "on i.item_id = im.item_item_id\n" +
            "left join media as m\n" +
            "on im.medias_media_id = m.media_id\n" +
            "where cri.customer_customer_id = ?1 and cri.request_items_request_item_id = ?2", nativeQuery = true)
    RequestItem findByCustomerIdAndRequestItemId(String customer_customer_id, long requestItemId);

    @Modifying
    @Transactional
    @Query(value = "delete im_1, m_1 from\n" +
            "item_medias as im_1 left join media as m_1 on im_1.medias_media_id = m_1.media_id\n" +
            "right join item_medias as im on m_1.media_id = im.medias_media_id\n" +
            "right join request_item as ri on im.item_item_id = ri.item_item_id\n" +
            "right join customer_request_items as cri on ri.request_item_id = cri.request_items_request_item_id\n" +
            "right join customer as c on cri.customer_customer_id = c.customer_id\n" +
            "where c.customer_id = ?1 and cri.request_items_request_item_id = ?2 and im_1.medias_media_id in ?3", nativeQuery = true)
    int deleteMediaByCustomerIdAndMediaId(String customer_id, long request_items_request_item_id,List<String> medias);


}
