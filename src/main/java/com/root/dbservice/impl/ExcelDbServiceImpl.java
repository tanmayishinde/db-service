package com.root.dbservice.impl;

import com.root.commondependencies.vo.DefaultResponseVO;
import com.root.commondependencies.vo.ParsedDataVO;
import com.root.dbservice.helper.ParsedDataHelper;
import com.root.dbservice.service.ExcelDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExcelDbServiceImpl implements ExcelDbService {

    private final ParsedDataHelper parsedDataHelper;

    @Autowired
    public ExcelDbServiceImpl(ParsedDataHelper parsedDataHelper){
        this.parsedDataHelper = parsedDataHelper;
    }
    @Override
    public DefaultResponseVO saveParsedData(ParsedDataVO parsedData) {
        DefaultResponseVO defaultResponse = new DefaultResponseVO();
        defaultResponse.setStatusCode("200");
        defaultResponse.setMessage("SUCCESS");
        parsedDataHelper.saveProductsInDb(parsedData);
        return defaultResponse;
    }
}
