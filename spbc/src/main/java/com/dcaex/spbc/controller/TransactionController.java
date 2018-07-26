package com.dcaex.spbc.controller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dcaex.spbc.thread.CheckThreadPoolManager;
import com.dcaex.spbc.web3j.TransactionOperate;

@RestController
@RequestMapping("/dcaex")
public class TransactionController {

    @Autowired
    CheckThreadPoolManager checkThreadPoolManager;

    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/createTransaction", method = RequestMethod.POST)
    public String createTransaction(@RequestBody String json,
                                    HttpServletRequest request) throws InterruptedException, ExecutionException, TimeoutException, IOException {

        System.out.println(json);

        JSONObject jObject=new JSONObject();
        Map<String,String> map = TransactionOperate.addJson2Eth(json);

        jObject.put("messageId",request.getHeader("messageId"));
        jObject.put("code",map.get("statuscode"));

        if (map.get("statuscode")=="0"){
            checkThreadPoolManager.addOrders(map.get("txHash"));
            jObject.put("txHash",map.get("txHash"));
        }else{
            jObject.put("error",map.get("message"));
        }
        return jObject.toString();
    }

//    @RequestMapping(value = "/test", method = {RequestMethod.POST,RequestMethod.GET})
//    public String createTransaction() {
//
//        return "test";
//    }
	
	
}
