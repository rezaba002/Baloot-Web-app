package com.example.baloot6backend.Repository;

import com.example.baloot6backend.Model.PurchasedListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchasedListItemRepository extends JpaRepository<PurchasedListItem, Long> {
    public List<PurchasedListItem> findByUsername(String username);
}
