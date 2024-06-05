package com.kokak.webstoregpttest.service;


import com.kokak.webstoregpttest.mapping.ProductMapper;
import com.kokak.webstoregpttest.object.dto.ProductDTO;
import com.kokak.webstoregpttest.object.dto.RatingDTO;
import com.kokak.webstoregpttest.object.entity.ProductEntity;
import com.kokak.webstoregpttest.repository.ProductRepository;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService{

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepository productRepository;

    private List<ProductDTO> fetchProducts(int limit) {
        String url = "https://fakestoreapi.com/products?limit=" + limit;
        HttpResponse<String> response = Unirest.get(url).asString();
        if (response.getStatus() != 200) {
            logger.error("Failed to fetch products from Fake Store API");
            return new ArrayList<>(); // Return an empty list or consider throwing an exception
        }
        JSONArray jsonArray = new JSONArray(response.getBody());
        List<ProductDTO> products = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(jsonObject.getInt("id")); // Assuming ID is a Long
            productDTO.setTitle(jsonObject.getString("title"));
            productDTO.setPrice(jsonObject.getDouble("price"));
            productDTO.setDescription(jsonObject.getString("description"));
            productDTO.setCategory(jsonObject.getString("category"));
            productDTO.setImage(jsonObject.getString("image"));
            productDTO.setRating(new RatingDTO(
                    jsonObject.getJSONObject("rating").getDouble("rate"),
                    jsonObject.getJSONObject("rating").getInt("count")));
            products.add(productDTO);
        }

        return products;
    }

    @Transactional
    public List<ProductDTO> fetchAndSaveProducts(int limit) {
        List<ProductDTO> fetchedProducts = fetchProducts(limit);
        List<ProductEntity> productEntities = fetchedProducts.stream()
                .map(productMapper::dtoToEntity)
                .collect(Collectors.toList());
        productRepository.saveAll(productEntities); // Save entities to the database
        logger.info("Fetched and saved {} products to the database", productEntities.size());
        return fetchedProducts; // Return fetched products
    }

    public List<ProductEntity> getAllProducts(){

       return productRepository.findAll();

    }
}
