package com.kokak.webstoregpttest.mapping;

import com.kokak.webstoregpttest.object.dto.ProductDTO;
import com.kokak.webstoregpttest.object.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RatingMapper.class)
public interface ProductMapper {

    @Mapping(target = "rating", source = "rating")
    ProductEntity dtoToEntity(ProductDTO dto);

    @Mapping(target = "rating", source = "rating")
    ProductDTO entityToDto(ProductEntity entity);
}
