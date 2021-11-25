package com.ezypay.interview.subscription.repository;

import com.ezypay.interview.subscription.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepo extends JpaRepository<Subscribe, Long> {
}
