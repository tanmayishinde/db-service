package com.root.dbservice.helper;

import com.root.commondependencies.exception.ValidationException;
import com.root.commondependencies.vo.ChildPartVO;
import com.root.commondependencies.vo.MonthlyPlanVO;
import com.root.commondependencies.vo.ParsedDataVO;
import com.root.commondependencies.vo.ProductVO;
import com.root.dbservice.entities.ChildPartEntity;
import com.root.dbservice.entities.MonthlyPlanEntity;
import com.root.dbservice.entities.ProductChildPartEntity;
import com.root.dbservice.entities.ProductEntity;
import com.root.dbservice.repo.ChildPartRepo;
import com.root.dbservice.repo.MonthlyPlanRepo;
import com.root.dbservice.repo.ProductChildPartRepo;
import com.root.dbservice.repo.ProductRepo;
import com.root.dbservice.service.AsyncService;
import com.root.dbservice.utils.ParsedDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
                            AsyncService asyncService){
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
}
