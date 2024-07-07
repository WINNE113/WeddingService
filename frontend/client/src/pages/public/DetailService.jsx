import React, { useEffect, useState } from "react"
import { useParams } from "react-router-dom"
import WithBaseTopping from "@/hocs/WithBaseTopping"
import { apiGetDetailService } from "@/apis/service"
import { BsPhoneVibrate } from "react-icons/bs"

const DetailService = ({ navigate, location, dispatch }) => {
    const { pid } = useParams()
    const [service, setService] = useState(null)

    const fetchDetailService = async () => {
        const response = apiGetDetailService({ serviceId: pid })
        console.log("Detail Service: " + JSON.stringify(response))
        if (response.success) setService(response.data)
    }

    useEffect(() => {
        fetchDetailService()
    }, [pid])

    if (!service) return <div className="h-80 text-center text-lg">Loading...</div>

    return (
        <div className="max-w-4xl mx-auto p-4 h-96">
            <div className="bg-white rounded-lg shadow-lg overflow-hidden">
                <img className="w-full h-64 object-cover" src={service.image} alt={service.title} />
                <div className="p-4">
                    <div className="flex items-center mb-4">
                        <img className="h-16 w-16 rounded-full" src={service.logo} alt="Logo" />
                        <div className="ml-4">
                            <h1 className="text-2xl font-bold">{service.title}</h1>
                            <p className="text-gray-600">{service.serviceTypeName}</p>
                        </div>
                    </div>
                    <p className="mb-2"><strong>Address:</strong> {service.addressService}</p>
                    <p className="mb-2"><strong>Supplier:</strong> {service.supplierName}</p>
                    <p className="mb-2"><strong>Supplier Address:</strong> {service.addressSupplier}</p>
                    <p className="mb-2 flex items-center"><BsPhoneVibrate className="mr-2" /> {service.phoneNumberService}</p>
                </div>
            </div>
        </div>
    )
}

export default WithBaseTopping(DetailService)
