package com.diabeaten.edgeservice.client;

import com.diabeaten.edgeservice.client.dto.BolusDTO;
import com.diabeaten.edgeservice.client.dto.GlucoseDTO;
import com.diabeaten.edgeservice.model.Bolus;
import com.diabeaten.edgeservice.model.Glucose;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@FeignClient(name = "glucose-bolus-service", url = "https://gb-service-diabeaten.herokuapp.com")
public interface GlucoseBolusClient {

    @GetMapping("/bolus/by")
    public List<Bolus> getBy(@RequestHeader(name = "Authorization") String token, @RequestParam(name = "userId") Long userId, @RequestParam(name = "fromDate", required = false) Date date);

    @PostMapping("/bolus")
    public Bolus create(@RequestHeader(name = "Authorization") String token, @RequestBody @Valid BolusDTO bolusDTO);

    @GetMapping("/glucose/{userId}")
    public List<Glucose> getByUserId(@RequestHeader(name = "Authorization") String token,@PathVariable(name = "userId") Long userId);

    @PostMapping("/glucose")
    public Glucose create(@RequestHeader(name = "Authorization") String token, @RequestBody @Valid GlucoseDTO glucoseDTO);
}
