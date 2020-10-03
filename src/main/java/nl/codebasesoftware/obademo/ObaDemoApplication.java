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
        UUID applicationId = UUID.fromString("7eb0a927-fa91-11ea-9969-15885d1f310a");
        UUID publicKeyId = UUID.fromString("88d5b4e9-fa91-11ea-9969-d97ea1c01426");
        KeyStore keyStore = PfxUtil.loadKeyStore("/keys/signing-keystore.pfx", "Smeerkaas123!");
        Key signingKey = keyStore.getKey("signing", "Smeerkaas123!".toCharArray());
        return new Oba.Builder(Oba.ObaEnvironment.PRODUCTION, applicationId, signingKey, publicKeyId)
                .setLoggingConfig(new Oba.LoggingConfig(true))
                .setHttpClientSetting(new Oba.HttpClientConfig(5000, 5000, 2, 10, true))
                .build();
    }
}
