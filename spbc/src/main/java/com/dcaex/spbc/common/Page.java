package com.dcaex.spbc.common;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {

		/**
		 * 序列化id
		 */
		private static final long serialVersionUID = 7569566861340703188L;
		private int pageSize;//每页显示多少条记录
		private int currentPage;//当前第几页数据
		private int totalRecord;//一共多少条记录
		private List<T> dataList;//要显示的数据
		private int totalPage;//总页数
		
		public Page() {
			super();
		}
		public Page(int pageSize, int currentPage, int totalRecord,
				int totalPage,List<T> dataList) {
			super();
			this.pageSize = pageSize;
			this.currentPage = currentPage;
			this.totalRecord = totalRecord;
			this.totalPage = totalPage;
			this.dataList = dataList;
		}
		
		public Page(int pageNum,int pageSize,List<T> sourceList){
			if(sourceList.size() ==0 ||sourceList == null){
				return;
			}
			//总记录条数
			this.totalRecord = sourceList.size();
			//每页显示多少条记录
			this.pageSize = pageSize;
			//获取总页数
			this.totalPage = this.totalRecord /this.pageSize;
			if(this.totalRecord % this.pageSize != 0){
				this.totalPage = this.totalPage +1;
			}
			//当前第几页数据
			if(this.totalPage < pageNum){
				this.currentPage = this.totalPage;
			}else{
				this.currentPage = pageNum;
			}
			
			//起始索引
			int fromIndex = this.pageSize*(this.currentPage-1);
			//结束索引
			int toIndex;
			if(this.pageSize * this.currentPage >this.totalRecord){
				toIndex = this.totalRecord;
			}else{
				toIndex = this.pageSize * this.currentPage;
			}
			this.dataList = sourceList.subList(fromIndex, toIndex);
		}
		
		public int getPageSize() {
			return pageSize;
		}
		public int getCurrentPage() {
			return currentPage;
		}
		public int getTotalRecord() {
			return totalRecord;
		}
		public List<T> getDataList() {
			return dataList;
		}
		public int getTotalPage() {
			return totalPage;
		}
		
}
