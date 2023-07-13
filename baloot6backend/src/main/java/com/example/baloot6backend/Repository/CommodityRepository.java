package com.example.baloot6backend.Repository;

import com.example.baloot6backend.Model.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommodityRepository extends JpaRepository<Commodity, Long> {
    @Query ("SELECT c FROM Commodity c WHERE c.id = :id")
    public Commodity findByCommodityId(@Param("id") Long commodityId);

    public List<Commodity> findByProviderId(long providerId);

}
