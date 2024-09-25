import React, { useEffect, useState } from "react"
import { useNavigate, useSearchParams } from "react-router-dom"
import { Button } from "@/components"
import path from "@/ultils/path"
const PaymentResult = () => {
  const [resultPayment, setResultPayment] = useState(false);
  const [searchParams] = useSearchParams()
  const navigate = useNavigate();


  useEffect(() => {
    const successParam = searchParams.get("success") === "true";
    setResultPayment(successParam);
  }, [searchParams])


  const handleButtonClick = () => {
    if (resultPayment) {
      navigate("/");
    } else {
      navigate(`/${path.SUPPLIER}/${path.DEPOSIT}`);
    }
  };


  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="w-full max-w-md bg-white rounded-lg shadow-md overflow-hidden">
        <div className="p-6">
          <h2 className="text-2xl font-bold text-center mb-4">
            {resultPayment ? "Thanh toán thành công" : "Thanh toán thất bại"}
          </h2>
          <div className="flex justify-center mb-4">
            {resultPayment ? (
              <svg
                className="w-16 h-16 text-green-500"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
                />
              </svg>
            ) : (
              <svg
                className="w-16 h-16 text-red-500"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"
                />
              </svg>
            )}
          </div>
          <p className="text-center text-gray-600 mb-6">
            {resultPayment
              ? "Cảm ơn bạn đã thanh toán. Giao dịch của bạn đã hoàn tất thành công."
              : "Chúng tôi rất tiếc, nhưng đã xảy ra lỗi khi xử lý thanh toán của bạn. Vui lòng thử lại."}
          </p>
          <div className="flex justify-center">
            <Button
              className={`px-4 py-2 rounded-md text-white font-semibold ${resultPayment ? 'bg-green-500 hover:bg-green-600' : 'bg-red-500 hover:bg-red-600'
                } transition-colors duration-300`}
              onClick={handleButtonClick}
            >
              {resultPayment ? "Quay lại trang chủ" : "Thử lại"}

            </Button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default PaymentResult
