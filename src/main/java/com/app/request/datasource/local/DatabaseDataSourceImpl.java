package com.app.request.datasource.local;

import com.app.request.datasource.local.dao.CustomerDao;
import com.app.request.datasource.local.dao.ItemDao;
import com.app.request.datasource.local.dao.MediaDao;
import com.app.request.datasource.local.dao.RequestItemDao;
import com.app.request.entity.Customer;
import com.app.request.entity.Item;
import com.app.request.entity.Media;
import com.app.request.entity.RequestItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Service
public class DatabaseDataSourceImpl implements DatabaseDataSource {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private RequestItemDao requestItemDao;

    @Autowired
    private MediaDao mediaDao;

    @Autowired
    private ItemDao itemDao;

    @Override
    public Customer saveCustomerRequest(String customerId, String itemName, int itemQuantity, double itemPrice, String itemUnit, double totalItemPriceForUserAsPerQuantity, List<Media> medias) {
        return customerDao.save(
                new Customer(
                        customerId,
                        asList(
                                new RequestItem(
                                        new Item(
                                                itemName,
                                                itemQuantity,
                                                itemPrice,
                                                itemUnit,
                                                totalItemPriceForUserAsPerQuantity,
                                                medias
                                        )
                                )
                        )
                )
        );
    }

    @Override
    public List<String> findAllMediaIdByCustomerId(String customerId) {
        return requestItemDao.findAllMediaIdsByCustomerId(customerId);
    }

    @Override
    public void saveAll(List<Media> mediaList ) {
        mediaDao.saveAll(mediaList);
    }

    @Override
    public List<RequestItem> findByCustomerId(String customerId) {
        return requestItemDao.findAllByCustomerId(customerId);
    }

    @Override
    public RequestItem findByCustomerIdAndRequestItemId(String customerId, long requestId) {
        return requestItemDao.findByCustomerIdAndRequestItemId(customerId, requestId);
    }

    @Override
    public int deleteMediaByCustomerIdRequestIdAndMediaId(String customerId, long requestId, List<String> mediaIds) {
        return requestItemDao.deleteMediaByCustomerIdAndMediaId(customerId, requestId, mediaIds);
    }

    @Override
    public List<Media> findMediaByCustomerIdAndRequestId(String customerId, long requestId) {
        return mediaDao.findByCustomerIdAndRequestItemId(customerId,requestId);
    }

    @Override
    public Customer saveCustomerRequest(String customerId, List<RequestItem> requestItems) {
        return customerDao.save(
                new Customer(
                        customerId,
                        requestItems
                )
        );
    }

    @Override
    public int deleteRequestItemByCustomerIdAndRequestItemId(String customerId, List<Long> requestIds) {
        return 0;
    }

    @Override
    public Optional<Customer> findCustomerByCustomerId(String customerId) {
        return customerDao.findById(customerId);
    }

    @Override
    public int updateCustomerRequest(int itemQuantity, String customerId, long requestItemId) {
        return itemDao.updateQuantity(itemQuantity,customerId,requestItemId);
    }

}
