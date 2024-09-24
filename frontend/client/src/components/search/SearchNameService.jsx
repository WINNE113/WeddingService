import React, { useEffect, useState } from "react";
import withBaseTopping from "@/hocs/WithBaseTopping";
import { useForm } from "react-hook-form"
import useDebounce from "@/hooks/useDebounce";
import { apiGetServiceByFuzzySearch } from "@/apis/service";
import path from "@/ultils/path"
import { formatVietnameseToString } from "@/ultils/fn"
import { Link } from "react-router-dom";
import { modal } from "@/redux/appSlice";
import { Button } from "..";
const SearchNameService = ({ getTitle, dispatch }) => {

    const { setValue, watch, register } = useForm()
    const [titleService, setTitleService] = useState([])


    const keyword = watch("keyword")

    const debounceValue = useDebounce(keyword, 500)

    const fetchServices = async (params) => {
        try {
            const response = await apiGetServiceByFuzzySearch({ approximateServiceTitle: params });
            if (response) setTitleService(response.data);
            else setTitleService([]);
        } catch (error) {
            console.error("Error fetching services:", error);
            setTitleService([]);
        }
    };

    useEffect(() => {
        if (debounceValue) {
            fetchServices(debounceValue);
        }
    }, [debounceValue])

    return (
        <div
            onClick={(e) => e.stopPropagation()}
            className="w-full max-w-[650px] bg-white rounded-md p-4"
        >
            <h1 className="text-lg font-bold tracking-tight pb-4 border-b">Tìm kiếm nhanh theo tên dịch vụ</h1>
            <div className="my-6 flex flex-col gap-4">
                <input
                    {...register("keyword")}
                    type="text"
                    value={keyword}
                    onChange={(e) => setValue("keyword", e.target.value)}
                    className="border rounded p-2 w-full"
                    placeholder="Nhập tên dịch vụ"
                />
                <div className="mt-2">
                    <div className="mt-3 bg-white">
                        {titleService.map((option) => (
                            <div className="border shadow-md mt-2 mb-2 p-2">
                                <Link
                                    to={`/${path.DETAIL_POST}/${option.serviceId}/${formatVietnameseToString(option.title)}`}
                                    className="text-fuchsia-950 text-lg cursor-pointer hover:underline font-semibold line-clamp-2"
                                    onClick={() => dispatch(modal({ isShowModal: false, modalContent: null }))}
                                >
                                    {option.title}

                                </Link>
                            </div>
                        ))}
                    </div>
                </div>
                <div className="mt-4 flex justify-center">
                    <Button
                        onClick={() => {
                            dispatch(modal({ isShowModal: false, modalContent: null }));
                            getTitle(keyword);
                        }}
                    >
                        Submit
                    </Button>
                </div>
            </div>
        </div>
    );
};

export default withBaseTopping(SearchNameService);
