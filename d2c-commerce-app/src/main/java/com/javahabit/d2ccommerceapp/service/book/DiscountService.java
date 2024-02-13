package com.javahabit.d2ccommerceapp.service.book;

import com.javahabit.d2ccommerceapp.vo.Discount;
import org.ff4j.FF4j;
import org.springframework.stereotype.Component;

@Component("discount-service")
public class DiscountService implements IService<Discount,String>{

    private final FF4j ff4j;

    public DiscountService(FF4j ff4j) {
        this.ff4j = ff4j;
    }

    @Override
    public Discount process(String s) {
        String discount = String.valueOf(ff4j.getProperty("discount").getValue());
        String couponCode = String.valueOf(ff4j.getProperty("couponCode").getValue());
        Discount specialDiscount = new Discount(discount, couponCode);
        return specialDiscount;
    }
}
