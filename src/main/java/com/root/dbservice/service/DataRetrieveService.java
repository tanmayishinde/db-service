package com.root.dbservice.service;

import com.root.commondependencies.displayvo.ChildPartDisplayVO;
import com.root.commondependencies.displayvo.ChildPartQuantityVO;
import com.root.commondependencies.displayvo.ProductDisplayVO;
import com.root.commondependencies.vo.CreationDateVO;
import com.root.commondependencies.vo.MonthlyPlanEntityVO;
import com.root.commondependencies.vo.ProductChildPartRelationShipVO;

import java.util.List;
import java.util.Map;

public interface DataRetrieveService {
    List<ProductDisplayVO> getProductList();
    List<ChildPartDisplayVO> getChildPartList();
    List<MonthlyPlanEntityVO> getMonthlyPlanVOList(CreationDateVO creationDateVO);

    List<ProductChildPartRelationShipVO> getproductChildPartRelationshipList();
}
