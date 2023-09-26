package com.example.gateway.filter;



import com.example.gateway.Controller.ErrorFoundException;
import com.example.gateway.Controller.ErrorResponse;
import com.example.gateway.config.JwtUtil;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Component
public class JwtRequest extends AbstractGatewayFilterFactory<JwtRequest.Config> {

    @Autowired
    private RouteValidator validator;

    @ExceptionHandler()
    public ResponseEntity<ErrorResponse> handleException(ErrorFoundException exc){
        ErrorResponse error = new ErrorResponse();
        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JwtUtil jwtUtil;

    public JwtRequest() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange,chain)->{

        if(validator.isSequred.test(exchange.getRequest())){
            if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                throw new ErrorFoundException("Authorization Header Missing");
            }

            String authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            if(authHeaders!=null && authHeaders.startsWith("Bearer ")){
                authHeaders = authHeaders.substring(7);
            }
            try{
//                restTemplate.getForObject("http://localhost:9191/users/validate?token"+authHeaders,)
                    jwtUtil.validateToken(authHeaders);
            }catch (Exception e){
                throw new ErrorFoundException("unauthorized");
            }

        }

            return chain.filter(exchange);
        });
    }

    public static class Config{
    }
}