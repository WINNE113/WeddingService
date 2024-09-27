package com.wedding.backend.repository;

import com.wedding.backend.common.StatusCommon;
import com.wedding.backend.dto.supplier.LicencesSupplier;
import com.wedding.backend.dto.supplier.ServiceLimitResponse;
import com.wedding.backend.entity.SupplierEntity;
import com.wedding.backend.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {
    Optional<SupplierEntity> findByPhoneNumberSupplier(String phoneNumber);

    Optional<SupplierEntity> findByEmailSupplier(String email);

    Optional<SupplierEntity> findByUser(UserEntity email);

    List<SupplierEntity> findAllByIsDeletedFalseOrderByCreatedDateDesc(Pageable pageable);

    Long countAllByIsDeletedFalse();

    Long countAllByIsDeletedFalseAndStatusSupplier(StatusCommon statusCommon);

    List<SupplierEntity> findAllByIsDeletedFalse();


    List<SupplierEntity> findAllByIsDeletedFalseAndStatusSupplier(StatusCommon statusCommon);



    @Query("SELECT s from SupplierEntity as s join TransactionEntity as t on s.id = t.userTransaction.id where s.isDeleted = false order by t.purchaseDate desc")
    List<SupplierEntity> findAllByTransactionEntitiesAndIsDeletedFalse(Pageable pageable);

    Optional<SupplierEntity> findByStatusSupplierAndUser_Id(StatusCommon statusCommon, String userId);

    Optional<SupplierEntity> findByUser_Id(String userId);


    @Query(value = "Select supplier.id as supplierId , service_limit as serviceLimit from supplier \n" +
            "inner join transaction on supplier.id = transaction.supplier_id\n" +
            "inner join service_package on transaction.package_id = service_package.id \n" +
            "where supplier.id =:supplierId and transaction.expired = false", nativeQuery = true)
    ServiceLimitResponse getServiceLimitOfPackageVIP(@Param("supplierId") Long supplierId);

    @Query(
            value = "Select s.id, img.url_images_license as imagesLicence from supplier_entity_url_images_license as img \n" +
                    "inner join supplier as s on img.supplier_entity_id = s.id \n" +
                    "where s.id=:sid", nativeQuery = true)
    List<LicencesSupplier> licencesSupplier(@Param("sid") Long supplierId);
}
