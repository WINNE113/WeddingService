package com.wedding.backend.repository;

import com.wedding.backend.common.StatusCommon;
import com.wedding.backend.dto.requestForQuotationService.BookingServicesBySupplier;
import com.wedding.backend.dto.service.ServiceByRequestForQuotation;
import com.wedding.backend.entity.RequestForQuotationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestForQuotationRepository extends JpaRepository<RequestForQuotationEntity, Long> {

    @Query(value = "SELECT b.id, b.name as nameCustomer, b.email, b.created_date as createdDate, b.phone_number as phoneNumber, b.note, b.service_id as serviceId, s.title as titleService, b.status " +
            "FROM request_for_quotation as b " +
            "JOIN services as s ON b.service_id = s.id " +
            "JOIN supplier as sup ON s.supplier_id = sup.id " +
            "WHERE sup.id = :supplierID", nativeQuery = true)
    List<BookingServicesBySupplier> bookingServiceBySupplier(@Param("supplierID") Long supplierID);

    @Query(
            value = "Select s.id, s.title, s.address, s.created_date as createdDate, s.image, rfq.id as requestForQuotationId\n" +
                    "From request_for_quotation as rfq inner join services as s on rfq.service_id = s.id \n" +
                    "where rfq.created_by =:createdBy and rfq.status = 'PENDING'", nativeQuery = true
    )
    List<ServiceByRequestForQuotation> getServiceByRequestForQuotation(@Param("createdBy") String createdBy, Pageable pageable);

    List<RequestForQuotationEntity> findAllByServerBooking_IdAndPhoneNumberAndStatus(Long serviceId, String phoneNumber, StatusCommon status);

    List<RequestForQuotationEntity> findAllByCreatedByAndServerBooking_Id(String createdBy, Long ServiceId);

    Optional<RequestForQuotationEntity> findByServerBooking_IdAndAndCreatedByAndStatus(Long serviceId, String createdBy, StatusCommon status);
}
