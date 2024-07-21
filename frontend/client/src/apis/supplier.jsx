
import axios from "@/axios";

export const apiAddInforSupplier = (data) =>
    axios({
        url: "/supplier/add",
        method: "post",
        data,
    })

export const apiSupplierGetByUser = () =>
    axios({
        url: "/supplier/getByUser",
        method: "GET"
    })
export const apiSuppliers = () =>
    axios({
        url: "/supplier/getAll",
        method: "GET"
    })
