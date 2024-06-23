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