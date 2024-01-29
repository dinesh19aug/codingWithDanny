package com.javahabit.d2ccommerceapp.vo;

import java.math.BigDecimal;

public record Book(int bookId, String bookName, BigDecimal price, String image) {
}
