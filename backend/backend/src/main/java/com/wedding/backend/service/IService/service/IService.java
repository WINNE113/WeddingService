package com.wedding.backend.service.IService.service;

import com.wedding.backend.base.BaseResultWithData;
import com.wedding.backend.base.BaseResultWithDataAndCount;
import com.wedding.backend.dto.service.ServiceDTO;
import com.wedding.backend.dto.service.ServiceDetail;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IService {
    BaseResultWithDataAndCount<List<ServiceDTO>> getAllByFalseDeletedAndAcceptStatus(Pageable pageable);

    BaseResultWithDataAndCount<List<ServiceDTO>> getAllByServiceTypeAndAcceptStatus(Long serviceTypeId,Pageable pageable);

    BaseResultWithData<ServiceDetail> getDetailServiceById(Long serviceId);
}
