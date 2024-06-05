package com.kokak.webstoregpttest.service;

import com.kokak.webstoregpttest.object.dto.ProductDTO;
import com.kokak.webstoregpttest.object.entity.ProductEntity;

import java.util.List;

public interface ProductService {

    List<ProductDTO> fetchAndSaveProducts(int limit);

    List<ProductEntity> getAllProducts();



}
