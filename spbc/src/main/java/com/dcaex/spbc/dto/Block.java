package com.dcaex.spbc.dto;

public class Block {

	private String height;
	private String timeStamp;
	private int transaction;
	private String hashValue;
	private String parentHash;
	private String sha3Uncles;
	private String miner;
	private String difficulty;
	private String totalDifficulty;
	private String size;
	private String gasUsed;
	private String gasLimit;
	private String nonce;
	private String blockReward;
	private String unclesReward;
	private String extraData;
	

	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public int getTransaction() {
		return transaction;
	}
	public void setTransaction(int transaction) {
		this.transaction = transaction;
	}
	public String getHashValue() {
		return hashValue;
	}
	public void setHashValue(String hashValue) {
		this.hashValue = hashValue;
	}
	public String getParentHash() {
		return parentHash;
	}
	public void setParentHash(String parentHash) {
		this.parentHash = parentHash;
	}
	public String getSha3Uncles() {
		return sha3Uncles;
	}
	public void setSha3Uncles(String sha3Uncles) {
		this.sha3Uncles = sha3Uncles;
	}
	public String getMiner() {
		return miner;
	}
	public void setMiner(String miner) {
		this.miner = miner;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getTotalDifficulty() {
		return totalDifficulty;
	}
	public void setTotalDifficulty(String totalDifficulty) {
		this.totalDifficulty = totalDifficulty;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getGasUsed() {
		return gasUsed;
	}
	public void setGasUsed(String gasUsed) {
		this.gasUsed = gasUsed;
	}
	public String getGasLimit() {
		return gasLimit;
	}
	public void setGasLimit(String gasLimit) {
		this.gasLimit = gasLimit;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getBlockReward() {
		return blockReward;
	}
	public void setBlockReward(String blockReward) {
		this.blockReward = blockReward;
	}
	public String getUnclesReward() {
		return unclesReward;
	}
	public void setUnclesReward(String unclesReward) {
		this.unclesReward = unclesReward;
	}
	public String getExtraData() {
		return extraData;
	}
	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}
	
	
}
