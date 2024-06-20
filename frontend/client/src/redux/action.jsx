import { apiGetCurrent, apiGetWishlist } from "@/apis/user"
import { createAsyncThunk } from "@reduxjs/toolkit"


export const getCurrent = createAsyncThunk(
  "user/current",
  async (data, { rejectWithValue }) => {
    const response = await apiGetCurrent()
    // if (!response) return rejectWithValue(response)
    return response
  }
)

export const getWishlist = createAsyncThunk(
  "user/wishlist",
  async (data, { rejectWithValue }) => {
    const response = await apiGetWishlist({
      page: 0,
      size: 100,
      wishListName: "POST",
    })
    if (!response) return rejectWithValue(null)
    return response || []
  }
)