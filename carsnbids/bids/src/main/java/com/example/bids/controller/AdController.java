package com.example.bids.controller;

import com.example.bids.VO.AdDetail;
import com.example.bids.entity.Ads;
import com.example.bids.repository.BidsRepository;
import com.example.bids.service.AdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biding/ads")
@Slf4j
public class AdController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ErrorFoundException exc){
        ErrorResponse error = new ErrorResponse();
        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }



    @Autowired
    private AdService adService;

    @RequestMapping(value = "/viewAd/{adId}", method = RequestMethod.GET)
    public AdDetail viewAd(@PathVariable("adId") int adId ,@RequestHeader("Authorization") String authorizationHeader){
        return adService.viewAd(adId,authorizationHeader);
    }

    @RequestMapping(value = "/viewAllAd", method = RequestMethod.GET)
    public List<Ads> viewAllAd(){
        return adService.viewAllAd();
    }

    @RequestMapping(value = "/createAd", method = RequestMethod.POST)
        public Ads createAd(@RequestBody Ads ad, @RequestHeader("Authorization") String authorizationHeader){
            return adService.createAd(ad,authorizationHeader);
    }


}
