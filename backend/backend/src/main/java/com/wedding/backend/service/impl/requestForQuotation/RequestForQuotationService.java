package com.wedding.backend.service.impl.requestForQuotation;

import com.wedding.backend.base.BaseResult;
import com.wedding.backend.base.BaseResultWithDataAndCount;
import com.wedding.backend.common.StatusCommon;
import com.wedding.backend.dto.requestForQuotationService.BookingServiceDTO;
import com.wedding.backend.dto.requestForQuotationService.BookingServicesBySupplier;
import com.wedding.backend.dto.requestForQuotationService.RequestForQuotationsDTO;
import com.wedding.backend.dto.service.ServiceByRequestForQuotation;
import com.wedding.backend.entity.RequestForQuotationEntity;
import com.wedding.backend.entity.ServiceEntity;
import com.wedding.backend.entity.SupplierEntity;
import com.wedding.backend.entity.UserEntity;
import com.wedding.backend.exception.ResourceNotFoundException;
import com.wedding.backend.mapper.RequestForQuotationServiceMapper;
import com.wedding.backend.repository.RequestForQuotationRepository;
import com.wedding.backend.repository.ServiceRepository;
import com.wedding.backend.repository.SupplierRepository;
import com.wedding.backend.service.IService.requestForQuotation.IRequestForQuotationService;
import com.wedding.backend.util.message.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RequestForQuotationService implements IRequestForQuotationService {

    private final RequestForQuotationRepository requestForQuotationRepository;
    private final RequestForQuotationServiceMapper mapper;
    private final ServiceRepository serviceRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public BaseResult addBooking(BookingServiceDTO bookingServiceDTO) {
        try {
            //TODO: Find if phone && serviceId && status == success found => Rejected
            List<RequestForQuotationEntity> dataFromDb = requestForQuotationRepository.findAllByServerBooking_IdAndPhoneNumberAndStatus(bookingServiceDTO.getServiceId(), bookingServiceDTO.getPhoneNumber(), StatusCommon.PROCESS);
            if (dataFromDb.isEmpty()) {
                RequestForQuotationEntity booking = mapper.dtoToEntity(bookingServiceDTO);
                Optional<ServiceEntity> service = serviceRepository.findById(bookingServiceDTO.getServiceId());
                service.ifPresent(booking::setServerBooking);
                booking.setStatus(StatusCommon.PROCESS);
                requestForQuotationRepository.save(booking);
                return new BaseResult(true, MessageUtil.MSG_SEND_BOOKING_SUCCESS);
            } else {
                return new BaseResult(true, MessageUtil.MSG_FOUND_BOOKING);
            }
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @Override
    public BaseResultWithDataAndCount<List<BookingServicesBySupplier>> getBookingServiceBySupplierId(Principal connectedUser) {
        BaseResultWithDataAndCount<List<BookingServicesBySupplier>> result = new BaseResultWithDataAndCount<>();
        try {
            var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            Optional<SupplierEntity> supplier = supplierRepository.findByUser_Id(user.getId());
            if (supplier.isPresent()) {
                Long supplierID = supplier.get().getId(); // Changed to 'supplierID'
                List<BookingServicesBySupplier> resultFromDb = requestForQuotationRepository.bookingServiceBySupplier(supplierID)
                        .stream()
                        .filter(bookingServicesBySupplier -> !bookingServicesBySupplier.getStatus().equals(StatusCommon.PENDING))
                        .collect(Collectors.toList());
                result.set(resultFromDb, (long) resultFromDb.size());
            } else {
                throw new ResourceNotFoundException(MessageUtil.SUPPLIER_NOT_FOUND);
            }
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
        return result;
    }

    @Override
    public BaseResult changeStatusBooking(String status, Long bookingId) {
        try {
            Optional<RequestForQuotationEntity> booking = requestForQuotationRepository.findById(bookingId);
            if (booking.isPresent()) {
                if (status.equals(StatusCommon.SUCCESS.name())) {
                    booking.get().setStatus(StatusCommon.SUCCESS);
                } else if (status.equals(StatusCommon.FAILED.name())) {
                    booking.get().setStatus(StatusCommon.FAILED);
                } else if (status.equals(StatusCommon.PROCESS.name())) {
                    booking.get().setStatus(StatusCommon.PROCESS);
                }
                requestForQuotationRepository.save(booking.get());
                return new BaseResult(true, MessageUtil.MSG_UPDATE_SUCCESS);
            } else {
                return new BaseResult(false, MessageUtil.BOOKING_NOT_FOUND);
            }
        } catch (Exception ex) {
            return new BaseResult(false, ex.getMessage());
        }
    }

    @Override
    public BaseResult addRequestForQuotationToCart(Long serviceId, Principal connectedUser) {
        BaseResult result = null;
        try {
            var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            if (user != null) {
                List<RequestForQuotationEntity> dataFromDb = requestForQuotationRepository.findAllByCreatedByAndServerBooking_Id(user.getId(), serviceId)
                        .stream()
                        .filter(requestForQuotationEntity -> requestForQuotationEntity.getStatus().equals(StatusCommon.PENDING) || requestForQuotationEntity.getStatus().equals(StatusCommon.PROCESS))
                        .toList();
                if (dataFromDb.isEmpty()) {
                    RequestForQuotationEntity requestForQuotationEntity = new RequestForQuotationEntity();
                    ServiceEntity serviceFromDb = serviceRepository.findById(serviceId)
                            .orElseThrow(() -> new ResourceNotFoundException(String.format("Service with ID %d not found", serviceId)));

                    requestForQuotationEntity.setServerBooking(serviceFromDb);
                    requestForQuotationEntity.setStatus(StatusCommon.PENDING);

                    requestForQuotationRepository.save(requestForQuotationEntity);

                    result = new BaseResult(true, MessageUtil.MSG_ADD_TO_REQUEST_FOR_QUOTATION_SUCCESS);
                } else {
                    result = new BaseResult(true, MessageUtil.MSG_SERVICE_ALREADY_IN_REQUEST_FOR_QUOTATION);
                }
            } else {
                return new BaseResult(false, MessageUtil.MSG_USER_BY_TOKEN_NOT_FOUND);
            }
        } catch (Exception ex) {
            return new BaseResult(false, ex.getMessage());
        }
        return result;
    }

    @Override
    public BaseResultWithDataAndCount<List<ServiceByRequestForQuotation>> getRequestForQuotationToCartByCustomerId(Pageable pageable, Principal connectedUser) {
        BaseResultWithDataAndCount<List<ServiceByRequestForQuotation>> result = new BaseResultWithDataAndCount<>();
        try {
            var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            if (user != null) {
                List<ServiceByRequestForQuotation> dataFromDB = requestForQuotationRepository.getServiceByRequestForQuotation(user.getId(), pageable);
                result.set(dataFromDB, (long) dataFromDB.size());
            } else {
                throw new ResourceNotFoundException(MessageUtil.MSG_USER_BY_TOKEN_NOT_FOUND);
            }
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
        return result;
    }

    @Override
    public BaseResult removeRequestForQuotationByCustomer(Long serviceId, Principal connectedUser) {
        try {
            var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            if (user != null) {
                Optional<RequestForQuotationEntity> requestForQuotationFromDb = requestForQuotationRepository.findByServerBooking_IdAndAndCreatedByAndStatus(serviceId, user.getId(), StatusCommon.PENDING);
                if (requestForQuotationFromDb.isPresent()) {
                    requestForQuotationRepository.delete(requestForQuotationFromDb.get());
                    return new BaseResult(true, MessageUtil.MSG_DELETE_SUCCESS);
                } else {
                    return new BaseResult(false, MessageUtil.MSG_SERVICE_NOT_FOUND);
                }
            } else {
                return new BaseResult(false, MessageUtil.MSG_USER_BY_TOKEN_NOT_FOUND);
            }
        } catch (Exception ex) {
            return new BaseResult(false, ex.getMessage());
        }
    }

    @Override
    public BaseResult sendPriceQuotes(RequestForQuotationsDTO request, Principal connectedUser) {
        BaseResult result = null;
        try {
            var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            if (user != null) {
                List<Long> serviceIds = request.getServiceIds();
                for (Long serviceId : serviceIds) {
                    Optional<RequestForQuotationEntity> requestForQuotation = requestForQuotationRepository.findByServerBooking_IdAndAndCreatedByAndStatus(serviceId, user.getId(), StatusCommon.PENDING);
                    if (requestForQuotation.isPresent()) {
                        requestForQuotation.get().setPhoneNumber(request.getPhoneNumber());
                        requestForQuotation.get().setName(request.getName());
                        requestForQuotation.get().setEmail(request.getEmail());
                        requestForQuotation.get().setNotes(request.getNotes());
                        Optional<ServiceEntity> service = serviceRepository.findById(serviceId);
                        service.ifPresent(serviceEntity -> requestForQuotation.get().setServerBooking(serviceEntity));
                        requestForQuotation.get().setStatus(StatusCommon.PROCESS);
                        requestForQuotationRepository.save(requestForQuotation.get());
                    }
                }
                result = new BaseResult(true, MessageUtil.MSG_SEND_BOOKING_SUCCESS);
            }

        } catch (Exception ex) {
            result = new BaseResult(false, ex.getMessage());
        }
        return result;
    }
}
