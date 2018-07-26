package com.dcaex.spbc.dao.elasearch;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.dcaex.spbc.dto.Author;

@Repository
public interface ElasearchMapper extends ElasticsearchRepository<Author,Long>{


}
