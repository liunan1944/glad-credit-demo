package com.glad.credit.util;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * Created by caoyanfei079 on 4/23/15.
 */
public final class GladAES {
    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }
    private final byte[] passwordBytes;
    private final Integer keySize;

    public GladAES(byte[] passwordBytes) {
        this(passwordBytes, AESConstants.AES_SIZE_128);
    }
    public GladAES(byte[] passwordBytes, Integer keySize) {
        this.passwordBytes = passwordBytes;
        this.keySize = keySize;
    }
    protected Cipher getEncryptCipher() throws GladCryptoAESException {
        return getCipher(Cipher.ENCRYPT_MODE);
    }

    protected Cipher getDecryptCipher() throws GladCryptoAESException {
        return getCipher(Cipher.DECRYPT_MODE);
    }

    protected Cipher getCipher(Integer mode) throws GladCryptoAESException {
        try {
            SecretKeySpec key = new SecretKeySpec(passwordBytes, AESConstants.ALGORITHM_AES);
            Cipher cipher = Cipher.getInstance(AESConstants.CIPHER_ALGORITHM_CBC,AESConstants.BC_PROVIDER);// ����������
            cipher.init(mode, key, new IvParameterSpec(AESConstants.DEFAULT_ROOT_IV.getBytes()));// ��ʼ��
            return cipher;
        }catch (Exception e) {
            throw new GladCryptoAESException("Failed to get the cipher with passwordBytes [" + passwordBytes + "] key size + [" + keySize + "] mode [" + mode + "]", e);
        }
    }


    public byte[] encrypt(String content) throws GladCryptoAESException {
        try {
            byte[] byteContent = content.getBytes(AESConstants.DEFAULT_CHARSET);
            return encrypt(byteContent);
        } catch (Exception e) {
            throw new GladCryptoAESException("failed to encryptInputStream the content [" + content + "]",e);
        }
    }
    public byte[] decrypt(String content) throws GladCryptoAESException {
        try {
            byte[] byteContent = content.getBytes(AESConstants.DEFAULT_CHARSET);
            return decrypt(byteContent);
        } catch (Exception e) {
            throw new GladCryptoAESException("failed to decryptOutputStream the content [" + content + "]",e);
        }
    }

    public byte[] encrypt(byte[] content) throws GladCryptoAESException {
        try {
            return getEncryptCipher().doFinal(content);
        } catch (Exception e) {
            throw new GladCryptoAESException("failed to encryptInputStream the content [" + content + "]",e);
        }
    }
    public byte[] decrypt(byte[] content) throws GladCryptoAESException {
        try {
            return getDecryptCipher().doFinal(content);
        } catch (Exception e) {
            throw new GladCryptoAESException("failed to Decrypt the content [" + content + "]",e);
        }
    }
}
