import { apiAddWishlist, apiRemoveWishlist } from "@/apis/user"
import { apiAddToRequestForQuotation } from "@/apis/service"
import { getRequestForQuotaion, getWishlist } from "@/redux/action"
import { formatMoney, formatVietnameseToString } from "@/ultils/fn"
import path from "@/ultils/path"
import moment from "moment"
import React, { useEffect } from "react"
import { FaHeart, FaRegHeart, FaShoppingCart } from "react-icons/fa"
import { useDispatch, useSelector } from "react-redux"
import { Link, useParams } from "react-router-dom"
import { toast } from "react-toastify"
import { Button } from ".."
import { modal } from "@/redux/appSlice"
import { Booking } from ".."
import { MdDataExploration, MdForwardToInbox } from "react-icons/md"
import { renderStarFromNumber } from "@/ultils/fn"
import { MdOutlinePriceChange } from "react-icons/md";


const Card = ({
  image,
  title,
  address,
  createdDate,
  id,
  isLike,
  wishListItemId,
  averageRating,
  minPrice,
  maxPrice
}) => {

  console.log("Min: " + minPrice);
  const dispatch = useDispatch()
  const { current, wishlist, requestForQuotation } = useSelector((s) => s.user)
  const handleAddWishlist = async () => {
    if (!current) return toast.warn("Bạn phải đăng nhập trước.")
    const response = await apiAddWishlist({ postId: id, wishlistName: "service" })
    if (response.wishlistId) {
      toast.success("Thêm bài đăng yêu thích thành công")
      dispatch(getWishlist())
    } else toast.error(response.message)
  }
  const handleRemoveWishlist = async () => {
    const wishlistId = wishlist?.find((el) => el.id === id)?.wishListItemId
    const response = await apiRemoveWishlist(wishlistId)
    if (response.success) {
      toast.success(response.message)
      dispatch(getWishlist())
    } else toast.error(response.message)
  }

  const handleAddToRequestForQuotation = async () => {
    if (!current) return toast.warn("Bạn phải đăng nhập trước.")
    const response = await apiAddToRequestForQuotation({ serviceId: id })
    if (response?.success) {
      toast.success(response?.message)
      dispatch(getRequestForQuotaion())
    } else toast.error(response?.message)
  }

  return (
    <div className="w-full col-span-1 flex flex-col rounded-md border transition-transform duration-300 hover:scale-105 hover:shadow-xl hover:shadow-gray-500">
      <div className="w-full h-[156px] relative">
        {!isLike && (
          <span
            onClick={handleAddWishlist}
            className="absolute top-2 right-2 text-white p-1 cursor-pointer"
          >
            <FaRegHeart size={22} />
          </span>
        )}
        {isLike && (
          <span
            onClick={handleRemoveWishlist}
            className="absolute top-2 right-2 text-red-500 p-1 cursor-pointer"
          >
            <FaHeart size={22} />
          </span>
        )}
        <img
          src={image}
          alt="avatar"
          className="w-full h-full object-cover rounded-tl-md rounded-tr-md"
        />
      </div>
      <div className="p-3 flex flex-col gap-1">
        <Link
          to={`/${path.DETAIL_POST}/${id}/${formatVietnameseToString(title)}`}
          className="text-fuchsia-950 text-lg cursor-pointer hover:underline font-semibold line-clamp-2"
        >
          {title}
        </Link>
        <span className="text-sm text-gray-500">{address}</span>
        <div className="flex justify-between items-center mt-2">
          <span className="flex items-center">
            {renderStarFromNumber(averageRating)?.map((item, index) => (
              <span key={index}>{item}</span>
            ))}
          </span>
          <span
            onClick={handleAddToRequestForQuotation}
            className="text-white bg-pink-500 hover:bg-pink-600 p-2 rounded-full cursor-pointer"
          >
            <MdForwardToInbox size={22} />
          </span>
        </div>
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
            className="bg-pink-500 text-white hover:bg-pink-600 transition-colors duration-300"
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

export default Card
