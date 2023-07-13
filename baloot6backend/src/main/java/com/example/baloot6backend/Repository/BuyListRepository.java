package com.example.baloot6backend.Repository;

import com.example.baloot6backend.Model.BuyList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyListRepository extends JpaRepository<BuyList, String> {
    public BuyList findByUsername(String username);
}
