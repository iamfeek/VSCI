package com.vsci.methods;

/**
 * Created by: Syafiq Hanafee
 * Dated: 26/5/15.
 */

import org.apache.commons.fileupload.FileItem;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;

/**
 * A utility class that encrypts or decrypts a file.
 *
 * @author www.codejava.net
 */
public class EncryptUtility {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    public static final String CIPHER_ALGORITHM = "AES";
    public static final String KEY_ALGORITHM = "AES";
    public static final String PASSWORD_HASH_ALGORITHM = "SHA-256";
    public static final Provider provider = new BouncyCastleProvider();

    public static void encrypt(Key secretKey, FileItem item, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, secretKey, item, outputFile);
    }

    public static void decrypt(Key secretKey, FileItem item, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, secretKey, item, outputFile);
    }

    private static void doCrypto(int cipherMode, Key secretKey, FileItem item,
                                 File outputFile) throws CryptoException {
        try {
//            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            byte[] inputBytes = new byte[(int) item.getSize()];
            item.getInputStream().read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            item.getInputStream().close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }

    public static Key buildKey(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digester = MessageDigest.getInstance(PASSWORD_HASH_ALGORITHM, provider);
        digester.update(String.valueOf(password).getBytes("UTF-8"));
        byte[] beforeTrimKey = digester.digest();
        byte[] key = new byte[16];

        //trims the key to match the 16bytes AES maxkeylength limitation by US Policy. I can remove that limitation, but that would mean that I have to change some jars in the JRE. mehhh..
        for (int i = 0; i < 16; i++) {
            key[i] = beforeTrimKey[i];
        }

        return new SecretKeySpec(key, KEY_ALGORITHM);

    }
}