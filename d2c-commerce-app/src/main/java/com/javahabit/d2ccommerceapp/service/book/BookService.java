package com.javahabit.d2ccommerceapp.service.book;

import com.javahabit.d2ccommerceapp.user.repository.UserRegionRepository;
import com.javahabit.d2ccommerceapp.user.repository.UserRepository;
import com.javahabit.d2ccommerceapp.vo.Book;
import com.javahabit.d2ccommerceapp.vo.security.User;
import com.javahabit.d2ccommerceapp.vo.security.UserRegion;
import org.ff4j.FF4j;
import org.ff4j.core.FlippingExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component("book-service")
public class BookService implements IService{
    final FF4j ff4j;
    private UserRepository userRepository;
    private UserRegionRepository userRegionRepository;
    @Autowired
    FlippingExecutionContext fex;
    public BookService(FF4j ff4j, UserRepository userRepository, UserRegionRepository userRegionRepository) {
        this.ff4j = ff4j;
        this.userRepository=userRepository;
        this.userRegionRepository=userRegionRepository;

    }

    @Override
    public  List<Book> apply() {

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
    @Override
    public String getUserState(String userName){
        Optional<User> user =  userRepository.findByUsername(userName);
        Long user_id = user.get().getId();
        UserRegion userRegion = userRegionRepository.findByUser_id(user_id);
        fex.addValue("region", userRegion.getState());
        return userRegion.getState();
    }
}
