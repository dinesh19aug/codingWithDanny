package com.javahabit.d2ccommerceapp.service.book;

import com.javahabit.d2ccommerceapp.vo.Book;
import org.ff4j.FF4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component("book-service")
public class BookService implements IService<List<Book>, String>{
    final FF4j ff4j;

    public BookService(FF4j ff4j) {
        this.ff4j = ff4j;
    }
    @Override
    public  List<Book> process(String optionalValue) {
        return getBookList();
    }

    private  List<Book> getBookList() {
        Book p1 = new Book(1,"Designing Data-Intensive Applications", BigDecimal.valueOf(22.99), "/images/ddia.jpeg");
        Book p2 = new Book(2,"Hands-On Machine Learning with Scikit-Learn, Keras, and TensorFlow", BigDecimal.valueOf(25.99), "/images/homlkt.jpeg");
        Book p3 = new Book(3,"Learning Go, 2nd Edition", BigDecimal.valueOf(32.99), "/images/lg.jpeg");
        Book p4 = new Book(4,"Clean Code: A Handbook of Agile Software Craftsmanship", BigDecimal.valueOf(32.99), "/images/cc.jpeg");

        if(ff4j.check("pre-release")){
            return List.of(p1, p2, p3, p4);
        }
        return List.of(p1, p2, p3);
    }

}
