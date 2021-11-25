package com.ezypay.interview.subscription.controller;

import com.ezypay.interview.subscription.entity.Subscribe;
import com.ezypay.interview.subscription.service.SubscribeService;
import com.ezypay.interview.subscription.util.SubscribeHelper;
import com.ezypay.interview.subscription.vm.Request;
import com.ezypay.interview.subscription.vm.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/subscribe")
@Slf4j
public class SubscriptionController {

    @Autowired
    private SubscribeService subscribeService;
    SubscribeHelper helper = SubscribeHelper.getInstance();

    //Single Endpoint to Create Subscription
    @PostMapping
    public ResponseEntity<Object> subscribe(@RequestBody Request request) {
        Response response = new Response();
        try {
            //Logging purpose
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            log.info("[RequestController] request : " + objectMapper.writeValueAsString(request));

            //Request to uppercase
            request.setSubscriptionType(request.getSubscriptionType().toUpperCase());
            request.setBillOn(request.getBillOn().toUpperCase());

            //Validate request
            helper.isValidSubscription(request);

            //Create subscription (persist into H2 in memory database)
            Subscribe result = subscribeService.createSubscription(helper.constructSubscribe(request));

            //Construct response according to required output
            response = helper.constructResponse(result);
            log.info("[RequestController] response : " + objectMapper.writeValueAsString(response));

        } catch (Exception e) {
            log.error("[RequestController] exception : " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //For result capturing purpose
    @GetMapping
    public ResponseEntity<Object> retrieve() {
        List<Subscribe> response = new ArrayList<>();
        try {
            response = subscribeService.findAll();
            response.forEach(obj -> obj.setInvoiceDates(helper.constructInvoiceDates(obj)));
        } catch (Exception e) {
            log.error("[RequestController] exception : " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
