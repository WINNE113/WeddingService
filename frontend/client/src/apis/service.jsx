import axios from "@/axios";

export const apiGetServiceType = () =>
    axios({
        url: "/service-type/getAll",
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
export const apiGetDetailService = (params) => {
    return axios({
        url: "/service/detail-service",
        method: "GET",
        params
    })
}

export const apiGetAlbumOfService = (params) => {
    return axios({
        url: "/guest/albByName",
        method: "GET",
        params
    })
}