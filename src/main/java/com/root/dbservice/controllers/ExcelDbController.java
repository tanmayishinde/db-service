package com.root.dbservice.controllers;


import com.root.commondependencies.displayvo.MonthlyPlanResponseVO;
import com.root.commondependencies.displayvo.ProductChildPartDisplayVO;
import com.root.commondependencies.displayvo.ProductDisplayVO;
import com.root.commondependencies.exception.ValidationException;
import com.root.commondependencies.vo.*;
import com.root.dbservice.service.ExcelDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelDbController {

    @Autowired
    private ExcelDbService excelDbService;
    @PostMapping("/import-data")
    public DefaultResponseVO saveExcelData(@RequestBody ParsedDataVO parsedData) throws ValidationException {
        return excelDbService.saveParsedData(parsedData);
    }

}
