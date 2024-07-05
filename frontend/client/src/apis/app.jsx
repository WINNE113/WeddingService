import axios from "axios"

export const apiGetProvinces = () =>
    axios({
        url: "https://vapi.vnappmob.com/api/province/",
        method: "get",
    })
export const apiGetLngLatFromAddress = (params) =>
    axios({
        method: "get",
        url: `https://api.geoapify.com/v1/geocode/search`,
        params,
    })
export const apiGetProvince = (provinceCode) =>
    axios({
        url: `https://provinces.open-api.vn/api/p/${provinceCode}?depth=3`,
        method: "get",
    })
export const apiGetDistricts = (provinceCode) =>
    axios({
        url: `https://vapi.vnappmob.com/api/province/district/${provinceCode}`,
        method: "get",
    })
export const apiGetWards = (destrictCode) =>
    axios({
        url: `https://vapi.vnappmob.com/api/province/ward/${destrictCode}`,
        method: "get",
    })
