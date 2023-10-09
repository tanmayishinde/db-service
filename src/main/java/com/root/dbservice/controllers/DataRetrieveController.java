package com.root.dbservice.controllers;

import com.root.commondependencies.displayvo.ChildPartDisplayVO;
import com.root.commondependencies.displayvo.ProductDisplayVO;
import com.root.commondependencies.vo.CreationDateVO;
import com.root.commondependencies.vo.MonthlyPlanEntityVO;
import com.root.commondependencies.vo.ProductChildPartRelationShipVO;
import com.root.dbservice.service.DataRetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dataRetrieve")
public class DataRetrieveController {

    private DataRetrieveService dataRetrieveService;
    @Autowired
    public DataRetrieveController(DataRetrieveService dataRetrieveService){
        this.dataRetrieveService=dataRetrieveService;
    }

    @GetMapping("/product-list")
    public List<ProductDisplayVO> getProductList(){
        return  dataRetrieveService.getProductList();
    }
    @GetMapping("/childPart-list")
    public List<ChildPartDisplayVO> getChildPartList(){
        return  dataRetrieveService.getChildPartList();
    }

    @GetMapping("/productChildPartRelationship-list")
    public List<ProductChildPartRelationShipVO> getproductChildPartRelationshipList(){
        return dataRetrieveService.getproductChildPartRelationshipList();
    }

    @PostMapping("/getMonthlyPlanList")
    public List<MonthlyPlanEntityVO> getMonthlyPlanVOList(@RequestBody CreationDateVO creationDateVO){
        return  dataRetrieveService.getMonthlyPlanVOList(creationDateVO);
    }


}
