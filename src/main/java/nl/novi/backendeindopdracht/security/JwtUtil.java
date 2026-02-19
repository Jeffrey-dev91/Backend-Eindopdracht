package nl.novi.backendeindopdracht.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;


@Component
public class JwtUtil {



    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long expirationMs = 1000 * 60 * 60;



    public String generateToken(String username, List<String> roles) {


        return Jwts.builder()
                .setSubject(username)
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key)
                .compact();

    }


    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }


    public List<String> getRoles(String token){

        return getClaims(token).get("roles",List.class);

    }




    public boolean isTokenValid(String token, UserDetails userDetails){

        try{
            getClaims(token);
            return true;
        } catch (Exception e){
            return false;
        }

    }



    private Claims getClaims(String token){

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();


    }


}
