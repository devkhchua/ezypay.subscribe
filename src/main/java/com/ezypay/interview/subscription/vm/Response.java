package com.ezypay.interview.subscription.vm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private Double amount;
    private String subscriptionType;
    private List<LocalDate> invoiceDates;
}
