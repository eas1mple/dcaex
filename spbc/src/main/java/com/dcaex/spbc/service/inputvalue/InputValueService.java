package com.dcaex.spbc.service.inputvalue;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dcaex.spbc.dto.Author;


@Service
public interface InputValueService {

	boolean insertInputValue(List<Author> tranInfor);

	void selectInputValue(List<Author> tranInfor);




}
