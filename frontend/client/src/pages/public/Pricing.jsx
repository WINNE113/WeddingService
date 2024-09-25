import { apiGetPricings, apiSubcribePricing } from "@/apis/pricing"
import { Button, VerifyPhone } from "@/components"
import { modal } from "@/redux/appSlice"
import { formatMoney } from "@/ultils/fn"
import React, { useEffect, useState } from "react"
import { useDispatch, useSelector } from "react-redux"
import { toast } from "react-toastify"
import { FaCheck } from "react-icons/fa"
import { MdOutlineCheckCircle } from "react-icons/md"
import clsx from "clsx"
import { getCurrent } from "@/redux/action"
import Swal from "sweetalert2"
import { NavLink, useNavigate } from "react-router-dom"
import path from "@/ultils/path"
import { CiStickyNote } from "react-icons/ci";


const PricingItem = ({
  name,
  description,
  price,
  durationDays,
  servicePackageId,
  serviceLimit,
  isDisabled
}) => {
  const dispatch = useDispatch()
  const { current } = useSelector((s) => s.user)
  const navigate = useNavigate();
  const handleSubcribe = async () => {
    if (current == null) {
      const toLogin = await Swal.fire({
        icon: "info",
        title: "Xác nhận thao tác",
        text: `Bạn phải đăng nhập trước để mua gói dịch vụ ${name}.`,
        showCancelButton: true,
        showConfirmButton: true,
        confirmButtonText: "Đăng nhập",
        cancelButtonText: "Quay lại",
      });
      if (toLogin.isConfirmed) {
        navigate("/" + path.LOGIN)
        return;
      }
      return;
    }
    const result = await Swal.fire({
      icon: "info",
      title: "Xác nhận thao tác",
      text: `Bạn có chắc muốn đăng ký gói dịch vụ ${name} không?`,
      showCancelButton: true,
      showConfirmButton: true,
      confirmButtonText: "Đăng ký",
      cancelButtonText: "Quay lại",
    });

    if (result.isConfirmed) {
      if (current?.phoneNumberConfirmed) {
        try {
          const response = await apiSubcribePricing({ servicePackageId });

          if (response.success) {
            toast.success(response.message);
            dispatch(getCurrent());
          } else if (response.message === "Nhà cung cấp không tồn tại!") {
            window.location.href = `/${path.SUPPLIER}/${path.INFORMATION_SUPPLIER}`;
          } else {
            toast.error(response.message);
          }
        } catch (error) {
          toast.error("Có lỗi xảy ra, vui lòng thử lại sau.");
        }
      } else {
        dispatch(modal({ isShowModal: true, modalContent: <VerifyPhone /> }));
      }
    }
  };


  // Đảm bảo giá trị boolean cho thuộc tính disabled
  // const isDisabled = current?.servicePackageUsed === name;
  return (
    <div className="col-span-1 mb-[150px] transition-transform duration-300 ease-in-out transform hover:scale-105 hover:shadow-lg">
      <h3
        className={clsx(
          "text-center p-4 border border-pink-500 bg-pink-700 text-white font-semibold rounded-t-md transition-colors duration-300",
          current?.servicePackageUsed === name &&
          "bg-green-500 border-green-700"
        )}
      >
        {name}
      </h3>
      <div
        className={clsx(
          "flex rounded-b-md border h-[260px] border-emerald-500 flex-col gap-2 justify-between items-center py-3 transition-transform duration-300 ease-in-out",
          current?.servicePackageUsed === name && "border-green-700"
        )}
      >
        <div className="flex flex-col gap-2 items-center">
          <span>
            <span>Số ngày áp dụng:</span>{" "}
            <span className="text-emerald-700 font-semibold">
              {durationDays + " ngày"}
            </span>
          </span>
          <span>
            <span>Giới hạn hiển thị dịch vụ:</span>{" "}
            <span className="text-emerald-700 font-semibold">
              {serviceLimit + " dịch vụ"}
            </span>
          </span>
          <span>
            <span>Giá:</span>{" "}
            <span className="text-emerald-700 font-semibold">
              {formatMoney(price) + " VNĐ"}
            </span>
          </span>
          <p className="text-sm italic my-3 px-4 line-clamp-5">{description}</p>
        </div>
        <div className="w-full px-4">
          {current?.servicePackageUsed === name ? (
            <div className="flex items-center py-2 px-4 rounded-md justify-center bg-green-700 text-white gap-2">
              <MdOutlineCheckCircle size={22} />
              Đã đăng ký
            </div>
          ) : (
            <Button
              onClick={handleSubcribe}
              className="bg-transparent text-emerald-700 border rounded-md border-emerald-700 py-2 w-full hover:bg-emerald-700 hover:text-white transition-colors duration-300"
              disabled={isDisabled}
            >
              Đăng ký
            </Button>
          )}
        </div>
      </div>
    </div>
  )
}

const Pricing = () => {
  const [pricings, setPricings] = useState([])
  const { current } = useSelector((s) => s.user)

  const fetchPricing = async () => {
    const response = await apiGetPricings({ page: 0, limit: 100 })
    if (response.data) setPricings(response.data)
  }
  useEffect(() => {
    fetchPricing()
  }, [])

  const benefits = [
    {
      name: 'Nổi bật',
      description: 'Dịch vụ của bạn sẽ được xuất hiện ở trang chủ của chúng tôi'
    },
    {
      name: 'Hiệu quả chi phí',
      description: 'Các gói dịch vụ được hỗ trợ đặc biệt về chi phí khi mua'
    },
    {
      name: 'Nhiều tính năng',
      description: 'Cung cấp nhiều tính năng nổi bật để dịch vụ của bạn dễ hàng tiếp canh người dùng'

    },
    {
      name: 'Hỗ trợ đáng tin cậy',
      description: 'Hỗ trợ chuyên gia khi bạn cần'
    },
  ]
  return (
    <div className="mx-auto w-main py-8 ">
      <header className="page-header category clearfix">
        <h1
          className="page-h1 text-4xl font-bold text-center text-pink-400 mt-12 mb-8 transition-transform duration-500 transform hover:scale-105 "
         
        >
          Nâng Tầm Tin Đăng Của Bạn với Các Gói VIP
        </h1>
      </header>
      <section>
        <div className="text-center mb-12 bg-gradient-to-b from-pink-50 to-white">
          <h2 className="text-4xl font-bold text-pink-400 mb-4">Nhận mức giá hấp dẫn tại đây</h2>
          <p className="text-xl text-gray-600 max-w-3xl mx-auto">
            Chọn gói dịch vụ hoàn hảo cho nhu cầu của bạn. Cho dù bạn mới bắt đầu hay đang điều hành một doanh nghiệp lớn, chúng tôi đều có gói dịch vụ phù hợp với bạn.
          </p>
        </div>
        <div className="bg-white rounded-lg shadow-lg p-8 mb-12">
          <h3 className="text-2xl font-bold mb-6">Tại sao chọn gói dịch vụ của chúng tôi??</h3>
          <span className="mb-8">
            Các gói dịch vụ được chúng tôi nghiên cứu cẩn thận nhằm mang lại cho bạn giá trị tốt nhất cho dịch vụ của bạn. Sau đây là những gì bạn có thể mong đợi:
          </span>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8 mt-4">
            {benefits.map((benefit) => (
              <div key={benefit.name} className="flex items-start">
                <CiStickyNote className="h-6 w-6 text-blue-500 mr-3 flex-shrink-0" aria-hidden="true" />
                <div>
                  <h4 className="font-semibold mb-1">{benefit.name}</h4>
                  <p className="text-gray-600">{benefit.description}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>
      <h1
        className="page-h1 text-2xl font-bold text-pink-400"
        style={{
          float: "none",
          marginTop: "50px",
          marginBottom: "30px",
          textAlign: "center",
          fontSize: "2em",
        }}
      >
        Bảng Giá Dịch Vụ
      </h1>
      <div className="mt-4 grid grid-cols-3 gap-3">
        {pricings?.map((el) => (
          <PricingItem
            key={el.servicePackageId}
            {...el}
            isDisabled={!!current?.servicePackageUsed && current?.servicePackageUsed !== el.name} // Disable nếu đã đăng ký một gói khác
          />
        ))}
      </div>
    </div>
  )
}

export default Pricing
