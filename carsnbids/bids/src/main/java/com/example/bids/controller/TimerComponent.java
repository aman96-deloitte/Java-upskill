package com.example.bids.controller;

import com.example.bids.entity.Ads;
import com.example.bids.entity.Bids;
import com.example.bids.repository.AdsRepository;
import com.example.bids.repository.BidsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class TimerComponent {


    @Autowired
    private AdsRepository adsRepository;

    @Autowired
    private BidsRepository bidsRepository;

    @Scheduled(fixedRate = 1000) // Update every second
    public void updateRemainingTime() {
        List<Ads> activeAds = adsRepository.findByBidEndTimeAfter(LocalDateTime.now());

        for (Ads ads : activeAds) {
            // Calculate the remaining time and update the bid if needed
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(now, ads.getBidEndTime());
            Duration fiveSeconds = Duration.ofSeconds(5);
            // If the bid has ended, mark it as ended
            System.out.print(duration);
            if(ads.getAdStatus()==1){
                if (duration.compareTo(fiveSeconds) < 0) {
//                    ads.setBidEndTime(now);
                    ads.setAdStatus(0);
                    System.out.print("inside duration");
                    Optional<Bids> optionalBids =  bidsRepository.findByAdId(ads.getAid());
                    if(optionalBids.isPresent()){
                        Bids bidFetch = optionalBids.get();
                        bidFetch.setBidStatus(1);
                        bidsRepository.save(bidFetch);
                    }
                    adsRepository.save(ads);
                }
            }

        }
    }


}
