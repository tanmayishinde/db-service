package com.root.dbservice.service;

import com.root.commondependencies.displayvo.MonthlyPlanResponseVO;
import com.root.commondependencies.displayvo.ProductChildPartDisplayVO;
import com.root.commondependencies.displayvo.ProductDisplayVO;
import com.root.commondependencies.exception.ValidationException;
import com.root.commondependencies.vo.*;

import java.util.List;

public interface ExcelDbService {
    DefaultResponseVO saveParsedData(ParsedDataVO parsedData) throws ValidationException;

    MonthlyPlanResponseVO getMonthlyPlanList(CreationDateVO creationDateVO) ;
    List<ProductDisplayVO> getProductList();

    List<ProductChildPartDisplayVO> getProductChildPartList();

   //List<ChildPartWeeklyPlan> getchildPartWeeklyPlan();
   // ChildPartWeeklyPlan getchildPartWeeklyPlan(Long childpartID);
}
