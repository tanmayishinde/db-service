package com.root.dbservice.controllers;


import com.root.commondependencies.exception.ValidationException;
import com.root.commondependencies.vo.DefaultResponseVO;
import com.root.commondependencies.vo.ParsedDataVO;
import com.root.dbservice.service.ExcelDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
