package com.kokak.webstoregpttest.controller;

import com.kokak.webstoregpttest.object.dto.ProductDTO;
import com.kokak.webstoregpttest.object.entity.ProductEntity;
import com.kokak.webstoregpttest.object.model.ProductModel;
import com.kokak.webstoregpttest.service.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductServiceImp productService;

    @GetMapping("/products/fetchAndSave")
    public List<ProductDTO> fetchAndSaveProducts(@RequestParam(value = "limit"/*defaultValue = "5"*/) int limit) {


        List<ProductDTO> listProducts = productService.fetchAndSaveProducts(limit);


        System.out.println("List: " + listProducts);
        // Fetches and saves products, then returns the fetched products
        return listProducts;
    }

    @GetMapping("/products")
    public List<ProductEntity> getAll(){
        return productService.getAllProducts();
    }
}
