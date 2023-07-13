package com.example.baloot6backend.Repository;

import com.example.baloot6backend.Model.RateCommodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateCommodityRepository extends JpaRepository<RateCommodity, Long> {

    List<RateCommodity> findByCommodityId(Long id);

}
