package nl.codebasesoftware.obademo;

import com.obaccelerator.sdk.Oba;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.security.*;
import java.util.UUID;

@SpringBootApplication
public class ObaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObaDemoApplication.class, args);
    }

    @Bean
    public Oba oba() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        UUID applicationId = UUID.fromString("f02c60ee-df96-11ea-9900-77034432e766");
        UUID publicKeyId = UUID.fromString("ff009ce0-df96-11ea-9900-c33e7036ef6d");
        KeyStore keyStore = PfxUtil.loadKeyStore("/keys/signing-keystore.pfx", "Smeerkaas123!");
        Key signingKey = keyStore.getKey("signing", "Smeerkaas123!".toCharArray());
        return new Oba.Builder(Oba.ObaEnvironment.PRODUCTION, applicationId, signingKey, publicKeyId)
                .setLoggingConfig(new Oba.LoggingConfig(true))
                .setHttpClientSetting(new Oba.HttpClientConfig(5000, 5000, 2, 10, true))
                .build();
    }
}
