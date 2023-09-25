package com.example.bids.service;

import com.example.bids.VO.User;
import com.example.bids.controller.ErrorFoundException;
import com.example.bids.entity.Ads;
import com.example.bids.entity.Comments;
import com.example.bids.repository.AdsRepository;
import com.example.bids.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private RestTemplate restTemplate;

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Extract the token part after "Bearer "
        }
        return null; // Return null or handle invalid headers as needed
    }

    public Comments addComment(Comments comments, String authorizationHeader) {
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
            comments.setEmail(user.getEmail());
            Ads ad = adsRepository.findByAdId(comments.getAid());
            if(ad.getAdStatus()==1){
                return commentsRepository.save(comments);
            }else{
                throw new ErrorFoundException("comment section closed");
            }

        } else {
            // Handle error responses here
            throw new RuntimeException("Failed to save entity");
        }
    }

    public List<Comments> viewComment(int adId){
            return commentsRepository.findByAid(adId);

    }



}
