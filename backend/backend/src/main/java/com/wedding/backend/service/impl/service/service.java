package com.wedding.backend.service.impl.service;

import com.wedding.backend.base.BaseResultWithDataAndCount;
import com.wedding.backend.common.StatusCommon;
import com.wedding.backend.dto.service.ServiceDTO;
import com.wedding.backend.exception.ResourceNotFoundException;
import com.wedding.backend.mapper.ServiceMapper;
import com.wedding.backend.repository.ServiceRepository;
import com.wedding.backend.service.IService.service.IService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class service implements IService {
    private final ServiceRepository repository;
    private final ServiceMapper mapper;

    @Override
    public BaseResultWithDataAndCount<List<ServiceDTO>> getAllByFalseDeletedAndAcceptStatus(Pageable pageable) {
        BaseResultWithDataAndCount<List<ServiceDTO>> result = new BaseResultWithDataAndCount<>();
        try {
            List<ServiceDTO> resultFromDb = repository.findAllByIsDeletedFalse(pageable)
                    .stream()
                    .map(serviceEntity -> mapper.entityToDto(serviceEntity))
                    .filter(serviceDTO -> serviceDTO.getStatus().equals(StatusCommon.APPROVED))
                    .toList();
            Long countResultFromDb = (long) resultFromDb.size();

            result.set(resultFromDb, countResultFromDb);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
        return result;
    }
}
