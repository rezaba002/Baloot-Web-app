package com.example.baloot6backend.Repository;

import com.example.baloot6backend.Model.Category;
import com.example.baloot6backend.Model.CommodityCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {

    Category findByName(String categoryName);

//    Category findById(Long commodityId);
}
