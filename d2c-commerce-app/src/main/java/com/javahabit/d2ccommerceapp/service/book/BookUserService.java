package com.javahabit.d2ccommerceapp.service.book;

import com.javahabit.d2ccommerceapp.user.repository.UserRegionRepository;
import com.javahabit.d2ccommerceapp.user.repository.UserRepository;
import com.javahabit.d2ccommerceapp.vo.security.User;
import com.javahabit.d2ccommerceapp.vo.security.UserRegion;
import org.ff4j.core.FlippingExecutionContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("book-user-service")
public class BookUserService implements IService<String, String>{
    private UserRepository userRepository;
    private UserRegionRepository userRegionRepository;
    final
    FlippingExecutionContext fex;

    public BookUserService(FlippingExecutionContext fex, UserRepository userRepository, UserRegionRepository userRegionRepository) {
        this.fex = fex;
        this.userRepository = userRepository;
        this.userRegionRepository = userRegionRepository;

    }

    @Override
    public String process(String userName) {
        return getUserState(userName);
    }
    public String getUserState(String userName) {
        Optional<User> user =  userRepository.findByUsername(userName);
        Long user_id = user.get().getId();
        UserRegion userRegion = userRegionRepository.findByUser_id(user_id);
        fex.addValue("region", userRegion.getState());
        return userRegion.getState();
    }



}
