package com.example.bids.repository;

import com.example.bids.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments,Integer> {

    List<Comments> findByAid(int adId);

}
