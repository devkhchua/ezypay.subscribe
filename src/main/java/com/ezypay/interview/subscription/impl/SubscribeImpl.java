package com.ezypay.interview.subscription.impl;

import com.ezypay.interview.subscription.entity.Subscribe;
import com.ezypay.interview.subscription.repository.SubscribeRepo;
import com.ezypay.interview.subscription.service.SubscribeService;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Data
@Transactional
public class SubscribeImpl implements SubscribeService {

    private final SubscribeRepo subscribeRepo;

    @Override
    public Subscribe createSubscription(Subscribe subscribe) {
        return subscribeRepo.save(subscribe);
    }

    @Override
    public List<Subscribe> findAll() {
        return subscribeRepo.findAll();
    }
}
