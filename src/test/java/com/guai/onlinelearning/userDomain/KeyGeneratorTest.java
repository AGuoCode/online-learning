package com.guai.onlinelearning.userDomain;

import com.guai.onlinelearning.utilities.GenerateKey;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

public class KeyGeneratorTest {

    @Test
    void generateKey() throws NoSuchAlgorithmException {
        GenerateKey generateKey = new GenerateKey();
        System.out.println("Generated Key : " + generateKey.generateKey());
    }
}
