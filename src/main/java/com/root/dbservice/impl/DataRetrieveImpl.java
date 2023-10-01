package com.root.dbservice.impl;

import com.root.commondependencies.displayvo.ChildPartDisplayVO;
import com.root.commondependencies.displayvo.ChildPartQuantityVO;
import com.root.commondependencies.displayvo.ProductDisplayVO;
import com.root.commondependencies.vo.CreationDateVO;
import com.root.commondependencies.vo.MonthlyPlanEntityVO;
import com.root.commondependencies.vo.ProductChildPartRelationShipVO;
import com.root.dbservice.entities.ChildPartEntity;
import com.root.dbservice.entities.MonthlyPlanEntity;
import com.root.dbservice.entities.ProductChildPartEntity;
import com.root.dbservice.entities.ProductEntity;
import com.root.dbservice.repo.ChildPartRepo;
import com.root.dbservice.repo.MonthlyPlanRepo;
import com.root.dbservice.repo.ProductChildPartRepo;
import com.root.dbservice.repo.ProductRepo;
import com.root.dbservice.service.DataRetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataRetrieveImpl implements DataRetrieveService {
    private final ProductRepo productRepo;
    private final ChildPartRepo childPartRepo;
    private final ProductChildPartRepo productChildPartRepo;
    private final MonthlyPlanRepo monthlyPlanRepo;


    @Autowired
    public DataRetrieveImpl(ProductRepo productRepo,
                            ChildPartRepo childPartRepo,
                            ProductChildPartRepo productChildPartRepo,
                            MonthlyPlanRepo monthlyPlanRepo

    ) {

        this.productRepo = productRepo;
        this.childPartRepo = childPartRepo;
        this.productChildPartRepo = productChildPartRepo;
        this.monthlyPlanRepo = monthlyPlanRepo;
    }

    @Override
    public List<ProductDisplayVO> getProductList() {
        List<ProductDisplayVO> productVOList = new ArrayList<>();
        List<ProductEntity> productEntityList = productRepo.findAll();
        for (ProductEntity product : productEntityList) {
            ProductDisplayVO productVO = new ProductDisplayVO();
            productVO.setProductID(product.getProductId());
            productVO.setProductName(product.getProductName());
            productVO.setProductSeries(product.getProductSeries());
            productVO.setProductOpeningStock(product.getProductOpeningStock());
            productVOList.add(productVO);
        }
        return productVOList;
    }

    @Override
    public List<ChildPartDisplayVO> getChildPartList() {
        List<ChildPartDisplayVO> childPartVOList = new ArrayList<>();
        List<ChildPartEntity> childPartEntityList = childPartRepo.findAll();
        for (ChildPartEntity childPart : childPartEntityList) {
            ChildPartDisplayVO childPartVO = new ChildPartDisplayVO();
            childPartVO.setChildPartID(childPart.getChildPartId());
            childPartVO.setChildPartName(childPart.getChildPartName());
            childPartVO.setChildPartSeries(childPart.getChildPartSeries());
            childPartVO.setChildPartOpeningStock(childPart.getChildPartOpeningStock());
            childPartVOList.add(childPartVO);
        }
        return childPartVOList;
    }

    @Override
    public Map<Long, List<ChildPartQuantityVO>> getProductChildPartList() {
        List<ProductChildPartEntity> productChildPartEntityList = productChildPartRepo.findAll();

        List<ProductChildPartRelationShipVO> productChildPartVOList = new ArrayList<>();
        for (ProductChildPartEntity productChildPart : productChildPartEntityList) {
            ProductChildPartRelationShipVO productChildPartVO = new ProductChildPartRelationShipVO();
            productChildPartVO.setProductId(productChildPart.getProductId());
            productChildPartVO.setChildPartId(productChildPart.getChildPartId());
            productChildPartVO.setChildPartQuantity(productChildPart.getQuantity());
            productChildPartVOList.add(productChildPartVO);
        }
        Map<Long, List<ChildPartQuantityVO>> partQuantityVOMap = new HashMap<>();
        for (ProductChildPartRelationShipVO productChildPartVO : productChildPartVOList) {
            if (partQuantityVOMap.containsKey(productChildPartVO.getProductId())) {
                ChildPartQuantityVO childPartQuantityVO = new ChildPartQuantityVO();
                childPartQuantityVO.setChildPartId(productChildPartVO.getChildPartId());
                childPartQuantityVO.setChildPartQuantity(productChildPartVO.getChildPartQuantity());
                partQuantityVOMap.get(productChildPartVO.getProductId()).add(childPartQuantityVO);
            } else {
                List<ChildPartQuantityVO> childPartQuantityVOList = new ArrayList<>();
                ChildPartQuantityVO childPartQuantityVO = new ChildPartQuantityVO();
                childPartQuantityVO.setChildPartId(productChildPartVO.getChildPartId());
                childPartQuantityVO.setChildPartQuantity(productChildPartVO.getChildPartQuantity());
                childPartQuantityVOList.add(childPartQuantityVO);
                partQuantityVOMap.put(productChildPartVO.getProductId(), childPartQuantityVOList);
            }
        }

        return partQuantityVOMap;

//        Map<Long, List<ProductChildPartVO>> collMap =
//                productChildPartVOList.stream()
//                        .collect(Collectors.groupingBy(productChildPartEntity
//                                ->productChildPartEntity.getProductId()));
    }

    @Override
    public List<MonthlyPlanEntityVO> getMonthlyPlanVOList(CreationDateVO creationDateVO) {
        List<MonthlyPlanEntityVO> monthlyPlanEntityVOList = new ArrayList<>();

        List<MonthlyPlanEntity> monthlyPlanEntityList = monthlyPlanRepo.
                findBycreationDateBetween(creationDateVO.getStartDate(), creationDateVO.getEndDate());
        for (MonthlyPlanEntity monthlyPlanEntity : monthlyPlanEntityList) {
            MonthlyPlanEntityVO monthlyPlanEntityVO = new MonthlyPlanEntityVO();
            monthlyPlanEntityVO.setId(monthlyPlanEntity.getId());
            monthlyPlanEntityVO.setProductId(monthlyPlanEntity.getProductId());
            monthlyPlanEntityVO.setWeek1(monthlyPlanEntity.getWeek1());
            monthlyPlanEntityVO.setWeek2(monthlyPlanEntity.getWeek2());
            monthlyPlanEntityVO.setWeek3(monthlyPlanEntity.getWeek3());
            monthlyPlanEntityVO.setWeek4(monthlyPlanEntity.getWeek4());
            monthlyPlanEntityVO.setCreationDate(monthlyPlanEntity.getCreationDate());
            monthlyPlanEntityVO.setUpdationDate(monthlyPlanEntity.getUpdationDate());
            monthlyPlanEntityVOList.add(monthlyPlanEntityVO);
        }


//        if (creationDateVO.getStartDate().equals(monthlyPlanEntityVO.getCreationDate())
//                && creationDateVO.getEndDate().equals(monthlyPlanEntityVO.getCreationDate())) {
//        monthlyPlanResponseVO.setStartDate(monthlyPlanEntityVO.getCreationDate());
//        monthlyPlanResponseVO.setEndDate(monthlyPlanEntityVO.getCreationDate());
//        monthlyPlanResponseVO.setMonthlyDisplayVOList(
//                getMonthlyPlanVODisplayList(creationDateVO));
        return monthlyPlanEntityVOList;
    }

    @Override
    public List<ProductChildPartRelationShipVO> getproductChildPartRelationshipList() {
        List<ProductChildPartRelationShipVO> productChildPartVOList=new ArrayList<>();
        List<ProductChildPartEntity> productChildPartEntityList = productChildPartRepo.findAll();
        for (ProductChildPartEntity productChildPartEntity : productChildPartEntityList) {
            ProductChildPartRelationShipVO productChildPartVO = new ProductChildPartRelationShipVO();
            productChildPartVO.setId(productChildPartEntity.getId());
            productChildPartVO.setProductId(productChildPartEntity.getProductId());
            productChildPartVO.setChildPartId(productChildPartEntity.getChildPartId());
            productChildPartVO.setChildPartQuantity(productChildPartEntity.getQuantity());
            productChildPartVOList.add(productChildPartVO);

        }
        return productChildPartVOList;
    }
}
