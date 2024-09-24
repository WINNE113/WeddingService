import axios from "@/axios";

export const apiGetServiceType = () =>
    axios({
        url: "/service-type/getAll",
        method: "GET"
    })

export const apiCreateNewBooking = (data) =>
    axios({
        url: "/request-for-quotation/add",
        method: "post",
        data,
    })

export const apiSendRequestForQuotations = (data) =>
    axios({
        url: "/request-for-quotation/send-request-for-quotation",
        method: "POST",
        data
    })

export const apiGetServiceByFuzzySearch = (params) => 
    axios({
        url: "/elasticsearch-services/fuzzy-search",
        method: "GET",
        params
    })

export const apiAddToRequestForQuotation = (params) =>
    axios({
        url: "/request-for-quotation/add-request-for-quotation-to-cart",
        method: "post",
        params,
    })

export const apiRemoveRequestForQuotation = (serviceId) =>
    axios({
        url: `/request-for-quotation/remove-by-customer/${serviceId}`,
        method: "Delete",
    })

export const apiGetBookingServices = () =>
    axios({
        url: "/request-for-quotation/request-for-quotation-by-supplier-id",
        method: "GET"
    })

export const apiGetServiceByDeleted = (params) =>
    axios({
        url: "/service/getAllByDeleted",
        method: "GET",
        params,
    })
export const apiGetServiceByServiceType = (params) =>
    axios({
        url: "/service/getAllByServiceType",
        method: "GET",
        params,
    })
export const apiGetServiceBySuggested = (params) =>
    axios({
        url: "/service/suggest-by-follow-or-not",
        method: "GET",
        params,
    })
export const apiGetDetailService = (params) => {
    return axios({
        url: "/service/detail-service",
        method: "GET",
        params
    })
}

export const apiGetServices = (data) =>
    axios({
        url: "/guest/service/filters",
        method: "post",
        data,
    })

export const apiGetAlbumOfService = (params) => {
    return axios({
        url: "/guest/albByName",
        method: "GET",
        params
    })
}

export const apiGetServiceByPackageVIP = (params) => {
    return axios({
        url: "/guest/service/services-by-package-vip",
        method: "GET",
        params
    })
}

export const apiGetServiceByPackageVIP1AndVIP2 = (params) => {
    return axios({
        url: "/guest/service/services-by-package-VIP1-VIP2",
        method: "GET",
        params,
    })
}

export const apiGetServiceBySupplier = (params) => {
    return axios({
        url: "/service/getBySupplier",
        method: "GET",
        params
    })
}

export const apiDeleteServices = (params) =>
    axios({
        url: "/service/delete-by-ids",
        method: "DELETE",
        params
    })

export const apiDeleteService = (params) =>
    axios({
        url: "/service/delete-by-id",
        method: "delete",
        params
    })

export const apiCreateNewService = (data) =>
    axios({
        url: "/service/update-insert",
        method: "post",
        data,
    })
export const apiGetServiceBySupplierId = (supplierId, params) =>
    axios({
        url: `guest/service/${supplierId}`,
        method: "GET",
        params
    })
export const apiGetServiceTypeNameBySupplierId = (supplierId) =>
    axios({
        url: `guest/service-type-name/${supplierId}`,
        method: "GET",
    })
export const apiUpdateApprovedService = (params) =>
    axios({
        url: "/admin/setIsApprovedService",
        method: "patch",
        params,
    })
export const apiUpdateRejectedService = (params) =>
    axios({
        url: "/admin/setIsRejectedService",
        method: "patch",
        params,
    })
export const apiUpdateStatusBooking = (params) =>
    axios({
        url: "/request-for-quotation/change/status-request-for-quotation",
        method: "patch",
        params,
    })

export const apiUpdateServiceSelected = (serviceId) =>
    axios({
        url: `/service/update/service-selected/${serviceId}`,
        method: "patch",
    })
export const apiGetServiceByLocation = (params) =>
    axios({
        url: "/guest/service/location",
        method: "get",
        params,
    })