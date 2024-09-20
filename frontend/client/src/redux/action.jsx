import { apiGetCurrent, apiGetWishlist, apiGetRequestForQuotation } from "@/apis/user"
import { apiGetProvinces } from "@/apis/app"
import { createAsyncThunk } from "@reduxjs/toolkit"
import { data } from "autoprefixer"


export const getCurrent = createAsyncThunk(
  "user/current",
  async (data, { rejectWithValue }) => {
    const response = await apiGetCurrent()
    if (!response) return rejectWithValue(response)
    return response
  }
)

export const getProvinces = createAsyncThunk(
  "app/provinces",
  async (data, { rejectWithValue }) => {
    const response = await apiGetProvinces()
    if (!response.status === 200) return rejectWithValue(response)
    return response.data || []
  }
)

export const getWishlist = createAsyncThunk(
  "user/wishlist",
  async (data, { rejectWithValue }) => {
    const response = await apiGetWishlist({
      page: 0,
      size: 100,
      wishListName: "service",
    })
    if (!response) return rejectWithValue(null)
    return response || []
  }
)

export const getRequestForQuotaion = createAsyncThunk(
  "user/requestForQuotation", // Name of action
  async (data, { rejectWithValue }) => {
    const response = await apiGetRequestForQuotation({
      page: 0,
      size: 100,
    })
    if (!response) return rejectWithValue(null)
    return response || []
  }
)