package com.springbank.user.auth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

@Configuration
@RequiredArgsConstructor
@Slf4j
// extends AuthorizationServerConfigurerAdapter
public class AuthServerConfig {

    @Value("${spring.security.oauth2.client.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.signing-key}")
    private String signingKey;
    @Value("${spring.security.oauth2.client.token-validity-seconds}")
    private Integer tokenValidity;
    @Value("${spring.security.oauth2.client.refresh-validity-seconds}")
    private Integer refreshTokenValidity;


    private final AuthenticationManager authenticationManager;


//    @Bean
//    public JwtAcccessTokenConverter tokenConverter(){
//       var token =  new JwtAcccessTokenConverter();
//       token.setSigningKey(signingKey);
//       return token;
//    }
//
//    @Bean
//    public JwtTokenStore tokenStore(){
//        return new JwtTokenStore(tokenConverter());
//    }
//
//    public void configure(AuthorizationServerEndpointsConfigurer endpointsConfigurer){
//        endpointsConfigurer
//                .authenticationManager(authenticationManager)
//                .tokenStore(tokenStore())
//                .accessTokenConverter(tokenConverter());
//    }
//
//    public void configure(AuthorizationServerSecurityConfigurer serverSecurityConfigurer){
//        serverSecurityConfigurer
//                .tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()");
//    }
//
//    public void configure(ClientDetailsServiceConfigurer clients){
//        clients
//                .inMemory()
//                .withClientId(clientId)
//                .secret(new BCryptPasswordEncoder(12).encode(clientSecret))
//                .scopes("read", "write")
//                .authorizedGrantTypes("password", "refresh_token")
//                .accessTokenValiditySeconds(tokenValidity)
//                .refreshTokenValiditySeconds(refreshTokenValidity);
//    }







// spring docs
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }


}
