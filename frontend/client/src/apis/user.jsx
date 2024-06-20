import axios from "@/axios";

export const apiRegister = (data) =>
    axios({
        url: "/auth/register",
        method: "post",
        data
    })
export const apiLogin = (data) =>
    axios({
        url: "/auth/login",
        method: "post",
        data
    })
export const apiGetCurrent = () =>
    axios({
        url: "/user/view-profile",
        method: "get",
    })
export const apiGetWishlist = (params) =>
    axios({
        url: "/wishlist/get",
        method: "get",
        params,
    })