package com.root.dbservice.impl;

import com.root.commondependencies.displayvo.*;
import com.root.commondependencies.exception.ValidationException;
import com.root.commondependencies.vo.*;
import com.root.dbservice.entities.ChildPartEntity;
import com.root.dbservice.entities.MonthlyPlanEntity;
import com.root.dbservice.entities.ProductChildPartEntity;
import com.root.dbservice.entities.ProductEntity;
import com.root.dbservice.helper.ParsedDataHelper;
import com.root.dbservice.repo.ChildPartRepo;
import com.root.dbservice.repo.MonthlyPlanRepo;
import com.root.dbservice.repo.ProductChildPartRepo;
import com.root.dbservice.repo.ProductRepo;
import com.root.dbservice.service.ExcelDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExcelDbServiceImpl implements ExcelDbService {

    private final ParsedDataHelper parsedDataHelper;

    private ProductRepo productRepo;
    private ChildPartRepo childPartRepo;
    private MonthlyPlanRepo monthlyPlanRepo;

    private ProductChildPartRepo productChildPartRepo;


    @Autowired
    public ExcelDbServiceImpl(ParsedDataHelper parsedDataHelper,
                              ProductRepo productRepo,
                              ChildPartRepo childPartRepo,
                              MonthlyPlanRepo monthlyPlanRepo,
                              ProductChildPartRepo productChildPartRepo
    ) {

        this.parsedDataHelper = parsedDataHelper;
        this.productRepo = productRepo;
        this.childPartRepo = childPartRepo;
        this.monthlyPlanRepo = monthlyPlanRepo;
        this.productChildPartRepo = productChildPartRepo;
    }

    @Override
    public DefaultResponseVO saveParsedData(ParsedDataVO parsedData) throws ValidationException {
        DefaultResponseVO defaultResponse = new DefaultResponseVO();
        defaultResponse.setStatusCode("200");
        defaultResponse.setMessage("SUCCESS");
        parsedDataHelper.saveProductsInDb(parsedData);
        return defaultResponse;
    }


    @Override
    public List<ProductDisplayVO> getProductList() {
        List<ProductDisplayVO> productVOList = new ArrayList<>();
        List<ProductEntity> productEntityList = productRepo.findAll();
        for (ProductEntity product : productEntityList) {
            ProductDisplayVO productVO = new ProductDisplayVO();
            productVO.setProductName(product.getProductName());
            productVO.setProductSeries(product.getProductSeries());
            productVO.setProductOpeningStock(product.getProductOpeningStock());
            productVOList.add(productVO);
        }
        return productVOList;
    }

    public List<ChildPartVO> getChildPartList() {
        List<ChildPartVO> childPartVOList = new ArrayList<>();
        List<ChildPartEntity> childPartEntityList = childPartRepo.findAll();
        for (ChildPartEntity childPart : childPartEntityList) {
            ChildPartVO childPartVO = new ChildPartVO();
            childPartVO.setChildPartName(childPart.getChildPartName());
            childPartVO.setChildPartSeries(childPart.getChildPartSeries());
            childPartVO.setChildPartOpeningStock(childPart.getChildPartOpeningStock());
            childPartVOList.add(childPartVO);
        }
        return childPartVOList;
    }

    public List<ProductChildPartDisplayVO> getProductChildPartList() {
        List<ProductChildPartDisplayVO> productChildPartDisplayVOList = new ArrayList<>();
        List<ProductEntity> productEntityList = productRepo.findAll();
        for (ProductEntity product : productEntityList) {
            ProductChildPartDisplayVO productChildPartDisplayVO = new ProductChildPartDisplayVO();
            List<ChildPartVO> childPartVOList = new ArrayList<>();


            productChildPartDisplayVO.setProductName(product.getProductName());
            productChildPartDisplayVO.setProductSeries(product.getProductSeries());

            //Get childPartId list associated to provided productID
            List<ProductChildPartEntity> productChildPartEntityList = productChildPartRepo.findByproductId(product.getProductId());

            for (ProductChildPartEntity getchildPartid : productChildPartEntityList) {
                //Get childpart list associated with childpartId
                ChildPartEntity childPart = childPartRepo.findBychildPartId(getchildPartid.getChildPartId());
                ChildPartVO childPartVO = new ChildPartVO();
                childPartVO.setChildPartName(childPart.getChildPartName());
                childPartVO.setChildPartSeries(childPart.getChildPartSeries());
                childPartVO.setChildPartOpeningStock(childPart.getChildPartOpeningStock());
                childPartVOList.add(childPartVO);
            }
            productChildPartDisplayVO.setChildPartVOList(childPartVOList);


            productChildPartDisplayVOList.add(productChildPartDisplayVO);
        }
        return productChildPartDisplayVOList;


    }


    @Override
    public MonthlyPlanResponseVO getMonthlyPlanList(CreationDateVO creationDateVO) {
        MonthlyPlanResponseVO monthlyPlanResponseVO = new MonthlyPlanResponseVO();
        MonthlyPlanEntityVO monthlyPlanEntityVO = new MonthlyPlanEntityVO();
//        if (creationDateVO.getStartDate().equals(monthlyPlanEntityVO.getCreationDate())
//                && creationDateVO.getEndDate().equals(monthlyPlanEntityVO.getCreationDate())) {
        monthlyPlanResponseVO.setStartDate(monthlyPlanEntityVO.getCreationDate());
        monthlyPlanResponseVO.setEndDate(monthlyPlanEntityVO.getCreationDate());
        monthlyPlanResponseVO.setMonthlyDisplayVOList(
                getMonthlyPlanVODisplayList(creationDateVO));
        return monthlyPlanResponseVO;


        //  }
        // return monthlyPlanResponseVO;
    }

    public List<MonthlyDisplayVO> getMonthlyPlanVODisplayList(
            CreationDateVO creationDateVO) {
        List<MonthlyDisplayVO> monthlyDisplayVOList = new ArrayList<>();

        List<ProductEntity> productEntities = productRepo.findAll();

        Map<Long, ProductEntity> productMap = new HashMap<>();
        for (ProductEntity productEntity1 : productEntities) {
            productMap.put(productEntity1.getProductId(), productEntity1);
        }
        List<MonthlyPlanEntity> monthlyPlanEntityList = monthlyPlanRepo.findBycreationDateBetween(creationDateVO.getStartDate(), creationDateVO.getEndDate());


        for (MonthlyPlanEntity monthlyPlanEntity : monthlyPlanEntityList) {
            MonthlyDisplayVO monthlyDisplayVO = new MonthlyDisplayVO();

            ProductEntity product = productMap.get(monthlyPlanEntity.getProductId());
            monthlyDisplayVO.setProductSeries(product.getProductSeries());
            monthlyDisplayVO.setProductName(product.getProductName());
            monthlyDisplayVO.setWeek1(monthlyPlanEntity.getWeek1());
            monthlyDisplayVO.setWeek2(monthlyPlanEntity.getWeek2());
            monthlyDisplayVO.setWeek3(monthlyPlanEntity.getWeek3());
            monthlyDisplayVO.setWeek4(monthlyPlanEntity.getWeek4());
            monthlyDisplayVO.setTotal(monthlyDisplayVO.getWeek1() + monthlyDisplayVO.getWeek2() + monthlyDisplayVO.getWeek3() + monthlyDisplayVO.getWeek4());
            monthlyDisplayVO.setOpeningStock(product.getProductOpeningStock());
            monthlyDisplayVO.setDispatch(500);
            monthlyDisplayVO.setProductionRFD(100);
            CoverageVO coverageVO = new CoverageVO();
            coverageVO.setCoverageWeek1(monthlyDisplayVO.getOpeningStock() + monthlyDisplayVO.getProductionRFD() - monthlyPlanEntity.getWeek1());
            coverageVO.setCoverageWeek2(coverageVO.getCoverageWeek1() - monthlyPlanEntity.getWeek2());
            coverageVO.setCoverageWeek3(coverageVO.getCoverageWeek2() - monthlyPlanEntity.getWeek3());
            coverageVO.setCoverageWeek4(coverageVO.getCoverageWeek3() - monthlyPlanEntity.getWeek4());
            coverageVO.setCoverageTotal(coverageVO.getCoverageWeek4());
            monthlyDisplayVO.setCoverageVO(coverageVO);

            List<ChildPartWeeklyPlan> weeklyPlan = getchildPartWeeklyPlan();
            List<ChildPartWithCoverage> childPartsCoverageVOList = new ArrayList<>();
            List<ProductChildPartEntity> productChildPartEntityList = productChildPartRepo.findByproductId(product.getProductId());
            for (ProductChildPartEntity getchildPartid : productChildPartEntityList) {
                //Get childpart list associated with childpartId
                ChildPartEntity childPart = childPartRepo.findBychildPartId(getchildPartid.getChildPartId());

                ChildPartWithCoverage childPartsWithCoverageVO = new ChildPartWithCoverage();
                //List<ChildPartWeeklyPlan> childPartWeeklyPlanList= getchildPartWeeklyPlan(childPart.getChildPartId());
                childPartsWithCoverageVO.setChildPartId(childPart.getChildPartId());
                childPartsWithCoverageVO.setChildPartName(childPart.getChildPartName());
                childPartsWithCoverageVO.setChildPartSeries(childPart.getChildPartSeries());
                childPartsWithCoverageVO.setChildPartQuantity(getchildPartid.getQuantity());

                ChildPartWeeklyPlan childPartWeeklyPlan = new ChildPartWeeklyPlan();
                for (ChildPartWeeklyPlan chP : weeklyPlan) {

                    if (chP.getChildPartId() == childPart.getChildPartId()) {
                        childPartWeeklyPlan.setChildPartId(childPartWeeklyPlan.getChildPartId());
                        childPartWeeklyPlan.setChildPWeeklyPlanI(chP.getChildPWeeklyPlanI());
                        childPartWeeklyPlan.setChildPWeeklyPlanII(chP.getChildPWeeklyPlanII());
                        childPartWeeklyPlan.setChildPWeeklyPlanIII(chP.getChildPWeeklyPlanIII());
                        childPartWeeklyPlan.setChildPWeeklyPlanIV(chP.getChildPWeeklyPlanIV());
                        childPartWeeklyPlan.setChildPMonthlyRequired(chP.getChildPMonthlyRequired());
                    }
                }

                ChildPartsCoverageVO childPartsCoverageVO = new ChildPartsCoverageVO();
                childPart.getChildPartOpeningStock();
                childPartWeeklyPlan.getChildPWeeklyPlanI();
                childPartsCoverageVO.setChildPCoverageWeek1(childPart.getChildPartOpeningStock()+ 100 - childPartWeeklyPlan.getChildPWeeklyPlanI());
                childPartsCoverageVO.setChildPCoverageWeek2(childPartsCoverageVO.getChildPCoverageWeek1()- childPartWeeklyPlan.getChildPWeeklyPlanII());
                childPartsCoverageVO.setChildPCoverageWeek3(childPartsCoverageVO.getChildPCoverageWeek2()- childPartWeeklyPlan.getChildPWeeklyPlanIII());
                childPartsCoverageVO.setChildPCoverageWeek4(childPartsCoverageVO.getChildPCoverageWeek3()- childPartWeeklyPlan.getChildPWeeklyPlanIV());
                childPartsCoverageVO.setChildPCoverageTotal(childPartsCoverageVO.getChildPCoverageWeek4());
                childPartsWithCoverageVO.setChildPartCoverage(childPartsCoverageVO);

                childPartsCoverageVOList.add(childPartsWithCoverageVO);
            }
           monthlyDisplayVO.setChildPartWithCoverageList(childPartsCoverageVOList);
            monthlyDisplayVOList.add(monthlyDisplayVO);
        }
        return monthlyDisplayVOList;
    }

    public List<ChildPartWeeklyPlan> getchildPartWeeklyPlan() {
        List<ChildPartWeeklyPlan> childPartWeeklyPlanList = new ArrayList<>();

        List<ChildPartEntity> childPartEntityList = childPartRepo.findAll();
        // ChildPartEntity childPartEntity = childPartRepo.findBychildPartId(childpartID);

        for (ChildPartEntity childPartEntity : childPartEntityList) {
            ChildPartWeeklyPlan childPartWeeklyPlan = new ChildPartWeeklyPlan();

            List<ProductChildPartEntity> childPartById = productChildPartRepo.findBychildPartId(childPartEntity.getChildPartId());
            childPartWeeklyPlan.setChildPartId(childPartEntity.getChildPartId());
            childPartWeeklyPlan.setChildPartSeries(childPartEntity.getChildPartSeries());
            Integer sumvaluePlanI = 0, sumvaluePlanII = 0, sumvaluePlanIII = 0, sumvaluePlanIV = 0;
            for (ProductChildPartEntity productChildPartEntity : childPartById) {
                MonthlyPlanEntity monthlyPlanEntity = monthlyPlanRepo.findByproductId(productChildPartEntity.getProductId());
                Integer valuePlanI = productChildPartEntity.getQuantity() * monthlyPlanEntity.getWeek1();
                Integer valuePlanII = productChildPartEntity.getQuantity() * monthlyPlanEntity.getWeek2();
                Integer valuePlanIII = productChildPartEntity.getQuantity() * monthlyPlanEntity.getWeek3();
                Integer valuePlanIV = productChildPartEntity.getQuantity() * monthlyPlanEntity.getWeek4();
                sumvaluePlanI = valuePlanI + sumvaluePlanI;
                sumvaluePlanII = valuePlanII + sumvaluePlanII;
                sumvaluePlanIII = valuePlanIII + sumvaluePlanIII;
                sumvaluePlanIV = valuePlanIV + sumvaluePlanIV;

                childPartWeeklyPlan.setChildPWeeklyPlanI(sumvaluePlanI);
                childPartWeeklyPlan.setChildPWeeklyPlanII(sumvaluePlanII);
                childPartWeeklyPlan.setChildPWeeklyPlanIII(sumvaluePlanIII);
                childPartWeeklyPlan.setChildPWeeklyPlanIV(sumvaluePlanIV);
                childPartWeeklyPlan.setChildPMonthlyRequired(childPartWeeklyPlan.getChildPWeeklyPlanI() +
                        childPartWeeklyPlan.getChildPWeeklyPlanII() + childPartWeeklyPlan.getChildPWeeklyPlanIII() +
                        childPartWeeklyPlan.getChildPWeeklyPlanIV());
            }
            childPartWeeklyPlanList.add(childPartWeeklyPlan);
        }
        return childPartWeeklyPlanList;
//        ChildPartWeeklyPlan childPartWeeklyPlan= (ChildPartWeeklyPlan) childPartWeeklyPlanList.stream()
//                .filter(childPartSeries1-> {return  childPartSeries1.equals(childPartSeries);}).findAny().orElse(null);
//        Optional<ChildPartWeeklyPlan> childPartWeeklyPlan = childPartWeeklyPlanList.stream()
//                .filter(childPartSeries1 -> childPartSeries1.getChildPartSeries().equalsIgnoreCase(childPartSeries))
//                .findFirst();
//        return childPartWeeklyPlan.isPresent() ? childPartWeeklyPlan.get() : null;
//        ChildPartWeeklyPlan childPartWeeklyPlan=new ChildPartWeeklyPlan();
//       if(childPartWeeklyPlanList.contains(childPartID)){
//
//           childPartWeeklyPlan.setChildPartId(childPartWeeklyPlan.getChildPartId());
//           childPartWeeklyPlan.setChildPWeeklyPlanI(childPartWeeklyPlan.getChildPWeeklyPlanI());
//           childPartWeeklyPlan.setChildPWeeklyPlanII(childPartWeeklyPlan.getChildPWeeklyPlanII());
//           childPartWeeklyPlan.setChildPWeeklyPlanIII(childPartWeeklyPlan.getChildPWeeklyPlanIII());
//           childPartWeeklyPlan.setChildPWeeklyPlanIV(childPartWeeklyPlan.getChildPWeeklyPlanIV());
//           childPartWeeklyPlan.setChildPMonthlyRequired(childPartWeeklyPlan.getChildPMonthlyRequired());
//           return childPartWeeklyPlan;
//       }
//       return childPartWeeklyPlan;


    }


}
