package com.dcaex.spbc.web3j;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.*;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import com.dcaex.spbc.dto.Author;
import com.dcaex.spbc.dto.Block;
import com.dcaex.spbc.dto.Transactions;
import com.dcaex.spbc.utils.GsonUtil;
import com.dcaex.spbc.utils.StringUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class TransactionOperate {


    /**rop测试网【发布测试环境时注意切换】*/
//    private static final String Ethurl = "http://39.105.84.55:8545/";
//    private static final String mainAcc = "0x4bcf0d6faea61d00539503a84ca61b8ee5e27491";
//    private static final String MainAccPassword = "dcaex2";
//    private static final String TexasPokerImageContractAdd = "0x64dd5d45b444cfe1442496d46c8003749220a2c2";

	  private static final String Ethurl = "http://localhost:8545/";
	  private static final String mainAcc = "0x3e9749507a178662875852f80b3f8c7b47b58eef";
	  private static final String MainAccPassword = "s1mple";
	  private static final String TexasPokerImageContractAdd = "0x7a15739def3350a161d0dc029cae5cfb41f7a2bd";

//    private static final String Ethurl = "http://localhost:8545/";
//    private static final String mainAcc = "0x4b7f6762f2a0ab62bd8ce882a4b8dac6949d5819";
//    private static final String MainAccPassword = "ypc920";
//    private static final String TexasPokerImageContractAdd = "0x31b5110830fdc931f1bad7113e4b91442adbf606";

    private static final BigInteger ACCOUNT_UNLOCK_DURATION = BigInteger.valueOf(10000);
    private static final Web3j web3 = Web3j.build(new HttpService(Ethurl));  // defaults to http://localhost:8545/

    private static Map<String,BigInteger> nonceMap= new HashMap<String, BigInteger>();
    
    static{
        nonceMap.put(mainAcc+"-nonce",  BigInteger.valueOf(0));
    }

    @SuppressWarnings({ "unused", "rawtypes" })
	public static Map addJson2Eth(String  inputJson) throws IOException, InterruptedException, ExecutionException, TimeoutException {

        Admin admin =Admin.build(new HttpService(Ethurl));
        PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(mainAcc,
                MainAccPassword,ACCOUNT_UNLOCK_DURATION).sendAsync().get(5, TimeUnit.MINUTES);

        BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000L);
        BigInteger GAS_LIMIT = BigInteger.valueOf(400_000);
        BigInteger value = Convert.toWei("1", Convert.Unit.ETHER).toBigInteger();
        String hexString = Numeric.toHexString(inputJson.getBytes());

        Transaction transaction = Transaction.createFunctionCallTransaction(mainAcc,getTransactionNonce(mainAcc),GAS_PRICE,GAS_LIMIT,
                TexasPokerImageContractAdd,value,hexString);

        EthSendTransaction ethSendTransaction = admin.ethSendTransaction(transaction).sendAsync().get(5,TimeUnit.MINUTES);

        Map<String,String> map = new HashMap<String,String>();

        Response.Error err = ethSendTransaction.getError();
        if (err!=null){
            map.put("statuscode",String.valueOf(err.getCode()));
            map.put("message",err.getMessage());
            System.out.println("err.code ==> "+err.getCode());
            System.out.println("err.data ==> "+err.getData());
            System.out.println("err.message ==> "+err.getMessage());
        }else{
            String txHash = ethSendTransaction.getTransactionHash();
            map.put("statuscode","0");
            map.put("txHash",txHash);
        }



//        EthTransaction ethTransaction=admin.ethGetTransactionByHash(txHash).send();
//
//        org.web3j.protocol.core.methods.response.Transaction transactionResult = ethTransaction.getResult();
//
//        String inputData16 = transactionResult.getInput().substring(2);
//        System.out.println(StringUtils.hexStringToString(inputData16));
        return map;
    }
    /**
     * 获取账号交易次数 nonce
     *
     * @param address 钱包地址
     * @return nonce
     */
    public static BigInteger getTransactionNonce(String address) {
        BigInteger nonce = BigInteger.ZERO;
        try {
            EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(address, DefaultBlockParameterName.PENDING).send();
            nonce = ethGetTransactionCount.getTransactionCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nonce;
    }

    /**
     * 获取交易blocknumber来判断交易状态
     *
     * @param txHash 交易哈希
     * @return bool
     */
    public static boolean getTranStatusByHash(String txHash){
        boolean bool = false;

        Admin admin =Admin.build(new HttpService());

        EthGetTransactionReceipt ethTransaction = null;
        try {
            ethTransaction = admin.ethGetTransactionReceipt(txHash).send();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TransactionReceipt transactionResult = ethTransaction.getResult();
        

        BigInteger blocknumber = null;

        try {
            blocknumber = transactionResult.getBlockNumber();
        } catch (NullPointerException e) {
            blocknumber = null;
        }

        if(blocknumber!=null){
            bool = true;
        }

        return bool;

    }

    /**
     * 将链上的输入的值持久化到数据库
     * @return
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
	public static List<Author> getInputValue() throws IOException{
        List<Author> list = new ArrayList<>();
        Admin admin =Admin.build(new HttpService(Ethurl));
        BigInteger bi = web3.ethBlockNumber().send().getBlockNumber();//查询块的高度
        BigInteger j = BigInteger.valueOf(1);
        BigInteger i = BigInteger.valueOf(1);
        for (;i.compareTo(bi)<=0;i=i.add(j)){
            DefaultBlockParameter dbp = new DefaultBlockParameterNumber(i);
            Request<?, EthBlock> request = web3.ethGetBlockByNumber(dbp, true);
            EthBlock ethBlock = request.send();
            EthBlock.Block block = ethBlock.getBlock();//遍历获取每一个块

            List<EthBlock.TransactionResult> transactions = block.getTransactions();//查询块里面的交易
            if (transactions.size() > 0){
                BigInteger m = BigInteger.valueOf(transactions.size());
                BigInteger n = BigInteger.valueOf(0);

                //遍历每一个交易信息
                for (;n.compareTo(m)<0;n=n.add(j)){
                    Request<?, EthTransaction> request1 = admin.ethGetTransactionByBlockNumberAndIndex(dbp, n);
                    EthTransaction ethTransaction = request1.send();
                    org.web3j.protocol.core.methods.response.Transaction result = ethTransaction.getResult();

                    
                    String inputData = result.getInput().substring(2);
                    String inputValue = StringUtils.hexStringToString(inputData);

                    Author author = null;
                    
                    try {
                    	author = GsonUtil.parseJsonWithGson(inputValue, Author.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
                    
                    list.add(author);
                }
            }
        }

        return list;
    }
    
    /**
     * 获取块的数据
     * @return
     * @throws IOException
     */
	@SuppressWarnings("rawtypes")
	public static List<Block> selectBlockList() throws IOException{
        List<Block> list = new ArrayList<>();
        BigInteger bi = web3.ethBlockNumber().send().getBlockNumber();//查询块的高度
        BigInteger i = BigInteger.valueOf(1);
        for (;i.compareTo(bi)<=0;bi=bi.subtract(i)){
            DefaultBlockParameter dbp = new DefaultBlockParameterNumber(bi);
            Request<?, EthBlock> request = web3.ethGetBlockByNumber(dbp, true);
            EthBlock ethBlock = request.send();
            EthBlock.Block block = ethBlock.getBlock();//遍历获取每一个块

            List<EthBlock.TransactionResult> transactions = block.getTransactions();//查询块里面的交易
            

            if (transactions.size() > 0){
            	
            	Block blo = new Block();
            	
            	blo.setHeight(bi.toString());
            	blo.setTransaction(transactions.size());
            	blo.setDifficulty(block.getDifficulty().toString());
            	blo.setExtraData(block.getExtraData());
            	blo.setGasLimit(block.getGasLimit().toString());
            	blo.setGasUsed(block.getGasUsed().toString());
            	blo.setHashValue(block.getHash());
            	blo.setMiner(block.getMiner());
            	blo.setNonce(block.getNonce().toString());
            	blo.setParentHash(block.getParentHash());
            	blo.setSha3Uncles(block.getParentHash());
            	blo.setSize(block.getSize().toString());
            	blo.setTimeStamp(block.getTimestamp().toString());
            	blo.setTotalDifficulty(block.getTotalDifficulty().toString());
            	
            	list.add(blo);
            	
            	blo=null;
            	
            	if (list.size()>10) {
					return list;
				}
            	
            }
        }
        return list;
    }
	
	/**
	 * 查询交易信息
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Transactions> selectTransactionList() throws IOException {
        List<Transactions> tlist = new ArrayList<Transactions>();
        Admin admin =Admin.build(new HttpService(Ethurl));
        BigInteger bi = web3.ethBlockNumber().send().getBlockNumber();//查询块的高度
        BigInteger i = BigInteger.valueOf(1);
        
        for (;i.compareTo(bi)<=0;bi=bi.subtract(i)){
        	
            DefaultBlockParameter dbp = new DefaultBlockParameterNumber(bi);
            Request<?, EthBlock> request = web3.ethGetBlockByNumber(dbp, true);
            EthBlock ethBlock = request.send();
            EthBlock.Block block = ethBlock.getBlock();//遍历获取每一个块

            List<EthBlock.TransactionResult> transactions = block.getTransactions();//查询块里面的交易
            
//            for (TransactionResult transactionResult : transactions) {
//            	EthGetTransactionReceipt ethTransaction = admin.ethGetTransactionReceipt(transactionResult.toString()).send();
//            	TransactionReceipt result = ethTransaction.getResult();
//			}
            
            if (transactions.size() > 0){
            	
                BigInteger m = BigInteger.valueOf(transactions.size());
                BigInteger n = BigInteger.valueOf(0);
                //遍历每一个交易信息
                for (;n.compareTo(m)<0;n=n.add(i)){
                	
                	Transactions tran = new Transactions();
                	
                    Request<?, EthTransaction> request1 = admin.ethGetTransactionByBlockNumberAndIndex(dbp, n);
                    EthTransaction ethTransaction = request1.send();
                    org.web3j.protocol.core.methods.response.Transaction result = ethTransaction.getResult();
                    
                    tran.setBlock(bi.toString());
                    tran.setFrom(result.getFrom());
                    tran.setGasPrice(result.getGasPrice().toString());
                    tran.setInputData(result.getInput());
                    tran.setNonce(result.getNonce().toString());
                    tran.setTo(result.getTo());
                    tran.setTxHash(result.getHash());
                    tran.setValue(result.getValue().toString());

                    tlist.add(tran);
                    tran = null;
                    if (tlist.size()>10) {
                    	return tlist;
					}
                }
            }
        }
        
		return tlist;
	}
	
	/**
	 * 根据块高度查询区块信息
	 * @param searchValue
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	public static Block selectByBlockNumber(String searchValue) throws IOException {
		BigInteger bi = BigInteger.valueOf(Integer.valueOf(searchValue));
        DefaultBlockParameter dbp = new DefaultBlockParameterNumber(bi);
        Request<?, EthBlock> request = web3.ethGetBlockByNumber(dbp, true);
        EthBlock ethBlock = request.send();
        org.web3j.protocol.core.methods.response.EthBlock.Block block = ethBlock.getBlock();
    	
        Block blo = new Block();
        
        blo.setHeight(bi.toString());
        List<TransactionResult> list = block.getTransactions();
    	blo.setTransaction(list.size());
    	blo.setDifficulty(block.getDifficulty().toString());
    	blo.setExtraData(block.getExtraData());
    	blo.setGasLimit(block.getGasLimit().toString());
    	blo.setGasUsed(block.getGasUsed().toString());
    	blo.setHashValue(block.getHash());
    	
    	blo.setMiner(block.getMiner());
    	
    	blo.setNonce(block.getNonce().toString());
    	blo.setParentHash(block.getParentHash());
    	blo.setSha3Uncles(block.getParentHash());
    	blo.setSize(block.getSize().toString());
    	blo.setTimeStamp(block.getTimestamp().toString());
    	blo.setTotalDifficulty(block.getTotalDifficulty().toString());
		
		return blo;
		
	}
	
	/**
	 * 根据交易Hash查询信息
	 * @param searchValue
	 * @return 
	 * @throws IOException 
	 */
	public static Transactions selectByTxHash(String searchValue) throws IOException {
		
		Admin admin =Admin.build(new HttpService(Ethurl));
		EthGetTransactionReceipt ethTransaction = admin.ethGetTransactionReceipt(searchValue).send();
    	TransactionReceipt result = ethTransaction.getResult();
    	
    	Transactions tran = new Transactions();
    	
        tran.setBlock(result.getBlockNumber().toString());
        tran.setFrom(result.getFrom());
        tran.setTo(result.getTo());
        tran.setTxHash(searchValue);
        tran.setTxReceiptStatus(result.getStatus());
        tran.setGasUsed(result.getGasUsed().toString()); 
        
        BigInteger index = result.getTransactionIndex();
        BigInteger bn = result.getBlockNumber();
        DefaultBlockParameter dbp = new DefaultBlockParameterNumber(bn);
        Request<?, EthTransaction> request1 = admin.ethGetTransactionByBlockNumberAndIndex(dbp, index);
        EthTransaction ethTransaction1 = request1.send();
        org.web3j.protocol.core.methods.response.Transaction result1 = ethTransaction1.getResult();
        
        tran.setValue(result1.getValue().toString());
        tran.setInputData(result1.getInput());
        tran.setNonce(result1.getNonce().toString());
        tran.setGasPrice(result1.getGasPrice().toString());
        tran.setTxFree(result1.getGas().toString());
        
        Request<?, EthBlock> request = web3.ethGetBlockByNumber(dbp, true);
        EthBlock ethBlock = request.send();
        EthBlock.Block block = ethBlock.getBlock();
        
        tran.setGasLimit(block.getGasLimit().toString());
        
        
        
		return tran;
    	
	}
	
	/**
	 * 根据地址查询交易信息
	 * @param searchValue
	 * @return
	 * @throws IOException 
	 */
	public static List<Transactions> selectByAddress(String searchValue) throws IOException {
		
        List<Transactions> tlist = new ArrayList<Transactions>();
        Admin admin =Admin.build(new HttpService(Ethurl));
        BigInteger bi = web3.ethBlockNumber().send().getBlockNumber();//查询块的高度
        BigInteger i = BigInteger.valueOf(1);
        
        for (;i.compareTo(bi)<=0;bi=bi.subtract(i)){
        	
            DefaultBlockParameter dbp = new DefaultBlockParameterNumber(bi);
            Request<?, EthBlock> request = web3.ethGetBlockByNumber(dbp, true);
            EthBlock ethBlock = request.send();
            EthBlock.Block block = ethBlock.getBlock();//遍历获取每一个块

            List<EthBlock.TransactionResult> transactions = block.getTransactions();//查询块里面的交易
            
//            for (TransactionResult transactionResult : transactions) {
//            	EthGetTransactionReceipt ethTransaction = admin.ethGetTransactionReceipt(transactionResult.toString()).send();
//            	TransactionReceipt result = ethTransaction.getResult();
//			}
            
            if (transactions.size() > 0){
            	
                BigInteger m = BigInteger.valueOf(transactions.size());
                BigInteger n = BigInteger.valueOf(0);
                //遍历每一个交易信息
                for (;n.compareTo(m)<0;n=n.add(i)){
                	
                	Transactions tran = new Transactions();
                	
                    Request<?, EthTransaction> request1 = admin.ethGetTransactionByBlockNumberAndIndex(dbp, n);
                    EthTransaction ethTransaction = request1.send();
                    org.web3j.protocol.core.methods.response.Transaction result = ethTransaction.getResult();
                    
                    if (result.getFrom().equals(searchValue)) {
                    	
                        tran.setBlock(bi.toString());
                        tran.setFrom(result.getFrom());
                        tran.setTo(result.getTo());
                        tran.setTxHash(result.getHash());
                        tran.setValue(result.getValue().toString());
                        tran.setTxFree(result.getGas().toString());
                        
					}else if (result.getTo().equals(searchValue)) {
						
                        tran.setBlock(bi.toString());
                        tran.setFrom(result.getFrom());
                        tran.setTo(result.getTo());
                        tran.setTxHash(result.getHash());
                        tran.setValue(result.getValue().toString());
                        tran.setTxFree(result.getGas().toString());
                        
					}

                    if (tran!=null) {
                    	tlist.add(tran);
					}
                }
            }
        }
		
		return tlist;
	}

    

}
