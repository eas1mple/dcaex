package com.dcaex.spbc.controller;

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
	
//    @RequestMapping("queryInformation")
//    @ResponseBody
//    public String queryInformation(String information,HttpServletRequest request){
//        List<Author> inforList = elasearchService.queryInformation(information);
//        request.getSession().setAttribute("inforList",inforList);
//        return "ok";
//    }
	
	/**
	 * 实时搜索查询版权信息
	 * @param information
	 * @param request
	 * @return
	 */
    @RequestMapping("queryInformation")
    public String queryInformation(String information,String pageNo,HttpServletRequest request){
    	Page<Author> page = new Page<Author>();
    	if (pageNo!=null) {
    		page.setPageNo(Integer.valueOf(pageNo));
		}
    	page.setUrl(request.getServletPath());
        page = elasearchService.queryInformation(page,information);
        request.setAttribute("page",page);
        
        
//        List<Author> inforList = elasearchService.queryInformation(information);
//        request.setAttribute("inforList",inforList);
        return "copyright/copyright";
        
    }
    
    
    @RequestMapping("toSearch")
    public String toSearch(){
    	
    	return "copyright/infor";
    }
    
    @RequestMapping("toCrinfor")
    public String toCrinfor(){
    	
    	return "copyright/crinfor";
    }
    
    @RequestMapping("selectDetailBySn")
    public String selectDetailBySn(String certifi,Model model){
    	
    	Author author= elasearchService.selectDetailBySn(certifi);
    	
    	model.addAttribute("author", author);
    	
    	return "copyright/crinfor";
    }
    	
}
