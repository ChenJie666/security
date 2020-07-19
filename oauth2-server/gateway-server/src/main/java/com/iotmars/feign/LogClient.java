package com.iotmars.feign;

import com.iotmars.utils.Log;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 存储到日志中心
 */
@FeignClient("log-center")
public interface LogClient {

	@PostMapping("/logs-anon/internal")
	void save(@RequestBody Log log);
}
