import { apiChangePassword } from "@/apis/user"
import { Button, InputForm, Title } from "@/components"
import WithBaseTopping from "@/hocs/WithBaseTopping"
import { logout } from "@/redux/userSlice"
import React, { useState } from "react"
import { useForm } from "react-hook-form"
import { toast } from "react-toastify"

const ChangePassword = ({ dispatch }) => {
    const [step, setStep] = useState("PHONE")
    const {
        register,
        formState: { errors },
        handleSubmit,
    } = useForm()
    const handleGetOtp = async (data) => {
        if (data.newPassword !== data.confirmationPassword)
            return toast.error("Nhập lại mật khẩu không đúng.")
        const response = await apiChangePassword(data)
        if (response.success) {
            toast.success(response.message)
            dispatch(logout())
        } else toast.error(response.message)
    }
    return (
        <div className="w-full min-h-screen bg-gray-100 flex items-center justify-center py-8">
            <div className="bg-white shadow-lg rounded-lg p-6 max-w-md w-full">
                <Title title="Đổi mật khẩu" className="text-center mb-6 text-2xl font-bold text-gray-800" />
                <div className="flex flex-col gap-4">
                    {step === "PHONE" && (
                        <>
                            <InputForm
                                id="currentPassword"
                                register={register}
                                errors={errors}
                                placeholder={"Nhập mật khẩu cũ"}
                                label="Mật khẩu cũ"
                                validate={{
                                    required: "Không được bỏ trống.",
                                    minLength: {
                                        value: 6,
                                        message: "Mật khẩu bắt buộc tối thiểu 6 ký tự.",
                                    },
                                }}
                                type="password"
                                className="border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-pink-500"
                            />
                            <InputForm
                                id="newPassword"
                                register={register}
                                errors={errors}
                                placeholder={"Nhập mật khẩu mới"}
                                label="Mật khẩu mới"
                                validate={{
                                    required: "Không được bỏ trống.",
                                    minLength: {
                                        value: 6,
                                        message: "Mật khẩu bắt buộc tối thiểu 6 ký tự.",
                                    },
                                }}
                                type="password"
                                className="border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-pink-500"
                            />
                            <InputForm
                                id="confirmationPassword"
                                register={register}
                                errors={errors}
                                placeholder={"Nhập lại mật khẩu mới"}
                                label="Nhập lại mật khẩu mới"
                                validate={{
                                    required: "Không được bỏ trống.",
                                    minLength: {
                                        value: 6,
                                        message: "Mật khẩu bắt buộc tối thiểu 6 ký tự.",
                                    },
                                }}
                                type="password"
                                className="border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-pink-500"
                            />
                            <Button
                                onClick={handleSubmit(handleGetOtp)}
                                className="w-full bg-pink-500 text-white rounded-lg px-4 py-2 font-semibold hover:bg-pink-600 transition duration-300"
                            >
                                Đổi mật khẩu
                            </Button>
                        </>
                    )}
                </div>
            </div>
        </div>
    )
}

export default WithBaseTopping(ChangePassword)
