package com.app.request.datasource.network.client;

import com.app.request.entity.BhangarTypeAndPrice;
import com.app.request.result.Success;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "admin")
public interface AdminClient {

	@GetMapping("/admin/getBhangarItemInfo")
	BhangarTypeAndPrice getBhangarItemInfo(
			@RequestParam Long itemId
	);

}
