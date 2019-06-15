package pl.mrzeszotarski.cryptopass;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneratedKeyPair {

    private String privateKey;
    private String publicKey;

}
