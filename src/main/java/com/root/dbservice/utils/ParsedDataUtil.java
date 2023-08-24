package com.root.dbservice.utils;

import com.root.commondependencies.vo.ProductVO;
import com.root.dbservice.entities.ProductEntity;

import java.util.ArrayList;
import java.util.List;

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

}
