package com.javahabit.d2ccommerceapp.service.book;

import org.springframework.stereotype.Component;

@Component("giveaway-monthly")
public class GiveawayMonthlyService implements IGiveaway{
    @Override
    public String giveAwayMsg() {
        return "SIGN UP FOR MONTHLY GIVEAWAY!";

    }
}
