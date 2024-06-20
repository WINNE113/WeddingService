import axios from "@/axios";

export const apiGetServiceType = () =>
    axios({
        url: "/service-type/getAll",
        method: "GET"
    })
