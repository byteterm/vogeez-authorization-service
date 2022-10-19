package systems.tat.authorization.service.util;

import com.nimbusds.jose.jwk.RSAKey;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public class RSAKeyUtil {

    public static final String PRIVATE_KEY = "/private.key";
    public static final String PUBLIC_KEY = "/public.key";
    public static final String KEY_PATH = "keys";

    private RSAKeyUtil() {

    }

    public static RSAKey getRSAKey() {
        KeyPair keyPair = loadKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    private static KeyPair loadKeyPair() {

        // Check if path exists
        Path privateKeyFilePath = Paths.get(KEY_PATH + PRIVATE_KEY);
        Path publicKeyFilePath = Paths.get(KEY_PATH + PUBLIC_KEY);

        if (!Paths.get(KEY_PATH).toFile().exists()
                || !privateKeyFilePath.toFile().exists()
                || !publicKeyFilePath.toFile().exists()) {
            return generateKeyPair();
        }

        // Load keys
        try {
            byte[] privateKeyBytes = Files.readAllBytes(privateKeyFilePath);
            byte[] publicKeyBytes = Files.readAllBytes(publicKeyFilePath);

            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            return new KeyPair(publicKey, privateKey);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Error while loading keys", e);
            return generateKeyPair();
        }
    }

    private static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            saveKeyPair(keyPair);

            return keyPair;
        } catch (NoSuchAlgorithmException e) {
            log.error("Error while generating RSA key pair", e);
            log.error("System will exit");
            System.exit(1);
            return null;
        }
    }

    private static void saveKeyPair(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        if (Paths.get("keys").toFile().mkdirs()) {
            log.info("Created directory 'keys'");
        }
        //Store Public and Private Key.
        try {
            Path privateKeyPath = Paths.get("keys", "private.key");
            Path publicKeyPath = Paths.get("keys", "public.key");

            Files.write(privateKeyPath, privateKey.getEncoded());
            Files.write(publicKeyPath, publicKey.getEncoded());
        } catch (IOException e) {
            log.error("Error while saving RSA key pair", e);
        }
    }
}
