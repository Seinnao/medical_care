package com.whz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.security.interfaces.RSAPublicKey;

/**
 * @author 文辉正
 * @since 2023/4/4 22:40
 */
@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtGenerator jwtGenerator;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //禁用csrf 允许通过post请求
                .authorizeRequests()
                .antMatchers("/user/login",
                        "/user/captcha",
                        "/user/register",
                        "/user/app-login").permitAll()
                .antMatchers("/**").hasAnyRole("USER","ADMIN","DOCTOR")
                .and()
                .oauth2ResourceServer().jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                .decoder(jwtDecoder());
    }


    private JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey((RSAPublicKey) jwtGenerator.getPublicKey()).build();
    }

    /**
     * 配置jwt令牌的那个字段用于权限认证
     * */
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthoritiesClaimName("authorities");
        converter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtAuthenticationConverter;
    }

}
