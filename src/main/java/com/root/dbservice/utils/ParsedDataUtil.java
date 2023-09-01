package com.root.dbservice.utils;

import com.root.commondependencies.vo.ChildPartVO;
import com.root.commondependencies.vo.MonthlyPlanVO;
import com.root.commondependencies.vo.ProductVO;
import com.root.dbservice.entities.ChildPartEntity;
import com.root.dbservice.entities.MonthlyPlanEntity;
import com.root.dbservice.entities.ProductChildPartEntity;
import com.root.dbservice.entities.ProductEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ParsedDataUtil {

    public static List<ProductEntity> getProductEntityList(List<ProductVO> productList){
        List<ProductEntity> productEntities = new ArrayList<>();
        for(ProductVO product : productList){
            ProductEntity productEntity = new ProductEntity();
            productEntity.setProductSeries(product.getProductSeries());
            productEntity.setProductName(product.getProductName());
            productEntity.setProductOpeningStock(product.getProductOpeningStock());
            productEntities.add(productEntity);
        }
        return productEntities;
    }

    public static List<ChildPartEntity> getChildPartEntityList(List<ChildPartVO> childPartList) {
        List<ChildPartEntity> childPartEntities = new ArrayList<>();
        for(ChildPartVO childPart : childPartList){
            ChildPartEntity childPartEntity = new ChildPartEntity();
            childPartEntity.setChildPartName(childPart.getChildPartName());
            childPartEntity.setChildPartSeries(childPart.getChildPartSeries());
            childPartEntity.setChildPartOpeningStock(childPart.getChildPartOpeningStock());
            childPartEntities.add(childPartEntity);
        }
        return childPartEntities;
    }

    public static List<ProductChildPartEntity> getProductChildPartRelationshipList(
            List<ProductVO> productList, List<ProductEntity> productEntities, List<ChildPartEntity> childPartEntities) {
        List<ProductChildPartEntity> productChildPartList = new ArrayList<>();

        Map<String,Long> productMap = new HashMap<>();
        Map<String,Long> childPartMap = new HashMap<>();

        for (ProductEntity productEntity : productEntities) {
            productMap.put(productEntity.getProductSeries(), productEntity.getProductId());
        }

        for (ChildPartEntity childPartEntity : childPartEntities) {
            childPartMap.put(childPartEntity.getChildPartSeries(), childPartEntity.getChildPartId());
        }

        for(ProductVO productVO : productList){
            for(Map.Entry<String,Integer> relation : productVO.getChildParts().entrySet()){
                ProductChildPartEntity productChildPart = new ProductChildPartEntity();
                productChildPart.setProductId(productMap.get(productVO.getProductSeries()));
                productChildPart.setChildPartId(childPartMap.get(relation.getKey()));
                productChildPart.setQuantity(relation.getValue());
                productChildPartList.add(productChildPart);
            }
        }
        return  productChildPartList;
    }

    public static List<MonthlyPlanEntity> getProductMonthlyPlanList(
            List<ProductEntity> productEntities, List<MonthlyPlanVO> monthlyPlanList) {

        List<MonthlyPlanEntity> monthlyPlanEntityList = new ArrayList<>();
        Map<String,Long> productMap = new HashMap<>();
        for (ProductEntity productEntity : productEntities) {
            productMap.put(productEntity.getProductSeries(), productEntity.getProductId());
        }

        for(MonthlyPlanVO monthlyPlanVO : monthlyPlanList){
            MonthlyPlanEntity monthlyPlanEntity = new MonthlyPlanEntity();
            monthlyPlanEntity.setProductId(productMap.get(monthlyPlanVO.getProductSeries()));
            monthlyPlanEntity.setWeek1(monthlyPlanVO.getWeek1());
            monthlyPlanEntity.setWeek2(monthlyPlanVO.getWeek2());
            monthlyPlanEntity.setWeek3(monthlyPlanVO.getWeek3());
            monthlyPlanEntity.setWeek4(monthlyPlanVO.getWeek4());
            monthlyPlanEntityList.add(monthlyPlanEntity);
        }
        return monthlyPlanEntityList;
    }
}
