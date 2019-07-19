package com.glad.credit.util;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Security;

/**
 * Created by caoyanfei079 on 4/23/15.
 */
public final class GladCryptoUtils {
    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }
    public static final String createAESPasswordAsHex() throws GladCryptoAESException {
        return createAESPasswordAsHex(AESConstants.AES_SIZE_128);
    }
    public static final String createAESPasswordAsHex(Integer length) throws GladCryptoAESException {
        byte[] result = createAESPassword(length);
        return Hex.encodeHexString(result);
    }

    public static final byte[] createAESPassword() throws GladCryptoAESException {
        return createAESPassword(AESConstants.AES_SIZE_128);
    }

    public static final byte[] createAESPassword(Integer length) throws GladCryptoAESException {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(AESConstants.ALGORITHM_AES);
            kg.init(length);
            SecretKey sk = kg.generateKey();
            return sk.getEncoded();
        } catch (Exception e) {
            throw new GladCryptoAESException("Failed to create the aes(" + AESConstants.AES_SIZE_128 + ") password", e);
        }
    }
}
