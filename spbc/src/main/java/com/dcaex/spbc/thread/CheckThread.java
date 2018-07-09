package com.dcaex.spbc.thread;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dcaex.spbc.utils.HttpUtils;
import com.dcaex.spbc.web3j.TransactionOperate;


/**
 * Created by Administrator on 2018/5/9.
 */
@Component
@Scope("prototype")//spring 多例
public class CheckThread implements Runnable{

    private String txHash;

    public CheckThread(String txHash){
        this.txHash = txHash;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    @Override
    public void run() {

        String url = "http://172.184.6.6:8080/api/callback";

        //业务操作
        System.out.println("交易查询开始，交易码："+txHash);

        boolean bool = true;
        int time = 0;

        while (!TransactionOperate.getTranStatusByHash(txHash)){//交易未成功

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time++;
            if(time==10){
                bool = false;
                break;
            }
            System.out.println("区块打包未成功！等待再次查询！");

        }
        //业务操作
        System.out.println("交易查询结束，交易码："+txHash);

        JSONObject jObject=new JSONObject();
        jObject.put("txHash",txHash);
        if (bool){
            jObject.put("code","0");
        }else{
            jObject.put("code","-1");
        }

        //回调接口
        HttpUtils.sendPost(url,jObject.toString());
    }


}