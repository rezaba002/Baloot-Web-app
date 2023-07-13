package com.example.baloot6backend.Repository;

import com.example.baloot6backend.Model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, String> {
    public Discount findByDiscountCode(String discountCode);
}
