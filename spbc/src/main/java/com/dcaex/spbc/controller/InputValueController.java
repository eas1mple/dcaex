package com.dcaex.spbc.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcaex.spbc.dto.Author;
import com.dcaex.spbc.service.inputvalue.InputValueService;
import com.dcaex.spbc.web3j.TransactionOperate;

@Controller
public class InputValueController {

    @Autowired
    private InputValueService dataSourceService;

    @ResponseBody
    @RequestMapping(value = "selectInputValue", method = RequestMethod.POST)
    public String selectInputValue(HttpServletResponse response) throws IOException {
    	List<Author> tranInfor = TransactionOperate.getInputValue();
        boolean b = false;
        if (tranInfor.size()>0){
        	try {
        		b = dataSourceService.insertInputValue(tranInfor);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }else{
            return "No such data !!!";
        }
        if (b){
            return "Successfully !!!";
        }else {
            return "Failure !!!";
        }
    }
	
}
