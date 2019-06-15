package pl.mrzeszotarski.cryptopass;

import org.junit.Assert;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class CipherPhraseTest {

    private CipherPhrase cipherPhrase = new CipherPhrase();

    @Test
    public void generationTest() throws IOException, NoSuchAlgorithmException {
        RSAGenerator generator = new RSAGenerator();
        generator.generate();
        generator.writeToFile("private1", Base64.getEncoder().encodeToString(generator.getPrivateKey().getEncoded()).getBytes());
        generator.writeToFile("public1", Base64.getEncoder().encodeToString(generator.getPublicKey().getEncoded()).getBytes());

    }

    @Test
    public void encryptionDecryptionTest() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        byte[] privateKeyBytes = Files.readAllBytes(Paths.get("private1"));
        byte[] publicKeyBytes = Files.readAllBytes(Paths.get("public1"));

        byte[] input = cipherPhrase.encrypt("test", Base64.getEncoder().encodeToString(Base64.getDecoder().decode(publicKeyBytes)));
        String decode = cipherPhrase.decode(input, Base64.getEncoder().encodeToString(Base64.getDecoder().decode(privateKeyBytes)));

        Assert.assertFalse(Base64.getEncoder().encodeToString(input).equals("test"));
        Assert.assertTrue(decode.equals("test"));
    }

}
