package com.rafu.sistrab.clients;

import com.rafu.sistrab.rest.dto.BrApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "br-api", url = "https://brapi.dev")
public interface BrApiClient {
    @GetMapping("/api/quote/{codes}")
    BrApiResponse getQuote(@PathVariable String codes,
                           @RequestParam(required = false, defaultValue = "1d") String range,
                           @RequestParam(required = false, defaultValue = "1d") String interval);
}
