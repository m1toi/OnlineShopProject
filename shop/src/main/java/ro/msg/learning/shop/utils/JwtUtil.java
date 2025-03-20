//package ro.msg.learning.shop.utils;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.stereotype.Component;
//
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.PublicKey;
//import java.security.interfaces.RSAPublicKey;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//    private final KeyPair keyPair;
//
//    public JwtUtil() throws Exception {
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//        keyPairGenerator.initialize(2048);
//        this.keyPair = keyPairGenerator.generateKeyPair();
//    }
//
//    public String generateToken(Authentication authentication) {
//        var authorities = authentication.getAuthorities();
//        String roles = authorities.stream()
//                .map(a -> a.getAuthority())
//                .reduce((a, b) -> a + "," + b)
//                .orElse("");
//
//        return Jwts.builder()
//                .setSubject(authentication.getName())
//                .claim("authorities", roles)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day validity
//                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
//                .compact();
//    }
//
//    public Jws<Claims> validateToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(keyPair.getPublic())
//                .build()
//                .parseClaimsJws(token);
//    }
//
//    @Bean
//    public PublicKey getPublicKey() {
//        return keyPair.getPublic();
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        return NimbusJwtDecoder.withPublicKey((RSAPublicKey) keyPair.getPublic()).build();
//    }
//
//}
