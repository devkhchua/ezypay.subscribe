package com.ezypay.interview.subscription.util;

import com.ezypay.interview.subscription.entity.Subscribe;
import com.ezypay.interview.subscription.vm.Request;
import com.ezypay.interview.subscription.vm.Response;
import lombok.extern.slf4j.Slf4j;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SubscribeHelper {

    private SubscribeHelper(){};
    private static SubscribeHelper instance;
    public static SubscribeHelper getInstance(){
        if(null == instance){
            instance = new SubscribeHelper();
        }
        return instance;
    }

    public void isValidSubscription(Request request) throws Exception {

        LocalDate maxDate = request.getStartDate().plusMonths(3);

        if (request.getStartDate().isAfter(request.getEndDate())) {
            log.error("Subscription start date is after end date");
            throw new Exception("Subscription start date is after end date");
        }

        if (request.getEndDate().isAfter(maxDate)) {
            log.error("Subscription period is more than 3 months");
            throw new Exception("Subscription period is more than 3 months");
        }

        if (request.getSubscriptionType().equals("WEEKLY") && !Arrays.stream(DayOfWeek.values()).anyMatch(obj -> obj.toString().equals(request.getBillOn().toUpperCase()))) {
            log.error("Please enter the correct bill on type for weekly : eg. TUESDAY");
            throw new Exception("Please enter the correct bill on type for weekly : eg. TUESDAY");
        }

        if (request.getSubscriptionType().equals("MONTHLY") && !isInteger(request.getBillOn())) {
            log.error("Please enter the correct bill on type for monthly : eg. 1 to 31");
            throw new Exception("Please enter the correct bill on type for monthly : eg. 1 to 31");
        }

    }

    public Response constructResponse(Subscribe subscribe) {
        Response response = new Response();
        response.setAmount(subscribe.getAmount());
        response.setSubscriptionType(subscribe.getSubscriptionType());
        response.setInvoiceDates(constructInvoiceDates(subscribe));
        return response;
    }

    public Subscribe constructSubscribe(Request request) {
        Subscribe response = new Subscribe();
        response.setBillOn(request.getBillOn());
        response.setAmount(request.getAmount());
        response.setSubscriptionType(request.getSubscriptionType());
        response.setStartDate(request.getStartDate());
        response.setEndDate(request.getEndDate());
        return response;
    }

    public List<LocalDate> constructInvoiceDates(Subscribe request) {
        List<LocalDate> temp = request.getStartDate().datesUntil(request.getEndDate().plusDays(1)).collect(Collectors.toList());

        if (request.getSubscriptionType().equals("WEEKLY")) {
            return temp.stream().filter(obj -> obj.equals(obj.with(DayOfWeek.valueOf(request.getBillOn())))).collect(Collectors.toList());
        }

        //if there is a date time exception, it means that the date is not available in that particular month (leap year)
        //alternative is to get the last day of the month as an invoice date
        if (request.getSubscriptionType().equals("MONTHLY")) {
            return temp.stream().filter(obj -> {
                try {
                    return obj.equals(obj.withDayOfMonth(Integer.parseInt(request.getBillOn())));
                }catch (DateTimeException e){
                    return obj.equals(obj.withDayOfMonth(obj.getMonth().length(obj.isLeapYear())));
                }
            }).collect(Collectors.toList());
        }

        return temp;
    }

    public boolean isInteger(String value) {
        if (value.isEmpty())
            return false;
        try {
            int tempObj = Integer.parseInt(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
