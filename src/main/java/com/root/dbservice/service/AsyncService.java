package com.root.dbservice.service;

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
import com.root.dbservice.utils.ParsedDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class AsyncService {

    private final ProductRepo productRepo;
    private final ChildPartRepo childPartRepo;
    private final ProductChildPartRepo productChildPartRepo;
    private final MonthlyPlanRepo monthlyPlanRepo;

    @Autowired
    public AsyncService(ProductRepo productRepo,
                        ChildPartRepo childPartRepo,
                        ProductChildPartRepo productChildPartRepo,
                        MonthlyPlanRepo monthlyPlanRepo){
        this.productRepo = productRepo;
        this.childPartRepo = childPartRepo;
        this.productChildPartRepo = productChildPartRepo;
        this.monthlyPlanRepo = monthlyPlanRepo;

    }

    @Async
    public CompletableFuture<List<ProductEntity>> getProductEntityList(List<ProductVO> productList) {
        List<ProductEntity> productEntityList = ParsedDataUtil.getProductEntityList(productList);
        productRepo.saveAll(productEntityList);
        return CompletableFuture.completedFuture(productEntityList);
    }

    @Async
    public CompletableFuture<List<ChildPartEntity>> getChildPartEntityList(List<ChildPartVO> childPartList) {
        List<ChildPartEntity> childPartEntityList = ParsedDataUtil.getChildPartEntityList(childPartList);
        childPartRepo.saveAll(childPartEntityList);
        return CompletableFuture.completedFuture(childPartEntityList);
    }

    @Async
    public CompletableFuture<List<ProductChildPartEntity>> getProductChildPartRelationshipList(
            List<ProductVO> productList, List<ProductEntity> productEntities, List<ChildPartEntity> childPartEntities) {
        List<ProductChildPartEntity> productChildPartEntityList = ParsedDataUtil.
                getProductChildPartRelationshipList(productList,productEntities,childPartEntities);
        productChildPartRepo.saveAll(productChildPartEntityList);
        return CompletableFuture.completedFuture(productChildPartEntityList);
    }

    @Async
    public CompletableFuture<List<MonthlyPlanEntity>> getProductMonthlyPlanList(
            List<ProductEntity> productEntities, List<MonthlyPlanVO> monthlyPlanList) {
        List<MonthlyPlanEntity> monthlyPlanEntityList =
                ParsedDataUtil.getProductMonthlyPlanList(productEntities,monthlyPlanList);
        monthlyPlanRepo.saveAll(monthlyPlanEntityList);
        return CompletableFuture.completedFuture(monthlyPlanEntityList);
    }
}
