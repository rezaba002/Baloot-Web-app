package com.example.baloot6backend.Repository;

import com.example.baloot6backend.Model.PurchasedList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasedListRepository extends JpaRepository<PurchasedList, Long> {
    public List<PurchasedList> findByUsername(String username);
}
