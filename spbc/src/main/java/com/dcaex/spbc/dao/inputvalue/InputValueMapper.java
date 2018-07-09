package com.dcaex.spbc.dao.inputvalue;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dcaex.spbc.dto.Author;


@Repository
public interface InputValueMapper {

	int insertInputValue(List<Author> tranInfor);

	int insertCertificateInfo(List<Author> tranInfor);

	int insertFeaturesInfo(List<Author> tranInfor);

	int insertWorkInfo(List<Author> tranInfor);

	int insertStatus(List<Author> tranInfor);

	
	
}
