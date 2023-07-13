package com.example.baloot6backend.Repository;

import com.example.baloot6backend.Model.UserDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDiscountRepository extends JpaRepository<UserDiscount, String> {

    List<UserDiscount> findByDiscountDiscountCode(String discountCode);
    List<UserDiscount> findByUserUsername(String username);
}
