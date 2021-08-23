package com.keoch.simpleJavaBlockchain;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

import org.bouncycastle.jcajce.provider.symmetric.AES.KeyGen;

public class Wallet {
	
	//keep it a secret
	public PrivateKey privateKey;
	//share it with others
	public PublicKey publicKey;
	
	public Wallet() {
		generateKeyPair();
	}

	private void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			//Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec,random); //256 bytes provides an acceptable security level
			KeyPair keyPair = keyGen.generateKeyPair();
			//Set the public and private keys from keyPair
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
}
