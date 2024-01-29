package com.javahabit.d2ccommerceapp.service;

import com.javahabit.d2ccommerceapp.vo.Book;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component("book-service")
public class BookService implements IService{
    @Override
    public  List<Book> apply() {
        return getBookList();

    }

    private static List<Book> getBookList() {
        Book p1 = new Book(1,"Designing Data-Intensive Applications", BigDecimal.valueOf(22.99), "/images/ddia.jpeg");
        Book p2 = new Book(2,"Hands-On Machine Learning with Scikit-Learn, Keras, and TensorFlow", BigDecimal.valueOf(25.99), "/images/homlkt.jpeg");
        Book p3 = new Book(3,"Learning Go, 2nd Edition", BigDecimal.valueOf(32.99), "/images/lg.jpeg");
        Book p4 = new Book(4,"Clean Code: A Handbook of Agile Software Craftsmanship", BigDecimal.valueOf(32.99), "/images/cc.jpeg");
        return List.of(p1, p2, p3, p4);
    }
}
