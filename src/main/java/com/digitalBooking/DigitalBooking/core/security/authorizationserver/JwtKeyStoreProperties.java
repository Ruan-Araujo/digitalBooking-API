package com.digitalBooking.DigitalBooking.core.security.authorizationserver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("digitalbooking.jwt.keystore")
public class JwtKeyStoreProperties {

    private Resource jksLocation;

    private String password;

    private String keyPairAlias;

}
