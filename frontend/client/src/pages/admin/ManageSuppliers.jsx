import { Button, Pagination, SelectLib, Title } from "@/components"
import withBaseTopping from "@/hocs/WithBaseTopping"
import moment from "moment"
import React, { useEffect, useState } from "react"
import { useForm } from "react-hook-form"
import { AiFillDelete, AiFillStar, AiOutlineEdit } from "react-icons/ai"
import { useSearchParams } from "react-router-dom"
import { toast } from "react-toastify"
import Swal from "sweetalert2"
import useDebounce from "@/hooks/useDebounce"
import { useSelector } from "react-redux"
import { apiSuppliersByAdmin, apiUpdateStatusSuppliersByAdmin, apiSuppliersByStatus } from "@/apis/supplier"
import path from "@/ultils/path"
import { stars, statuses } from "@/ultils/constant"
import { modal } from "@/redux/appSlice"
import { DetailImages } from "@/components"

const ManageSuppliers = ({ dispatch, navigate }) => {
  const { setValue, watch } = useForm()
  const keyword = watch("keyword")
  const status = watch("status")
  const updateStatus = watch("updateStatus")
  const [searchParams] = useSearchParams()
  const [update, setUpdate] = useState(false)
  const [editSupplier, setEditSupplier] = useState()
  const [suppliers, setSuppliers] = useState([])
  const [count, setCount] = useState(0)

  // Fetch supplier data
  const fetchSupplier = async (params) => {
    try {
      const response = await apiSuppliersByAdmin(params)
      if (response) {
        setSuppliers(response)
        setCount(response.count)
      }
      else setSuppliers([])
    } catch (error) {
      console.error("Error fetching suppliers:", error)
      toast.error("Lỗi khi tải nhà cung cấp")
    }
  }
  const fetchSupplierByStatus = async () => {
    try {
      const response = await apiSuppliersByStatus({
        status: status.value
      })
      if (response) {
        setSuppliers(response)
        setCount(response.count)
      }
      else setSuppliers([])
    } catch (error) {
    }
  }
  // Định nghĩa hàm render, được gọi khi cần cập nhật trạng thái (update).
  const render = () => {
    setUpdate(!update)
  }
  const handleDeletePost = (pid) => {

  }
  const handleChangeStatus = async () => {
    const response = await apiUpdateStatusSuppliersByAdmin({
      sid: editSupplier.id,
      status: updateStatus
    })
    if (response.success) {
      toast.success(response.message)
      render()
      setEditSupplier(null)
    } else toast.error(response.message)
  }
  useEffect(() => {
    fetchSupplierByStatus();
  }, [status, searchParams])

  useEffect(() => {
    const { page, ...searchParamsObject } = Object.fromEntries([
      ...searchParams,
    ])
    if (page && Number(page)) searchParamsObject.page = Number(page) - 1
    else searchParamsObject.page = 0
    searchParamsObject.limit = 5
    fetchSupplier(searchParamsObject);
  }, [update, searchParams])
  return (
    <section className="mb-[200px]">
      <Title title="Quản lý nhà cung cấp">
        <div className="flex items-center gap-4">
          {editSupplier && updateStatus && (
            <Button
              className="bg-blue-500"
              onClick={() => handleChangeStatus()}
            >
              Cập nhật
            </Button>
          )}
          {editSupplier && (
            <Button className="bg-orange-500" onClick={() => setEditSupplier(null)}>
              Hủy
            </Button>
          )}
        </div>
      </Title>
      <div className="p-4 mt-4">
        <div className="flex items-center gap-4 justify-between">
          <div className="flex items-center gap-4">
            <div className="flex items-center gap-2">
              <span>Lọc theo:</span>
              <SelectLib
                placeholder="Trạng thái"
                className="py-2"
                options={statuses}
                onChange={(val) => setValue("status", val)}
              />
            </div>
          </div>
        </div>
        <div className="mt-6 w-full">
          <table className="table-fixed w-full">
            <thead>
              <tr className="text-pink-500">
                <th className="p-2 border font-medium text-center">Mã ID</th>
                <th className="p-2 border font-medium text-center">
                  Ảnh đại diện
                </th>
                <th className="p-2 border font-medium text-center">Tên nhà cung cấp</th>
                <th className="p-2 border font-medium text-center">Số điện thoại</th>
                <th className="p-2 border font-medium text-center">Địa chỉ</th>
                <th className="p-2 border font-medium text-center">Ngày tạo</th>
                <th className="p-2 border font-medium text-center">
                  Ngày cập nhật
                </th>
                <th className="p-2 border font-medium text-center">Giấy phép kinh doanh</th>
                <th className="p-2 border font-medium text-center">
                  Trạng thái
                </th>

                <th className="p-2 border bg-pink-600 text-white font-medium text-center">
                  Hành động
                </th>
              </tr>
            </thead>
            <tbody className="text-sm">
              {suppliers?.data?.map((el) => (
                <tr className="border" key={el.id}>
                  <td className="p-2 text-center">{el.id}</td>
                  <td className="p-2 text-center">
                    <span className="flex items-center justify-center">
                      <img
                        src={el.logo}
                        className="w-24 h-24 rounded-md border p-2 object-cover"
                        alt=""
                      />
                    </span>
                  </td>
                  <td className="p-2 text-center">
                    {el.name}
                  </td>
                  <td className="p-2 text-center">
                    {el.phoneNumberSupplier}
                  </td>

                  <td className="p-2 text-center">
                    {el.addressSupplier}
                  </td>
                  <td className="p-2 text-center">
                    {moment(el.createdDate).format("DD/MM/YYYY")}
                  </td>
                  <td className="p-2 text-center">
                    {moment(el.modifiedDate).format("DD/MM/YYYY")}
                  </td>
                  <td className="p-2 text-center">
                    {
                      (el.imagesLicence && el.imagesLicence.length > 0) ? (
                        <img
                          src={el.imagesLicence[0]}
                          className="w-24 h-24 rounded-md border p-2 object-cover"
                          alt="licence supplier"
                          onClick={() => {
                            dispatch(
                              modal({
                                isShowModal: true,
                                modalContent: <DetailImages images={el.imagesLicence} />
                              })
                            )
                          }}
                        />) : (
                        <h1>Not Found</h1>
                      )
                    }
                  </td>
                  <td className="p-2 text-center">
                    {editSupplier?.id === el.id ? (
                      <select
                        onChange={(e) =>
                          setValue("updateStatus", e.target.value)
                        }
                        value={updateStatus}
                        className="form-select rounded-md"
                        id="updateStatus"
                      >
                        <option value="APPROVED">Thành công</option>
                        <option value="REVIEW">Đang xử lý</option>
                        <option value="REJECTED">Đã từ chối</option>
                      </select>
                    ) : (
                      <span>
                        {statuses.find((n) => n.value === el.statusSupplier)?.name}
                      </span>
                    )}
                  </td>
                  <td className="p-2">
                    <span className="flex w-full justify-center text-emerald-700 items-center gap-2">
                      <span
                        onClick={() => {
                          setEditSupplier(el)
                          setValue("updateStatus", el.statusSupplier)
                        }}
                        title="Chỉnh sửa"
                        className="text-lg text-rose-600 cursor-pointer px-1"
                      >
                        <AiOutlineEdit size={22} />
                      </span>
                      <span
                        onClick={() => handleDeletePost(el.id)}
                        className="text-lg text-rose-600 cursor-pointer px-1"
                        title="Xóa"
                      >
                        <AiFillDelete size={22} />
                      </span>
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <div className="mt-6">
          <Pagination totalCount={count} />
        </div>
      </div>
    </section>
  )
}

export default withBaseTopping(ManageSuppliers)
