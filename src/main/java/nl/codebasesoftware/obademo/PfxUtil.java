package nl.codebasesoftware.obademo;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;


public class PfxUtil {

    private KeyStore keyStore;
    private String pfxPassword;

    public PfxUtil(final String pfxPath, final String pfxPassword) {
        this.pfxPassword = pfxPassword;
        keyStore = loadKeyStore(pfxPath, pfxPassword);
    }

    public Certificate getCertificate(String alias) {
        try {
            return keyStore.getCertificate(alias);
        } catch (Exception e) {
            throw new RuntimeException("Could not load certificate", e);
        }
    }

    public PublicKey getPublicKey(String alias) {
        Certificate certificate = getCertificate(alias);
        if (certificate == null) {
            throw new RuntimeException("Could not find certificate for alias " + alias);
        }
        return certificate.getPublicKey();
    }


    public Key getPrivateKey(String alias) {
        try {
            Key key = keyStore.getKey(alias, pfxPassword.toCharArray());
            if (key == null) {
                throw new RuntimeException("Could not find key for alias " + alias);
            }
            return key;
        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException e) {
            throw new RuntimeException("Could not load private key", e);
        }
    }

    public static KeyStore loadKeyStore(String pfxPath, String pfxPassword) {
        KeyStore ks = null;
        try (InputStream keyFile = PfxUtil.class.getResourceAsStream(pfxPath)) {
            if (keyFile == null) {
                throw new RuntimeException("Could not find " + pfxPath);
            }
            ks = KeyStore.getInstance("PKCS12", "SunJSSE");
            ks.load(keyFile, pfxPassword.toCharArray());
            return ks;
        } catch (KeyStoreException | NoSuchProviderException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException(e);
        }
    }

}
