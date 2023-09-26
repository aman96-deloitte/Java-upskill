package com.example.bids.service;

import com.example.bids.VO.AdDetail;
import com.example.bids.VO.Car;
import com.example.bids.VO.User;
import com.example.bids.controller.ErrorFoundException;
import com.example.bids.controller.ErrorResponse;
import com.example.bids.entity.Ads;
import com.example.bids.entity.Bids;
import com.example.bids.repository.AdsRepository;
import com.example.bids.repository.BidsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class AdService {

@Autowired
private AdsRepository adsRepository;

@Autowired
private BidsRepository bidsRepository;

@Autowired
private RestTemplate restTemplate;

    @ExceptionHandler()
    public ResponseEntity<ErrorResponse> handleException(ErrorFoundException exc){
        ErrorResponse error = new ErrorResponse();
        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Extract the token part after "Bearer "
        }
        return null; // Return null or handle invalid headers as needed
    }


    public Ads createAd(Ads ad, String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"token\": "+"\""+token+"\"}";
        System.out.print(requestBody);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        String postUrl = "http://USER-SERVICE/users/getUser";
        ResponseEntity<User> responseEntity = restTemplate.postForEntity(postUrl, requestEntity, User.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            User user = responseEntity.getBody();
            // Process the response data here
            ad.setEmail(user.getEmail());

            LocalDateTime bidEndTime = LocalDateTime.now().plusHours(1);
            ad.setBidEndTime(bidEndTime);

            return adsRepository.save(ad);
        } else {
            // Handle error responses here
            throw new RuntimeException("Failed to save entity");
        }
    }



    public AdDetail viewAd(int adId, String authorizationHeader) {
        AdDetail adDetail = new AdDetail();
        Optional<Ads> adsOptional = Optional.ofNullable(adsRepository.findByAdId(adId).orElseThrow(() -> new ErrorFoundException("Invalid AdId")));
        Ads ad = adsOptional.get();


        Optional<Bids> bidsOptional = Optional.ofNullable(bidsRepository.findByAdId(adId).orElseThrow(() -> new ErrorFoundException("Invalid BidId")));
        if(bidsOptional.isPresent()){
            Bids bid = bidsOptional.get();
            adDetail.setBids(bid);
        }
        Car car = restTemplate.getForObject("http://CARS-SERVICE/cars/"+ad.getCarId(),Car.class);

        adDetail.setAd(ad);
        adDetail.setCar(car);

        return adDetail;

    }


    public List<Ads> viewAllAd() {
        return adsRepository.findAll();
    }
}
