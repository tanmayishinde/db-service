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

    @GetMapping("/getMonthlyPlanList")
    public MonthlyPlanResponseVO getMonthlyPlanList(@RequestBody CreationDateVO creationDateVO){
        return excelDbService.getMonthlyPlanList(creationDateVO);
    }
    @GetMapping("/product-list")
    public List<ProductDisplayVO> getProductList(){
        return  excelDbService.getProductList();
    }
    @GetMapping("/productChildPart-list")
    public List<ProductChildPartDisplayVO> getProductChildPartList(){
        return  excelDbService.getProductChildPartList();
    }
//    @GetMapping("/childPartWeeklyPlan-list")
//    public List<ChildPartWeeklyPlan> childPartWeeklyPlan(){
//        return  excelDbService.getchildPartWeeklyPlan();
//    }

}
