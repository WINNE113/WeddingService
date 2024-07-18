import { customMoney, formatMoney, renderStarFromNumber } from "@/ultils/fn"
import clsx from "clsx"
import React, { useEffect, useState } from "react"
import { AiOutlineHeart, AiOutlineUnorderedList } from "react-icons/ai"
import { GoLocation } from "react-icons/go"
import { BsPhoneVibrate } from "react-icons/bs"
import { createSearchParams, useParams } from "react-router-dom"

// import { apiGetDetailPost, apiGetPosts, apiGetRatings } from "@/apis/post"

import { apiGetDetailService } from "@/apis/service"
import { apiGetRatings } from "@/apis/rating"
import { apiGetAlbumOfService } from "@/apis/service"

import moment from "moment"
import DOMPurify from "dompurify"
import { apiGetLngLatFromAddress } from "@/apis/app"
import { CgSpinner } from "react-icons/cg"
import {
  BoxFilter,
  Button,
  Comments,
  DetailImages,
  LongCard,
  Map,
  Rating,
  Report,
} from "@/components"
import TypeBox from "@/components/comment/TypeBox"
import { useSelector } from "react-redux"
import WithBaseTopping from "@/hocs/WithBaseTopping"
import path from "@/ultils/path"
import { MdOutlineReportProblem } from "react-icons/md"
import { modal } from "@/redux/appSlice"
import { Slide, toast } from "react-toastify"
import { apiAddWishlist, apiRemoveWishlist } from "@/apis/user"
import { getWishlist } from "@/redux/action"
import { FaHeart, FaRegHeart } from "react-icons/fa"

const DetailPost = ({ navigate, location, dispatch }) => {
  const { pid } = useParams()
  const { isShowModal } = useSelector((s) => s.app)
  const [seeMore, setSeeMore] = useState(false)
  const [post, setPost] = useState()
  const [rating, setRating] = useState({})
  const [center, setCenter] = useState([])
  const [posts, setPosts] = useState([])
  const [albums, setAlbums] = useState([])

  const { current, wishlist } = useSelector((s) => s.user)

  const fetchDetailPost = async () => {
    const response = await apiGetDetailService({ serviceId: pid })
    console.log("detail service: " + JSON.stringify(response))
    if (response) setPost({ ...response?.postDetail, images: response?.images })
  }

  const getAlbum = async () => {
    try {
      const response = await apiGetAlbumOfService({ serviceId: pid, albName: "dream" });
      if (response && response.data) {
        setAlbums(response.data);
      } else {
        setAlbums([]);
        console.log("No data available");
      }
    } catch (error) {
      setAlbums([]);
      console.error("Error fetching album:", error);
    }
  };

  // const getPosts = async (address) => {
  //   const formdata = new FormData()
  //   formdata.append("json", JSON.stringify({ address, status: "APPROVED" }))
  //   formdata.append("size", 5)
  //   const response = await apiGetAlbumOfService(formdata)
  //   if (response) setPosts(response.data)
  //   else setPosts([])
  // }

  const fetchRating = async () => {
    const response = await apiGetRatings({ postId: pid })
    if (response) setRating(response)
    else setRating({})
  }
  const fetLngLat = async (payload) => {
    const response = await apiGetLngLatFromAddress(payload)
    if (response.status === 200)
      setCenter([
        response.data?.features[0]?.properties?.lat,
        response.data?.features[0]?.properties?.lon,
      ])
  }

  useEffect(() => {
    fetchDetailPost();
    if (!isShowModal) {
      fetchRating();
      getAlbum();
    }
  }, [pid, isShowModal]);

  useEffect(() => {
    if (post?.address) {
      fetLngLat({
        text: post?.address,
        apiKey: import.meta.env.VITE_MAP_API_KEY,
      })
      getPosts(post?.address?.split(",")[post?.address?.split(",")?.length - 1])
    }
  }, [post])
  const handleAddWishlist = async () => {
    if (!current) return toast.warn("Bạn phải đăng nhập trước.")
    const response = await apiAddWishlist({ postId: pid, wishlistName: "POST" })
    if (response.wishlistId) {
      toast.success("Thêm bài đăng yêu thích thành công")
      dispatch(getWishlist())
    } else toast.error(response.message)
  }
  const handleRemoveWishlist = async () => {
    const wishlistId = wishlist?.find((el) => +el.id === +pid)?.wishListItemId
    const response = await apiRemoveWishlist(wishlistId)
    if (response.success) {
      toast.success(response.message)
      dispatch(getWishlist())
    } else toast.error(response.message)
  }
  return (
    <div className="w-main mt-6 m-auto pb-[200px]">
      <div className="grid grid-cols-4 h-[410px] relative grid-rows-2 gap-3">
        {albums[0] && (
          <img
            onClick={() =>
              dispatch(
                modal({
                  isShowModal: true,
                  modalContent: (
                    <DetailImages currentImage={0} images={albums?.imageURL} />
                  ),
                })
              )
            }
            src={albums[0]?.imageURL}
            alt="avatar"
            className="col-span-2 w-full h-full row-span-2 object-cover cursor-pointer rounded-l-md"
          />
        )}
        {albums[1] && (
          <img
            onClick={() =>
              dispatch(
                modal({
                  isShowModal: true,
                  modalContent: (
                    <DetailImages currentImage={1} images={albums?.imageURL} />
                  ),
                })
              )
            }
            src={albums[0]?.imageURL}
            alt="avatar"
            className="col-span-1 w-full h-full row-span-1 object-cover cursor-pointer"
          />
        )}
        {albums[2] && (
          <img
            onClick={() =>
              dispatch(
                modal({
                  isShowModal: true,
                  modalContent: (
                    <DetailImages currentImage={2} images={albums?.imageURL} />
                  ),
                })
              )
            }
            src={albums[0]?.imageURL}
            alt="avatar"
            className="col-span-1 w-full h-full row-span-1 object-cover cursor-pointer rounded-tr-md"
          />
        )}
        {albums[3] && (
          <img
            onClick={() =>
              dispatch(
                modal({
                  isShowModal: true,
                  modalContent: (
                    <DetailImages currentImage={3} images={albums?.imageURL} />
                  ),
                })
              )
            }
            src={albums[0]?.imageURL}
            alt="avatar"
            className="col-span-1 w-full h-full row-span-1 object-cover cursor-pointer"
          />
        )}
        {albums[4] && (
          <img
            onClick={() =>
              dispatch(
                modal({
                  isShowModal: true,
                  modalContent: (
                    <DetailImages currentImage={4} images={albums?.imageURL} />
                  ),
                })
              )
            }
            src={albums[0]?.imageURL}
            alt="avatar"
            className="col-span-1 w-full h-full row-span-1 object-cover cursor-pointer rounded-br-md"
          />
        )}
        <div
          onClick={() =>
            dispatch(
              modal({
                isShowModal: true,
                modalContent: <DetailImages images={albums?.imageURL} />,
              })
            )
          }
          className="absolute cursor-pointer bottom-6 right-8 bg-white borer-2 rounded-md border-emerald-800 gap-2 flex items-center justify-center px-4 py-2"
        >
          <AiOutlineUnorderedList />
          <span className="text-emerald-800 font-medium">
            Hiện thị tất cả ảnh
          </span>
        </div>
      </div>
    </div>
  )
}
export default WithBaseTopping(DetailPost)
