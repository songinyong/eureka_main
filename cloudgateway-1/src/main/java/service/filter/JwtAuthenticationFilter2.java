package service.filter;



import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;
import service.exception.JwtTokenMalformedException;
import service.exception.JwtTokenMissingException;
import service.util.JwtUtil;

@Component
public class JwtAuthenticationFilter2 extends AbstractGatewayFilterFactory<JwtAuthenticationFilter2.Config> {
    public JwtAuthenticationFilter2() {
    	
        super(Config.class);
    }
    
	@Autowired
	private JwtUtil jwtUtil;

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();

           // final List<String> apiEndpoints = List.of("/post", "/posts");

    		//Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
    		//		.noneMatch(uri -> r.getURI().getPath().contains(uri));
            // Request Header 에 token 이 존재하지 않을 때
    		
    		//if (isApiSecured.test(request)) {
            if (!request.getHeaders().containsKey("Authorization")) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
                System.out.println("토큰 없음");
				return response.setComplete();
			}

            
            // Request Header 에서 token 문자열 받아오기
            final String token = request.getHeaders().getOrEmpty("Authorization").get(0);
            System.out.println(request.getHeaders());
            
            try {
				jwtUtil.validateToken(token);
				System.out.println("토큰 검사 통과");
			} catch (JwtTokenMalformedException | JwtTokenMissingException e) {
		
				System.out.println("토큰 검사 실패");
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.BAD_REQUEST);

				return response.setComplete();
			}

			Claims claims = jwtUtil.getClaims(token);
			exchange.getRequest().mutate().header("email", String.valueOf(claims.get("email"))).build();
		
    		//}
		return chain.filter(exchange); // 토큰이 일치할 때

        });
        
        }

    private Mono<Void> handleUnAuthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = (ServerHttpResponse) exchange.getResponse();

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return ((ReactiveHttpOutputMessage) response).setComplete();
    }

   

    public static class Config {

    } }