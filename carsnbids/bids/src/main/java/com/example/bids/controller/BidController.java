package com.example.bids.controller;


import com.example.bids.VO.BidsDetail;
import com.example.bids.entity.Ads;
import com.example.bids.entity.Bids;
import com.example.bids.entity.Comments;
import com.example.bids.repository.BidsRepository;
import com.example.bids.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biding/bids")
public class BidController {

    @Autowired
    private BidsRepository bidsRepository;
    @Autowired
    private BidService bidService;

    @RequestMapping(value = "/createBid", method = RequestMethod.POST)
    public Bids createBid(@RequestBody Bids bid, @RequestHeader("Authorization") String authorizationHeader){
        return bidService.createBid(bid,authorizationHeader);
    }

    @RequestMapping(value="/viewActiveBids", method = RequestMethod.GET)
    public List<Bids> viewActiveBids(){
        return bidService.viewActiveBids();
    }

    @RequestMapping(value="/viewNonActiveBids", method = RequestMethod.GET)
    public List<Bids> viewNonActiveBids(){
        return bidService.viewNonActiveBids();
    }
}
