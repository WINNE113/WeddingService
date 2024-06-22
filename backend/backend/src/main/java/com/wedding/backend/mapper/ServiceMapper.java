package com.wedding.backend.mapper;


import com.wedding.backend.dto.service.ServiceDTO;
import com.wedding.backend.entity.ServiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceDTO entityToDto(ServiceEntity serviceEntity);
    ServiceEntity dtoToEntity(ServiceDTO serviceDTO);
}
