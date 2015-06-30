package com.vsci.methods;

/**
 * Created by: Syafiq Hanafee
 * Dated: 26/5/15.
 */
public class CryptoException extends Exception {

    public CryptoException() {
    }

    public CryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
