package com.dcaex.spbc.common;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page<T> {
	private int pageNo = 1;//页码，默认是第一页
    private int pageSize =10;//每页显示的记录数，默认是10
    private int totalRecord;//总记录数
    private int totalPage;//总页数
    private List<T> results;//对应的当前页记录
    
    
    private int prePageNo;//上一页页码
    
    private int nextPageNo;//下一页码
    
    
    
    
    //
    public int getPrePageNo() {
    	
    	if(pageNo==1){
    		this.prePageNo=1;
    	}else{
    		this.prePageNo=pageNo-1;
    	}
    	
		return prePageNo;
	}
	public void setPrePageNo(int prePageNo) {
		this.prePageNo = prePageNo;
	}
	public int getNextPageNo() {
		
		if(pageNo==this.getTotalPage()){
    		this.nextPageNo=this.getTotalPage();
    	}else{
    		this.nextPageNo=pageNo+1;
    	}
		
		return nextPageNo;
	}
	public void setNextPageNo(int nextPageNo) {
		this.nextPageNo = nextPageNo;
	}
	private String url;//分页的路径
    
    
    public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	private Map<String, Object> params = new HashMap<String, Object>();//其他的参数我们把它分装成一个Map对象
	
    public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getTotalPage() {
		
		if(totalRecord%pageSize==0){
			this.totalPage=totalRecord/pageSize;
		}else{
			this.totalPage=(totalRecord/pageSize)+1;
		}

		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
    
}
