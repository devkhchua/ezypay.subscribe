package com.ezypay.interview.subscription.vm;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd/MM/yyyy")
    private List<LocalDate> invoiceDates;
}
