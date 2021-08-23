package com.keoch.simpleJavaBlockchain;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {

	public String transactionId; // this is also the hash of the transaction.
	public PublicKey sender;// sender's address/public key.
	public PublicKey recipient;// recipient's address/public key.
	public float value;
	public byte[] signature; // this is to prevent anybody else from spending funds in our wallet.

	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

	private static int sequence = 0;// a rough count of how many transactions have been generated.

	// Constructor
	public Transaction(PublicKey sender, PublicKey recipient, float value, ArrayList<TransactionInput> inputs) {
		super();
		this.sender = sender;
		this.recipient = recipient;
		this.value = value;
		this.inputs = inputs;
	}

	// This Calculates the transaction hash (which will be used as its Id)
	private String calculateHash() {
		sequence++;//increase the sequence to avoid 2 identical transactions having the same hash
		return StringUtil.applySha256(
				StringUtil.getStringFromKey(sender)+
				StringUtil.getStringFromKey(recipient)+
				Float.toString(value)+sequence
				);
	}
	
	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtil.getStringFromKey(sender)+StringUtil.getStringFromKey(recipient)+Float.toString(value);
		signature = StringUtil.applyECDSASig(privateKey, data);
	}
	
	public boolean verifySignature() {
		String data = StringUtil.getStringFromKey(sender)+StringUtil.getStringFromKey(recipient)+Float.toString(value);
		return StringUtil.verifyECDSASig(sender, data, signature);
	}
}
