import React, { memo, useEffect, useRef, useState } from "react"
import { Link, NavLink, useSearchParams } from "react-router-dom"
import { useSelector, useDispatch } from "react-redux"
import { logout } from "@/redux/userSlice"
import withBaseTopping from "@/hocs/WithBaseTopping"
import path from "@/ultils/path"
import { menuColors } from "@/ultils/constant"
import clsx from "clsx"
import { modal, resetFilter } from "@/redux/appSlice"
import { AiOutlineHeart } from "react-icons/ai"
import { MdForwardToInbox } from "react-icons/md"
import Button from "../common/Button"
import Swal from "sweetalert2"
import { formatMoney } from "@/ultils/fn"
import { apiGetServiceType } from "@/apis/service"
import Dropdown from "../dropdown/Dropdown"
import { VerifyPhone } from ".."

const activedStyle = "text-sm flex gap-2 items-center px-4 py-3 rounded-full border border-white"
const notActivedStyle = "text-sm flex gap-2 items-center px-4 py-3 rounded-full border border-pink-400 hover:border-white"

const Navigation = ({ navigate }) => {
  const [params] = useSearchParams()
  const [isShowOptions, setIsShowOptions] = useState(false)
  const [hoveredMenu, setHoveredMenu] = useState(null)
  const timeoutRef = useRef(null)
  const [menu, setMenu] = useState([
    {
      path: "/danh-sach",
      name: "DỊCH VỤ CƯỚI",
      id: "dichvucuoi",
      dropdownItems: [] // Khởi tạo dropdownItems là một mảng rỗng
    },
    {
      path: "/danh-sach/?type=" + path.TIMOGHEP,
      name: "KINH NGHIỆM & Ý TƯỞNG",
      id: "timoghep",
    },
    {
      path: "/" + path.PRICING,
      name: "BẢNG GIÁ GÓI",
      id: "banggiadichvu",
    },
    {
      path: "/" + path.ABOUT_US,
      name: "Giới thiệu",
      id: "gioithieu",
    },
  ])

  const { current, wishlist, requestForQuotation} = useSelector((state) => state.user)
  const dispatch = useDispatch()


  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await apiGetServiceType()
        const updatedMenu = menu.map((item) => {
          if (item.id === "dichvucuoi") {
            return {
              ...item,
              dropdownItems: response.data.map((apiItem) => ({
                name: apiItem.name,
                path: `/${path.LIST}?service_type_id=${apiItem.id}`,
                iconURL: apiItem.iconURL
              })),
            }
          }
          return item
        })
        setMenu(updatedMenu)
      } catch (error) {
        console.error('Error fetching data:', error)
      }
    }

    fetchData()
  }, [])

  const handleMouseEnter = (id) => {
    clearTimeout(timeoutRef.current)
    setHoveredMenu(id)
  }

  const handleMouseLeave = () => {
    timeoutRef.current = setTimeout(() => {
      setHoveredMenu(null)
    }, 200)
  }

  const handleShowOptions = (e) => {
    e.stopPropagation()
    setIsShowOptions(prev => !prev)
  }

  useEffect(() => {
    const handleOffOptionsExternalClick = (e) => {
      const optionsElm = document.getElementById("options")
      if (optionsElm && optionsElm.contains(e.target)) {
        setIsShowOptions(true)
      } else {
        setIsShowOptions(false)
      }
    }
    window.addEventListener("click", handleOffOptionsExternalClick)

    return () => {
      window.removeEventListener("click", handleOffOptionsExternalClick)
    }
  }, [])

  const handleClickCreatePost = (pathname) => {
    if (current?.roles?.some((el) => el.name === "ROLE_SUPPLIER")) {
      navigate(pathname)
    } else {
      Swal.fire({
        icon: "info",
        title: "Oops!",
        text: "Bạn phải xác minh SĐT mới được truy cập",
        showCancelButton: true,
        confirmButtonText: "Đi xác minh",
        cancelButtonText: "Bỏ qua",
      }).then((rs) => {
        if (rs.isConfirmed) {
          dispatch(modal({ isShowModal: true, modalContent: <VerifyPhone /> }))
        }
      })
    }
  }

  return (
    <div className="flex bg-white py-6 justify-center border-b-2 border-pink-200">
      <div className="w-main flex flex-col gap-4">
        <div className="flex justify-between items-center">
          <Link className="text-3xl text-pink-300 font-bold" to={"/"}>
            SweetDream
          </Link>
          <div className="flex items-center gap-4">
            <div className="flex gap-2">
              {!current ? (
                <>
                  <button
                    onClick={() => navigate(`/${path.LOGIN}`, { state: "LOGIN" })}
                    className="bg-gray-100 text-pink-300 rounded-md px-6 py-2 text-sm font-medium"
                  >
                    Đăng nhập
                  </button>
                  <button
                    onClick={() => navigate(`/${path.LOGIN}`, { state: "REGISTER" })}
                    className="bg-gray-100 text-pink-300 rounded-md px-6 py-2 text-sm font-medium"
                  >
                    Đăng ký
                  </button>
                </>
              ) : (
                <>
                  {current?.roles?.some((el) => el.name === "ROLE_CUSTOMER") && (
                    <Link
                      to={`/${path.MEMBER}/${path.REQUEST_FOR_QUOTATION}`}
                      className="flex items-center gap-2 text-pink-400 rounded-md px-6 py-2 text-sm font-medium"
                    >
                      <span className="relative">
                        {requestForQuotation?.count > 0 && (
                          <span className="absolute -top-2 -right-2 flex items-center justify-center w-3 h-3 text-[8px] text-pink-400 bg-red-500 border border-white rounded-full">
                            {requestForQuotation.count}
                          </span>
                        )}
                        <MdForwardToInbox size={22} />
                      </span>
                      Danh sách yêu cầu báo giá
                    </Link>
                  )}
                  {current?.roles?.some((el) => el.name === "ROLE_CUSTOMER") && (
                    <Link
                      to={`/${path.MEMBER}/${path.WISHLIST}`}
                      className="flex items-center gap-2 text-pink-400 rounded-md px-6 py-2 text-sm font-medium"
                    >
                      <span className="relative">
                        {wishlist?.length > 0 && (
                          <span className="absolute -top-2 -right-2 flex items-center justify-center w-3 h-3 text-[8px] text-pink-400 bg-red-500 border border-white rounded-full">
                            {wishlist.length}
                          </span>
                        )}
                        <AiOutlineHeart size={22} />
                      </span>
                      Yêu thích
                    </Link>
                  )}
                  <Button
                    onClick={() => handleClickCreatePost(`/${path.SUPPLIER}/${path.INFORMATION_SUPPLIER}`)}
                    className="bg-gradient-to-r from-main-rose to-main-pink text-white rounded-md px-6 py-2 text-sm font-medium flex items-center gap-2 border"
                  >
                    Trở Thành Nhà Cung Cấp
                  </Button>
                  {current?.roles?.some((el) => el.name === "ROLE_SUPPLIER") && (
                    <Link
                      to={`/${path.SUPPLIER}/${path.DEPOSIT}`}
                      className="bg-gradient-to-r from-pink-400 to-pink-600 text-white rounded-md px-6 py-2 text-sm font-medium flex items-center gap-2 border"
                    >
                      Nạp tiền
                    </Link>
                  )}
                  <div
                    onClick={handleShowOptions}
                    className="relative flex items-center cursor-pointer gap-2"
                  >
                    {isShowOptions && (
                      <div
                        id="options"
                        className="absolute flex flex-col min-w-[150px] w-fit z-50 top-full right-0 bg-white rounded-md border text-gray-800 shadow-lg"
                      >
                        {current?.roles?.some(el => el.name === "ROLE_CUSTOMER") && (
                          <Link
                            to={`/${path.MEMBER}/${path.PERSONAL}`}
                            className="p-3 hover:bg-gray-100 hover:text-emerald-600 font-medium"
                          >
                            Thông tin cá nhân
                          </Link>
                        )}
                        {current?.roles?.some(el => el.name === "ROLE_SUPPLIER") && (
                          <Link
                            to={`/${path.SUPPLIER}/${path.INFORMATION_SUPPLIER}`}
                            className="p-3 hover:bg-gray-100 hover:text-emerald-600 font-medium"
                          >
                            Quản lý dịch vụ
                          </Link>
                        )}
                        {current?.roles?.some(el => el.name === "ROLE_ADMIN") && (
                          <Link
                            to={`/${path.ADMIN}/${path.DASHBOARD}`}
                            className="p-3 hover:bg-gray-100 hover:text-emerald-600 font-medium"
                          >
                            Admin
                          </Link>
                        )}
                        <span
                          onClick={() => dispatch(logout())}
                          className="p-3 hover:bg-gray-100 hover:text-emerald-600 font-medium cursor-pointer"
                        >
                          Đăng xuất
                        </span>
                      </div>
                    )}
                    <span className="text-pink-400 text-sm flex flex-col">
                      <span className="font-bold">{current?.userName}</span>
                      <span>{`TK chính: ${formatMoney(+current?.balance)} VND`}</span>
                    </span>
                    <img
                      src={current?.profileImage || "/user.svg"}
                      alt="avatar"
                      className="w-12 h-12 object-cover rounded-full border"
                    />
                  </div>
                </>
              )}
            </div>
          </div>
        </div>
        <div className="flex items-center gap-4 text-pink-300 relative">
          {menu.map((el) => (
            <div
              key={el.id}
              onMouseEnter={() => handleMouseEnter(el.id)}
              onMouseLeave={handleMouseLeave}
              className="relative"
            >
              <NavLink
                to={el.path}
                onClick={() => dispatch(resetFilter(true))}
                className={({ isActive }) =>
                  clsx(
                    params.get("type") === el.type ? activedStyle : notActivedStyle,
                    !params.get("type") && isActive && activedStyle
                  )
                }
              >
                {el.name}
              </NavLink>
              {el.dropdownItems && hoveredMenu === el.id && (
                <Dropdown
                  items={el.dropdownItems}
                  onMouseEnter={() => handleMouseEnter(el.id)}
                  onMouseLeave={handleMouseLeave}
                />
              )}
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}

export default withBaseTopping(memo(Navigation))
