package com.dcaex.spbc.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dcaex.spbc.dto.Block;
import com.dcaex.spbc.dto.Transactions;
import com.dcaex.spbc.service.datasource.DataSourceService;
import com.dcaex.spbc.utils.NumberUtils;
import com.dcaex.spbc.web3j.TransactionOperate;

@Controller
@RequestMapping("/datasource/")
public class DataSourceController {

	@Autowired
	private DataSourceService dataSourceService;

	/**
	 * 跳转到区块链列表数据
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("selectDataIndex")
	public String selectDataIndex(Model model) throws IOException{
		
		return "index";
		
	}
	
	/**
	 * 展示块信息的列表
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("selectBlockList")
	public String selectBlockList(Model model) throws IOException{
		List<Block> blist = TransactionOperate.selectBlockList();
		model.addAttribute("blist", blist);
		return "left";
	}
	
	/**
	 * 展示交易信息列表
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("selectTransactionsList")
	public String selectTransactionsList(Model model) throws IOException{
		List<Transactions> tlist = TransactionOperate.selectTransactionList();
		model.addAttribute("tlist", tlist);
		return "right";
	}
	
	/**
	 * 根据条件查询
	 * @param searchValue
	 * @throws IOException 
	 */
	@RequestMapping("selectBySingle")
	public String selectBySingle(String searchValue,Model model) throws IOException{
		String str = searchValue.trim().substring(0,2);
		Block block = null;
		if (!str.equals("0x")) {
			if (NumberUtils.isNumber(searchValue)) {
				block = TransactionOperate.selectByBlockNumber(searchValue);
			}
			model.addAttribute("block",block);
			return "transaction/block";
		}else {
			if (searchValue.length()==66) {
				Transactions tran = TransactionOperate.selectByTxHash(searchValue);
				model.addAttribute("tran", tran);
				return "transaction/transaction";
			}else if (searchValue.length()==42) {
				List<Transactions> transList = TransactionOperate.selectByAddress(searchValue);
				if (transList.size()>0) {
					model.addAttribute("transList", transList);
					return "transaction/transList";
				}else{
					return "empty";
				}
			}
		}
		return "";
		
	}
	
	@RequestMapping("selectByBlockNumber")
	public String selectByBlockNumber(String searchValue,Model model) throws IOException{
		Block block = TransactionOperate.selectByBlockNumber(searchValue);
		model.addAttribute("block", block);
		return "transaction/block";
	}

	@RequestMapping("selectByTxHash")
	public String selectByTxHash(String searchValue,Model model) throws IOException{
		Transactions tran = TransactionOperate.selectByTxHash(searchValue);
		model.addAttribute("tran", tran);
		return "transaction/transaction";
	}
	
	@RequestMapping("selectByAddress")
	public String selectByAddress(String searchValue,Model model) throws IOException{
		List<Transactions> transList = TransactionOperate.selectByAddress(searchValue);
		model.addAttribute("transList", transList);
		model.addAttribute("searchValue", searchValue);
		return "transaction/transList";
	}
}
