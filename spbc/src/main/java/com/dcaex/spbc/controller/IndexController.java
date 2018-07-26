package com.dcaex.spbc.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dcaex.spbc.dto.Author;
import com.dcaex.spbc.service.elasearch.ElasearchService;
import com.dcaex.spbc.service.inputvalue.InputValueService;
import com.dcaex.spbc.web3j.TransactionOperate;

@Controller
public class IndexController {
	
	@Autowired
	private ElasearchService elasearchService;
	
	@Autowired
    private InputValueService dataSourceService;

	@RequestMapping("/toIndex")
	public String toIndex() throws IOException{
		
		List<Author> tranInfor = TransactionOperate.getInputValue();
		
        if (tranInfor.size()>0){
            dataSourceService.selectInputValue(tranInfor);
        }
		
		return "index";
	}
	
}
