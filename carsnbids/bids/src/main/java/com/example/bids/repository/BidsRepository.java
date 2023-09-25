package com.example.bids.repository;

import com.example.bids.VO.BidsDetail;
import com.example.bids.entity.Bids;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidsRepository extends JpaRepository<Bids,Integer> {

    Optional<Bids> findByAdId(int adId);

    @Query(value = "SELECT * FROM bids WHERE bid_status = 0", nativeQuery = true)
    List<Bids> findByActiveStatus();

    @Query(value = "SELECT * FROM bids WHERE bid_status = 1", nativeQuery = true)
    List<Bids> findByNonActiveStatus();



}
