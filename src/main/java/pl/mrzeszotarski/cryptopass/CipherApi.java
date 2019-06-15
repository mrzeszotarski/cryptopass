package pl.mrzeszotarski.cryptopass;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@RestController
@RequestMapping("/cipher")
public class CipherApi {

    private RSAGenerator rsaGenerator = new RSAGenerator();
    private CipherPhrase cipherPhrase = new CipherPhrase();

    @ApiOperation(value = "/generate")
    @GetMapping("/generate")
    public GeneratedKeyPair generate() throws NoSuchAlgorithmException {
        rsaGenerator.generate();
        return new GeneratedKeyPair(Base64.getEncoder().encodeToString(rsaGenerator.getPrivateKey().getEncoded()), Base64.getEncoder().encodeToString(rsaGenerator.getPublicKey().getEncoded()));
    }

    @ApiOperation(value = "/encrypt")
    @PostMapping("/encrypt")
    public String encrypt(String dataToEncrypt, String publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        return Base64.getEncoder().encodeToString(cipherPhrase.encrypt(dataToEncrypt, publicKey));
    }

    @ApiOperation(value = "/decrypt")
    @PostMapping("/decrypt")
    public String decrypt(String encryptedData, String privateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return cipherPhrase.decode(Base64.getDecoder().decode(encryptedData), privateKey);
    }
}
