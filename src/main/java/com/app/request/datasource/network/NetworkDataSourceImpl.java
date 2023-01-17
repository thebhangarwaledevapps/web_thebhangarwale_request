package com.app.request.datasource.network;

import com.app.request.datasource.network.client.AdminClient;
import com.app.request.entity.BhangarTypeAndPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NetworkDataSourceImpl implements NetworkDataSource{

    @Autowired
    private AdminClient adminClient;

    @Override
    public BhangarTypeAndPrice getBhangarItemInfo(long itemId) {
        try {
            return adminClient.getBhangarItemInfo(itemId);
        }catch (Exception e){
            return null;
        }
    }
}
