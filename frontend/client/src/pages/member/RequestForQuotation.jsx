import { Title, InputForm, Button } from "@/components";
import React, { useState } from "react";
import { useSelector } from "react-redux";
import { formatVietnameseToString } from "@/ultils/fn";
import { Link } from "react-router-dom";
import path from "@/ultils/path";
import moment from "moment"
import { useForm } from "react-hook-form"
import { MdBookmarkRemove } from "react-icons/md";
import { apiRemoveRequestForQuotation, apiSendRequestForQuotations } from "@/apis/service";
import { toast } from "react-toastify";
import Swal from "sweetalert2";

const RequestForQuotation = () => {
    const { requestForQuotation } = useSelector((s) => s.user);
    const {
        register,
        handleSubmit,
        formState: { errors },
        reset,
        watch,
    } = useForm()

    const handleSendPriceQuote = async (data) => {
        Swal.fire({
            icon: "warning",
            title: "Xác nhận thao tác",
            text: "Bạn có chắc muốn gửi thông tin nhận báo giá dịch vụ này?",
            showCancelButton: true,
            showConfirmButton: true,
            confirmButtonText: "Gửi",
            cancelButtonText: "Quay lại",
        }).then(async (rs) => {
            if (rs.isConfirmed) {
                const serviceIds = requestForQuotation?.data?.map((service) => service.id); // Collect service IDs
                const requestBody = {
                    name: data.name,
                    email: data.email,
                    phoneNumber: data.phoneNumber,
                    notes: data.content,
                    serviceIds: serviceIds,
                }
                const response = await apiSendRequestForQuotations(requestBody);
                if (response.success) {
                  toast.success(response.message)
                  window.location.reload()
                } else toast.error("Có lỗi hãy thử lại sau")
            }
        })
    };

    const handleRemoveRequestForQuotation = async (serviceId) => {

        const response = await apiRemoveRequestForQuotation(serviceId)
        if (response.success) {
            toast.success(response.message)
            window.location.reload()
        } else {
            toast.error(response.message)
        }
    }

    return (
        <div className="container mx-auto px-4 py-8">
            <Title title="Danh sách yêu cầu báo giá" />
            <div className="flex flex-col lg:flex-row gap-8">
                {/* Phần hiển thị dịch vụ */}
                <div className="w-full lg:w-2/3">
                    <div className="bg-white shadow-md rounded-lg px-8 pt-6 pb-8 mb-4">
                        <h2 className="text-2xl font-bold mb-6">Dịch vụ</h2>
                        <div className="space-y-4 max-h-[calc(100vh-300px)] overflow-y-auto mb-4">
                            {requestForQuotation?.data?.map((service) => (
                                <div
                                    key={service.id}
                                    className="flex items-start space-x-4 pb-4 border-b border-gray-200 last:border-b-0"
                                >
                                    <img
                                        src={service.image}
                                        alt={`Service ${service.id}`}
                                        width={80}
                                        height={80}
                                        className="object-cover rounded-tl-md rounded-tr-md"
                                    />
                                    <div className="flex-1">
                                        <Link
                                            to={`/${path.DETAIL_POST}/${service.id}/${formatVietnameseToString(
                                                service.title
                                            )}`}
                                            className="text-fuchsia-950 text-lg font-semibold cursor-pointer hover:underline"
                                        >
                                            {service.title}
                                        </Link>
                                        <p className="text-sm text-gray-600 mt-1">{service.address}</p>
                                        <p className="text-sm text-gray-500 mt-1"> {moment(service?.createdDate).format("DD/MM/YYYY")}</p>
                                    </div>
                                    <div className="flex items-end justify-center mt-5">
                                        <MdBookmarkRemove
                                            className="text-red-500 hover:text-red-800 cursor-pointer"
                                            size={24}
                                            onClick={() => handleRemoveRequestForQuotation(service.id)}
                                        />
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>

                {/* Phần thông tin checkout */}
                <div className="w-full lg:w-1/3">
                    <div className="w-full flex flex-col gap-2 items-center justify-center border-2 rounded-md text-black p-4">
                        <h1 className="text-xl font-bold">Yêu Cầu Báo Giá</h1>
                        <InputForm
                            register={register}
                            errors={errors}
                            id="name"
                            validate={{
                                required: "Trường này không được bỏ trống."
                            }}
                            placeholder="Tên"
                            fullWidth
                        />
                        <InputForm
                            register={register}
                            errors={errors}
                            id="email"
                            validate={{
                                required: "Trường này không được bỏ trống."
                            }}
                            placeholder="E-mail"
                            fullWidth
                        />
                        <InputForm
                            register={register}
                            errors={errors}
                            id="phoneNumber"
                            validate={{
                                required: "Trường này không được bỏ trống."
                            }}
                            placeholder="Điện thoại"
                            fullWidth
                        />
                        <textarea
                            placeholder="Bạn hãy mô tả thêm ghi chú"
                            id="content"
                            rows="5"
                            className="form-textarea w-full rounded-md border-gray-200"
                            {...register("content", { required: "Không thể bỏ trống." })}
                        ></textarea>
                        {errors["content"] && (
                            <small className="text-xs text-red-500">
                                {errors["content"]?.message}
                            </small>
                        )}
                        <div className="mt-6 mb-6">
                            <span className="">Khi bạn click vào "Nhận báo giá", nghĩa là bạn đã đồng ý với các điều khoản sử dụng của SweetDream. </span>
                        </div>

                        <Button onClick={handleSubmit(handleSendPriceQuote)}>Nhận Báo Giá</Button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default RequestForQuotation;
