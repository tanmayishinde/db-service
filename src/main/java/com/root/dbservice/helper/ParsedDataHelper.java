package com.root.dbservice.helper;

import com.root.commondependencies.displayvo.ProductDisplayVO;
import com.root.commondependencies.exception.ValidationException;
import com.root.commondependencies.vo.*;
import com.root.dbservice.entities.ChildPartEntity;
import com.root.dbservice.entities.MonthlyPlanEntity;
import com.root.dbservice.entities.ProductChildPartEntity;
import com.root.dbservice.entities.ProductEntity;
import com.root.dbservice.repo.ChildPartRepo;
import com.root.dbservice.repo.MonthlyPlanRepo;
import com.root.dbservice.repo.ProductChildPartRepo;
import com.root.dbservice.repo.ProductRepo;
import com.root.dbservice.service.AsyncService;
import com.root.commondependencies.vo.ProductChildPartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ParsedDataHelper {

    private final ProductRepo productRepo;
    private final ChildPartRepo childPartRepo;
    private final ProductChildPartRepo productChildPartRepo;
    private final MonthlyPlanRepo monthlyPlanRepo;

    private final AsyncService asyncService;

    @Autowired
    public ParsedDataHelper(ProductRepo productRepo,
                            ChildPartRepo childPartRepo,
                            ProductChildPartRepo productChildPartRepo,
                            MonthlyPlanRepo monthlyPlanRepo,
                            AsyncService asyncService) {
        this.productRepo = productRepo;
        this.childPartRepo = childPartRepo;
        this.productChildPartRepo = productChildPartRepo;
        this.monthlyPlanRepo = monthlyPlanRepo;
        this.asyncService = asyncService;
    }

    public void saveProductsInDb(ParsedDataVO parsedDataVO) throws ValidationException {
        try {
            List<ProductVO> productList = parsedDataVO.getProductsList();
            List<ChildPartVO> childPartList = parsedDataVO.getChildPartsList();
            List<MonthlyPlanVO> monthlyPlanList = parsedDataVO.getMonthlyPlanList();

            if (!CollectionUtils.isEmpty(productList) && !CollectionUtils.isEmpty(childPartList)
                    && !CollectionUtils.isEmpty(monthlyPlanList)) {

                CompletableFuture<List<ProductEntity>> productEntitiesListCf
                        = asyncService.getProductEntityList(productList);

                CompletableFuture<List<ChildPartEntity>> childPartEntitiesListCf
                        = asyncService.getChildPartEntityList(childPartList);

                CompletableFuture.allOf(productEntitiesListCf, childPartEntitiesListCf).join();
                List<ProductEntity> productEntitiesList = productEntitiesListCf.get();
                List<ChildPartEntity> childPartEntitiesList = childPartEntitiesListCf.get();

                CompletableFuture<List<ProductChildPartEntity>> productChildPartListCf =
                        asyncService.getProductChildPartRelationshipList(productList, productEntitiesList, childPartEntitiesList);

                CompletableFuture<List<MonthlyPlanEntity>> monthlyPlanEntityListCf =
                        asyncService.getProductMonthlyPlanList(productEntitiesList, monthlyPlanList);

                CompletableFuture.allOf(productChildPartListCf, monthlyPlanEntityListCf).join();
            }
        } catch (Exception ex) {
            throw new ValidationException.Builder().errorMessage(ex.getMessage()).build();
        }

    }

    public List<ProductDisplayVO> getProductVOList() {
        List<ProductDisplayVO> productVOList = new ArrayList<>();
        ProductDisplayVO productVO = new ProductDisplayVO();

        List<ProductEntity> productEntityList = productRepo.findAll();
        for (ProductEntity product : productEntityList) {
            productVO.setProductName(product.getProductName());
            productVO.setProductSeries(product.getProductSeries());
            productVO.setProductOpeningStock(product.getProductOpeningStock());
            productVOList.add(productVO);
        }
        return productVOList;
    }


    //    public List<ChildPartVO> getChildV0List(List<ProductChildPartVO> getProductChildPartVOList) {
//        List<ChildPartsCoverageVO> childPartVOList = new ArrayList<>();
//        ChildPartsCoverageVO childPartVO = new ChildPartsCoverageVO();
//        Pro
//
//        List<ChildPartEntity> childPartEntityList = childPartRepo.findAll();
//        for (ChildPartEntity childPart : childPartEntityList) {
//
//            childPartVO.setChildPartName(childPart.getChildPartName());
//            childPartVO.setChildPartSeries(childPart.getChildPartSeries());
//            childPartVO.setQuantity(getProductChildPartVOList.get();
//            childPartVO.setChildPWeek1(ch)
//                    childPart.getChildPartOpeningStock());
//            childPartVO.setChildPWeek1(childPart.ge);
//            childPartVOList.add(childPartVO);
//        }
//        return childPartVOList;
//    }
    public List<ProductChildPartVO> getProductChildPartVOList() {
        List<ProductChildPartVO> productChildPartVOList = new ArrayList<>();
        ProductChildPartVO productChildPartVOObj = new ProductChildPartVO();

        List<ProductChildPartEntity> productChildPartEntityList = productChildPartRepo.findAll();
        for (ProductChildPartEntity pCEnity : productChildPartEntityList) {
            productChildPartVOObj.setChildPartId(pCEnity.getChildPartId());
            productChildPartVOObj.setProductId(pCEnity.getProductId());
            productChildPartVOObj.setChildPartQuantity(pCEnity.getQuantity());
            productChildPartVOList.add(productChildPartVOObj);
        }
        return productChildPartVOList;
    }



}
