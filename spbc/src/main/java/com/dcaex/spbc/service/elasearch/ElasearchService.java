package com.dcaex.spbc.service.elasearch;


import java.util.List;

import com.dcaex.spbc.dto.Author;

public interface ElasearchService {

	List<Author> queryInformation(String information);

//	Page<Author> queryInformation(Page<Author> page,String information);
	
	Author selectDetailBySn(String certifi);



}
