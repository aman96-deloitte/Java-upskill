package com.example.bids.repository;

import com.example.bids.entity.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdsRepository extends JpaRepository<Ads,Integer> {


    @Query(value = "SELECT * FROM ads WHERE aid = ?1", nativeQuery = true)
    Ads findByAdId(int id);

    List<Ads> findByBidEndTimeAfter(LocalDateTime now);
}
