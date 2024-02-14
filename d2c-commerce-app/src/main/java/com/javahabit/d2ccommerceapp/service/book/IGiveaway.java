package com.javahabit.d2ccommerceapp.service.book;

import org.ff4j.aop.Flip;

public interface IGiveaway {
    @Flip(name = "giveaway-aop", alterBean = "giveaway-weekly")
    String giveAwayMsg();
}
