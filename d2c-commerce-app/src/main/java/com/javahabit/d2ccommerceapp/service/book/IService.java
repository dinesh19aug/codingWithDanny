package com.javahabit.d2ccommerceapp.service.book;

public interface IService {
    <T> T apply();

    String getUserState(String userName);
}
