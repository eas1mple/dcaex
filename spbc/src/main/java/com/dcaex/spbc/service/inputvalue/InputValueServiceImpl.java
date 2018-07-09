package com.dcaex.spbc.service.inputvalue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcaex.spbc.dao.elasearch.ElasearchMapper;
import com.dcaex.spbc.dao.inputvalue.InputValueMapper;
import com.dcaex.spbc.dto.Author;
import com.dcaex.spbc.utils.UUID;

@Service
public class InputValueServiceImpl implements InputValueService {
	
	@Autowired
	private InputValueMapper dataSourceMapper;
	@Autowired
	private ElasearchMapper elasearchMapper;

	@Override
	public boolean insertInputValue(List<Author> tranInfor) {
        boolean b = false;

        for (Author au: tranInfor) {
            au.setId(UUID.next());
            au.getData().getStatus().setStatusId(UUID.next());
            au.getData().getCertificateInfo().setCertificateInfoId(UUID.next());
            au.getData().getFeatures().setFeaturesId(UUID.next());
            au.getData().getWorkInfo().setWorkInfoId(UUID.next());
        	if (au!=null) {
				
        		elasearchMapper.save(au);
			}
        }

//        int i = dataSourceMapper.insertInputValue(tranInfor);
//        int j = dataSourceMapper.insertCertificateInfo(tranInfor);
//        int k = dataSourceMapper.insertFeaturesInfo(tranInfor);
//        int m = dataSourceMapper.insertWorkInfo(tranInfor);
//        int n = dataSourceMapper.insertStatus(tranInfor);
//
//        if (i>0 && j>0 && k>0 && m>0 && n>0){
//            b = true;
//        }

        return b;
	}
//	
//	@Override
//	public void selectInputValue(List<Author> tranInfor) {
//
//        for (Author au: tranInfor) {
//            au.setId(UUID.next());
//            au.getData().getStatus().setStatusId(UUID.next());
//            au.getData().getCertificateInfo().setCertificateInfoId(UUID.next());
//            au.getData().getFeatures().setFeaturesId(UUID.next());
//            au.getData().getWorkInfo().setWorkInfoId(UUID.next());
//            elasearchMapper.save(au);
//        }
//
//        dataSourceMapper.insertInputValue(tranInfor);
//        dataSourceMapper.insertCertificateInfo(tranInfor);
//        dataSourceMapper.insertFeaturesInfo(tranInfor);
//        dataSourceMapper.insertWorkInfo(tranInfor);
//        dataSourceMapper.insertStatus(tranInfor);
//
//	}

	@Override
	public void selectInputValue(List<Author> tranInfor) {
		// TODO Auto-generated method stub
		
	}

}
