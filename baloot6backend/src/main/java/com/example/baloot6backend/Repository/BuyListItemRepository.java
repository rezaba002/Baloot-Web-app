package com.example.baloot6backend.Repository;

import com.example.baloot6backend.Model.BuyListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuyListItemRepository extends JpaRepository<BuyListItem, Long> {
    public List<BuyListItem> findByUsername(String username);
}
