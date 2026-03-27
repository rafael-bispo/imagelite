package br.com.estudo.imageliteapi.domain;

import br.com.estudo.imageliteapi.application.jwt.SecretKeyGenerator;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccessToken {

    private String accessToken;
}
