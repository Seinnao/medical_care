package com.whz.config;

import com.whz.dto.UserDTO;
import com.whz.utils.RedisUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 文辉正
 * @since 2023/4/4 22:46
 */
@Component
@Slf4j
@Data
public class JwtGenerator {

    @Autowired
    RedisUtil redisUtil;

    private Key privateKey;
    private Key publicKey;

    @Value("${jwt.subject}")
    private String subject;

    @Value("${jwt.issuer}")
    private String issuer;//发行人

    //生成公钥和私钥
    @PostConstruct
    public void init() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();

        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();

        //公钥存放到redis
        redisUtil.set("publicKey",this.publicKey.getEncoded());
    }

    public String generateJwt(UserDTO userDTO) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 设置 JWT Header
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "RS256");
        header.put("typ", "JWT");

        Map<String, Object> body = new HashMap<>();
        body.put("authorities",userDTO.getRole());
        body.put("id",userDTO.getId());
        body.put("username",userDTO.getUsername());

        //利
        return Jwts.builder()
                .setHeader(header)
                .setClaims(body)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(new Date(nowMillis + 3600000L))  //到期时间
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

}
