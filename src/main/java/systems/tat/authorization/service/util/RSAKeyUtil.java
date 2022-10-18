package systems.tat.authorization.service.util;

import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import systems.tat.authorization.service.config.data.OAuthConfig;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;

/**
 * @author : Niklas Tat
 * @since : 0.1
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class RSAKeyUtil {

    private final OAuthConfig oAuthConfig;

    public RSAKey getRSAKey() {
        KeyPair keyPair = getKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    private KeyPair getKeyPair() {
        if (oAuthConfig.rsaPrivateKey.equals("?")
                || oAuthConfig.rsaPublicKey.equals("?")) {
            return generateKeyPair();
        }

        return loadKeyPair();
    }

    private KeyPair loadKeyPair() {
        PrivateKey privateKey = null;
        PublicKey publicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(oAuthConfig.rsaPrivateKey.getBytes());
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(oAuthConfig.rsaPublicKey.getBytes());

            privateKey = keyFactory.generatePrivate(privateKeySpec);
            publicKey = keyFactory.generatePublic(publicKeySpec);
            log.info("Loaded RSA key pair successfully from config");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Error while loading RSA key pair", e);
        }

        return new KeyPair(publicKey, privateKey);
    }

    private KeyPair generateKeyPair() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
            log.info("Generated new RSA key pair with length 2048");
            log.info("Public key: " + keyPair.getPublic().toString());
            log.info("Private key: " + keyPair.getPrivate().toString());
            log.info("Please save the public and private key in the application.properties file with the properties 'tat.authorization-service.oauth2.rsa.public-key' and 'tat.authorization-service.oauth2.rsa.private-key'");
        } catch (NoSuchAlgorithmException e) {
            log.error("Could not generate RSA key pair", e);
            throw new IllegalStateException(e);
        }

        return keyPair;
    }
}
