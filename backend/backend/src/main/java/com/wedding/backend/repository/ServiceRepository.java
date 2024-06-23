package com.wedding.backend.repository;

import com.wedding.backend.entity.ServiceEntity;
import com.wedding.backend.entity.ServiceTypeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findAllByIsDeletedFalse(Pageable pageable);

    List<ServiceEntity> findAllByServiceTypeAndIsDeletedFalse(ServiceTypeEntity serviceType, Pageable pageable);
}
