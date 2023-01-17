package com.app.request.datasource.network;

import com.app.request.entity.BhangarTypeAndPrice;
import org.springframework.stereotype.Service;

@Service
public interface NetworkDataSource {
    BhangarTypeAndPrice getBhangarItemInfo(long itemId);
}
