package com.javahabit.d2ccommerceapp.user.repository;


import com.javahabit.d2ccommerceapp.vo.security.UserRegion;
import org.springframework.data.repository.CrudRepository;

public interface UserRegionRepository extends CrudRepository<UserRegion, Long> {
    UserRegion findByUser_id(Long userId);
}
