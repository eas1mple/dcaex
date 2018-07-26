package com.dcaex.spbc.service.elasearch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcaex.spbc.dao.elasearch.ElasearchMapper;
import com.dcaex.spbc.dto.Author;

@Service
public class ElasearchServiceImpl implements ElasearchService{
	
	@Autowired
	private ElasearchMapper elasearchMapper;

//	@Override
//	public Page<Author> queryInformation(Page<Author> page, String information) {
//      QueryStringQueryBuilder qsqb = new QueryStringQueryBuilder(information);
//      Iterable<Author> search = elasearchMapper.search(qsqb);
//      Iterator<Author> iterator = search.iterator();
//      List<Author> list = new ArrayList<Author>();
//      while (iterator.hasNext()){
//          list.add(iterator.next());
//      }
//		return page;
//	}
	

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<Author> queryInformation(String information) {
		QueryStringQueryBuilder qsqb = new QueryStringQueryBuilder(information);
		Iterable search = elasearchMapper.search(qsqb);
		Iterator iterator = search.iterator();
		List<Author> list = new ArrayList<Author>();
		while (iterator.hasNext()){
			list.add((Author) iterator.next());
		}
		return list;
	}

	@Override
	public Author selectDetailBySn(String certifi) {
		QueryStringQueryBuilder qsqb = new QueryStringQueryBuilder(certifi);
		Iterable<Author> search = elasearchMapper.search(qsqb);
		Iterator<Author> iterator = search.iterator();
		while (iterator.hasNext()){
			return iterator.next();
		}
		return null;
	}



}
