package com.ezypay.interview.subscription.service;

import com.ezypay.interview.subscription.entity.Subscribe;

import java.util.List;

public interface SubscribeService {
    public Subscribe createSubscription(Subscribe subscribe);
    public List<Subscribe> findAll();
}
