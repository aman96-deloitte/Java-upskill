package com.example.bids.service;

import com.example.bids.VO.BidsDetail;
import com.example.bids.VO.User;
import com.example.bids.controller.ErrorFoundException;
import com.example.bids.controller.ErrorResponse;
import com.example.bids.entity.Bids;
import com.example.bids.repository.BidsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BidService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private BidsRepository bidsRepository;

    @ExceptionHandler
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

    public Bids createBid(Bids bid, String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"token\": "+"\""+token+"\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        String postUrl = "http://USER-SERVICE/users/getUser";
        ResponseEntity<User> responseEntity = restTemplate.postForEntity(postUrl, requestEntity, User.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            User user = responseEntity.getBody();
            // Process the response data here
            bid.setEmail(user.getEmail());
            Optional<Bids> bidsOptional =  bidsRepository.findByAdId(bid.getAdId());
            if(bidsOptional.isEmpty()){
                bid.setTotalBids(1);
               return bidsRepository.save(bid);
            }else{
                Bids bidFetched = bidsOptional.get();
                if(bid.getBidPrice()>bidFetched.getBidPrice() && bidFetched.getBidStatus()==0){
                    bidFetched.setBidPrice(bid.getBidPrice());
                    bidFetched.setEmail(user.getEmail());
                    bidFetched.setCreationDate(new Date());
                    bidFetched.setTotalBids(bidFetched.getTotalBids()+1);
                    return bidsRepository.save(bidFetched);
                }else{
                    throw new ErrorFoundException("Bid price less than the current amount");
                }
            }
        } else {
            // Handle error responses here
            throw new ErrorFoundException("Failed to save entity");
        }
    }


    public List<Bids> viewActiveBids() {

        return bidsRepository.findByActiveStatus();
    }

    public List<Bids> viewNonActiveBids() {
        return bidsRepository.findByNonActiveStatus();
    }
}
