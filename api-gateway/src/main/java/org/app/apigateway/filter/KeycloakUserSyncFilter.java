package org.app.apigateway.filter;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.apigateway.user.dto.RegisterRequest;
import org.app.apigateway.user.service.UserService;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserSyncFilter implements WebFilter {

    private final UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        log.debug("token : {}", token);
        RegisterRequest request=getUserDetails(token);

        if(userId == null){
            userId=request.getKeycloakId();
        }

        if (userId != null) {
            String finalUserId = userId;
            return  userService.validateUser(userId)
                    .flatMap(exists->{
                        if(!exists){

//                            Register user

                            if (request != null) {
                               return userService.registerUser(request)
                                       .then(Mono.empty());
                            }
                            else return Mono.empty();

                        }else {
                            log.info("user already exists , skipping sync !");
                            return Mono.empty();
                        }
                    })
                    .then(Mono.defer(
                            ()->{
                                ServerHttpRequest HttpRequest = exchange.getRequest().mutate()
                                        .header("X-User-ID", finalUserId)
                                        .build();
                                return chain.filter(exchange.mutate().request(HttpRequest).build());

                            }
                    ))
                    ;
        }

        return chain.filter(exchange);
    }

    private RegisterRequest getUserDetails(String token){
        String tokenWithoutBearer=token.replace("Bearer ", "");
        try {
            SignedJWT signedJWT=SignedJWT.parse(tokenWithoutBearer);
            JWTClaimsSet claims=signedJWT.getJWTClaimsSet();
            return RegisterRequest.builder()
                    .email(claims.getStringClaim("email"))
                    .password("dummy@1234")
                    .keycloakId(claims.getStringClaim("sub"))
                    .firstName(claims.getStringClaim("given_name"))
                    .lastName(claims.getStringClaim("family_name"))
                    .build();

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
