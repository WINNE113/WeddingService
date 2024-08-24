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
  return (
    <div className="mx-auto w-main py-8">
      <header className="page-header category clearfix">
        <h1
          className="page-h1 text-2xl font-bold text-center mt-12 mb-8 transition-transform duration-500 transform hover:scale-105"
          style={{
            fontSize: "2em",
            color: "#e91e63", // Bright pink for headings
          }}
        >
          Nâng Tầm Tin Đăng Của Bạn với Các Gói VIP
        </h1>
      </header>
      <section
        className="section bg-cover bg-center p-8 rounded-lg transition-transform duration-500 hover:scale-105"
        style={{
          backgroundImage: 'url("https://matchthemes.com/demohtml/tilia/images/pages/img-about1.jpg")',
          backgroundColor: 'rgba(233, 30, 99, 0.9)', // Overlay pink color
        }}
      >
        <div className="section-content text-center">
          <p className="mb-4 text-lg leading-relaxed text-white">
            Chào mừng bạn đến với sweetdream.com - nền tảng dịch vụ cưới hỏi hàng đầu!
            Chúng tôi cung cấp các gói dịch vụ VIP để giúp tin đăng của bạn nổi bật
            trên trang chủ, thu hút được nhiều khách hàng hơn.
          </p>
          <p className="font-semibold text-lg text-white">ƯU ĐIỂM GÓI VIP:</p>
          <div className="benefit-item text-pink-200 flex items-center space-x-2">
            <FaCheck className="text-2xl" />
            <strong>Tăng Hiển Thị:</strong> Đảm bảo tin đăng của bạn luôn xuất hiện trên trang chủ.
          </div>
          <div className="benefit-item text-pink-200 flex items-center space-x-2 mt-2">
            <FaCheck className="text-2xl" />
            <strong>Tiếp Cận Nhiều Khách Hàng Hơn:</strong> Gói VIP giúp bạn tiếp cận đúng đối tượng khách hàng.
          </div>
          <div className="benefit-item text-pink-200 flex items-center space-x-2 mt-2">
            <FaCheck className="text-2xl" />
            <strong>Tùy Chọn Linh Hoạt:</strong> Chọn gói VIP phù hợp với nhu cầu và ngân sách của bạn.
          </div>
          <div className="benefit-item text-pink-200 flex items-center space-x-2 mt-2">
            <FaCheck className="text-2xl" />
            <strong>Hỗ Trợ Tận Tình:</strong> Đội ngũ hỗ trợ luôn sẵn sàng giúp đỡ bạn bất cứ lúc nào.
          </div>
        </div>
      </section>
      <h1
        className="page-h1 text-2xl font-bold"
        style={{
          float: "none",
          marginTop: "50px",
          marginBottom: "30px",
          textAlign: "center",
          fontSize: "2em",
          color: "#e91e63", // Same pink color for consistency
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
