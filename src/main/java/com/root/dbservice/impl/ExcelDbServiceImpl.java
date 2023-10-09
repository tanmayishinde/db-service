package com.root.dbservice.impl;

import com.root.commondependencies.exception.ValidationException;
import com.root.commondependencies.vo.DefaultResponseVO;
import com.root.commondependencies.vo.ParsedDataVO;
import com.root.dbservice.helper.ParsedDataHelper;
import com.root.dbservice.repo.ChildPartRepo;
import com.root.dbservice.repo.MonthlyPlanRepo;
import com.root.dbservice.repo.ProductChildPartRepo;
import com.root.dbservice.repo.ProductRepo;
import com.root.dbservice.service.ExcelDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcelDbServiceImpl implements ExcelDbService {

    private final ParsedDataHelper parsedDataHelper;

    private ProductRepo productRepo;
    private ChildPartRepo childPartRepo;
    private MonthlyPlanRepo monthlyPlanRepo;

    private ProductChildPartRepo productChildPartRepo;


    @Autowired
    public ExcelDbServiceImpl(ParsedDataHelper parsedDataHelper,
                              ProductRepo productRepo,
                              ChildPartRepo childPartRepo,
                              MonthlyPlanRepo monthlyPlanRepo,
                              ProductChildPartRepo productChildPartRepo
    ) {

        this.parsedDataHelper = parsedDataHelper;
        this.productRepo = productRepo;
        this.childPartRepo = childPartRepo;
        this.monthlyPlanRepo = monthlyPlanRepo;
        this.productChildPartRepo = productChildPartRepo;
    }

    @Override
    public DefaultResponseVO saveParsedData(ParsedDataVO parsedData) throws ValidationException {
        DefaultResponseVO defaultResponse = new DefaultResponseVO();
        defaultResponse.setStatusCode("200");
        defaultResponse.setMessage("SUCCESS");
        parsedDataHelper.saveProductsInDb(parsedData);
        return defaultResponse;
    }


}
