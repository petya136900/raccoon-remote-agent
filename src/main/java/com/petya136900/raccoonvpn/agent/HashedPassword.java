package com.petya136900.raccoonvpn.agent;

import com.petya136900.racoonvpn.tools.Tools;

public class HashedPassword {
    private String hashedPassword;
    protected HashedPassword() {}
    public static HashedPassword hash(String hash) {
        return new HashedPassword().setHash(hash);
    }
    public static HashedPassword password(String password) {
        return new HashedPassword().setPassword(password);
    }
    public String getHash() {
        return this.hashedPassword;
    }
    private HashedPassword setPassword(String password) {
        this.hashedPassword = Tools.hashSHA256(password);
        return this;
    }
    private HashedPassword setHash(String hash) {
        this.hashedPassword = hash;
        return this;
    }
}
