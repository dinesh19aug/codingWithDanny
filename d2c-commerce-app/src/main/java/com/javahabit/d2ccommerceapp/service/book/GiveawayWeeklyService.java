package com.javahabit.d2ccommerceapp.service.book;

import org.springframework.stereotype.Component;

@Component("giveaway-weekly")
public class GiveawayWeeklyService implements IGiveaway{
    @Override
    public String giveAwayMsg() {
        return "SIGN UP FOR WEEKLY GIVEAWAY!";

    }
}
