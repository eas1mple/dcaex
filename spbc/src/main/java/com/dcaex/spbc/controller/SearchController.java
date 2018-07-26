package com.dcaex.spbc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dcaex.spbc.common.Page;
import com.dcaex.spbc.dto.Author;
import com.dcaex.spbc.service.elasearch.ElasearchService;

@Controller
@RequestMapping("/dcaex/author/")
public class SearchController {
	
	@Autowired
	private ElasearchService elasearchService;
	
	/**
	 * 搜索版权信息首页
	 * @return
	 */
	@RequestMapping("toSearch")
	public String toSearch(){
		return "copyright/infor";
	}
	
	@RequestMapping("queryFirstInformation")
	public  String queryFirstInformation(String information,HttpServletRequest request){
		List<Author> inforList = elasearchService.queryInformation(information);
		Page<Author> page = new Page<Author>(1,10,inforList);
		request.setAttribute("page", page);
		return "copyright/copyright";
	}
	
	/**
	 * 实时搜索查询版权信息
	 * @param information
	 * @param request
	 * @return
	 */
    @RequestMapping("queryInformation")
    public String queryInformation(String information,String pageNum,HttpServletRequest request){
        List<Author> inforList = elasearchService.queryInformation(information);
        Page<Author> page = new Page<Author>(Integer.valueOf(pageNum), 10, inforList);
        request.setAttribute("page", page);
        return "copyright/copyright";
    }
    
    @RequestMapping("selectDetailBySn")
    public String selectDetailBySn(String certifi,Model model){
    	Author author= elasearchService.selectDetailBySn(certifi);
    	model.addAttribute("author", author);
    	return "copyright/crinfor";
    }
    	
}
