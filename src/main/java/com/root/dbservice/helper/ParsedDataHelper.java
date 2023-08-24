package com.root.dbservice.helper;

import com.root.commondependencies.vo.ParsedDataVO;
import com.root.commondependencies.vo.ProductVO;
import com.root.dbservice.entities.ProductEntity;
import com.root.dbservice.repo.ProductRepo;
import com.root.dbservice.utils.ParsedDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class ParsedDataHelper {

    private final ProductRepo productRepo;
    @Autowired
    public ParsedDataHelper(ProductRepo productRepo){
        this.productRepo = productRepo;
    }

    public void saveProductsInDb(ParsedDataVO parsedDataVO){
        List<ProductVO> productList = parsedDataVO.getProductsList();
        if(!CollectionUtils.isEmpty(productList)){
            List<ProductEntity> productEntities = ParsedDataUtil.getProductEntityList(productList);
            productRepo.saveAll(productEntities);
        }
    }

}
