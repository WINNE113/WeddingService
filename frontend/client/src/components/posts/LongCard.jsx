import { formatMoney, formatVietnameseToString } from "@/ultils/fn"
import path from "@/ultils/path"
import clsx from "clsx"
import moment from "moment"
import React from "react"
import { Link } from "react-router-dom"
import { twMerge } from "tailwind-merge"
import { Button } from ".."
import { Booking } from ".."
import { useDispatch, useSelector } from "react-redux"
import { MdForwardToInbox } from "react-icons/md"
import { MdOutlinePriceChange } from "react-icons/md";
import { apiAddToRequestForQuotation } from "@/apis/service"
import { toast } from "react-toastify"
import { modal } from "@/redux/appSlice"
import { getRequestForQuotaion, getWishlist } from "@/redux/action"
import { FaPlus } from "react-icons/fa";


const LongCard = ({
  image,
  title,
  address,
  minPrice,
  maxPrice,
  createdDate,
  hideImage,
  containerClassName,
  id,
}) => {
  const dispatch = useDispatch()
  const { current, wishlist, requestForQuotation } = useSelector((s) => s.user)

  const handleAddToRequestForQuotation = async () => {
    if (!current) return toast.warn("Bạn phải đăng nhập trước.")
    const response = await apiAddToRequestForQuotation({ serviceId: id })
    if (response?.success) {
      toast.success(response?.message)
      dispatch(getRequestForQuotaion())
    } else toast.error(response?.message)
  }

  return (
    <div
      className={twMerge(
        clsx("w-full grid grid-cols-10 rounded-md border", containerClassName)
      )}
    >
      {!hideImage && (
        <img
          src={image}
          alt="avatar"
          className="w-full col-span-2 h-[156px] object-cover p-2 rounded-tl-md rounded-bl-md"
        />
      )}
      <div
        className={clsx(
          "p-3 flex flex-col gap-1 w-full",
          hideImage ? "col-span-10" : "col-span-8"
        )}
      >
        <div className="flex justify-between items-center">
          <span className="text-sm text-gray-500 font-semibold">

            {address?.split(",")[address?.split(",")?.length - 1]}
          </span>
          <span
            onClick={handleAddToRequestForQuotation}
            className="text-white bg-pink-500 hover:bg-pink-600 p-2 rounded-md cursor-pointer"
          >
            <FaPlus size={14} />
          </span>
        </div>
        <Link
          className="text-fuchsia-950 cursor-pointer hover:underline font-semibold line-clamp-2"
          to={`/${path.DETAIL_POST}/${id}/${formatVietnameseToString(title)}`}
        >
          {title}
        </Link>
        <span className="text-sm text-gray-500">{address}</span>
        {minPrice !== 0 && maxPrice !== 0 ? (
          <div className="flex text-pink-800 text-lg justify-start items-center">
            <MdOutlinePriceChange size={20} />
            <span className="ml-2">{formatMoney(minPrice)} - {formatMoney(maxPrice)} VND</span>
          </div>
        ) : (
          <div className="flex text-pink-500 text-lg justify-start items-center">
            <MdOutlinePriceChange size={20} />
            <span className="ml-2">Liên hệ để biết giá</span>
          </div>
        )}
        <div className="mt-3 flex justify-between items-center">
          <Button
            onClick={() =>
              dispatch(
                modal({
                  isShowModal: true,
                  modalContent: <Booking id={id} />,
                })
              )
            }
            className="bg-pink-500"
          >
            <MdForwardToInbox size={22} />
            Nhận báo giá
          </Button>
          <span className="text-gray-500 text-sm">
            {moment(createdDate).format("DD/MM/YYYY")}
          </span>
        </div>
      </div>
    </div>
  )
}

export default LongCard
