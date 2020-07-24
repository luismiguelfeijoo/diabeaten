package com.diabeaten.edgeservice.client;

import com.diabeaten.edgeservice.client.dto.InformationDTO;
import com.diabeaten.edgeservice.client.dto.UpdateInformationDTO;
import com.diabeaten.edgeservice.model.Information;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name ="information-service")
public interface InformationClient {
    @GetMapping("/information/{id}")
    public Information getById(@RequestHeader(name = "Authorization") String token, @PathVariable(name = "id") Long id);
    @PostMapping("/information")
    public Information create(@RequestHeader(name = "Authorization") String token, @RequestBody InformationDTO informationDTO);
    @PutMapping("/information/{id}")
    public Information update(@RequestHeader(name = "Authorization") String token, @PathVariable(name = "id") Long id, @RequestBody UpdateInformationDTO updateInformationDTO);
}
