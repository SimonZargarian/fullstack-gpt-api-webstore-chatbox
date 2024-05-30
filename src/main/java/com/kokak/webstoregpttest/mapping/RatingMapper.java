package com.kokak.webstoregpttest.mapping;
import com.kokak.webstoregpttest.object.dto.RatingDTO;
import com.kokak.webstoregpttest.object.entity.RatingEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    RatingEntity dtoToEntity(RatingDTO dto);
    RatingDTO entityToDto(RatingEntity entity);
}
