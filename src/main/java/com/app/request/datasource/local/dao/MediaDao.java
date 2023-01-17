package com.app.request.datasource.local.dao;

import com.app.request.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

//https://www.baeldung.com/spring-data-derived-queries
public interface MediaDao extends JpaRepository<Media, String> {

    //@Query(value = "SELECT media_id FROM Media,Request_Item WHERE customer_id = ?1 GROUP BY media_id", nativeQuery = true)
    //List<String> findAllMediaIdByCustomerId(String customerId);

}
