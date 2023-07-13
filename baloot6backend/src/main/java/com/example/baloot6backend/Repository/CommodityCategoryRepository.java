package com.example.baloot6backend.Repository;

import com.example.baloot6backend.Model.Category;
import com.example.baloot6backend.Model.CommodityCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public interface CommodityCategoryRepository extends JpaRepository<CommodityCategory, Long> {
    List<CommodityCategory> findByCommodityId(Long id);
}
