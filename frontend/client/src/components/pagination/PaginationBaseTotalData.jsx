import React, { useState } from "react";
import PagiItemBaseTotalData from "./PagiItemBaseTotalData";
import { useSearchParams } from "react-router-dom";
import { LongCard } from "..";

const PaginationBaseTotalData = ({ data = [], itemsPerPage = 5 }) => {
    const [params, setParams] = useSearchParams();
    const [currentPage, setCurrentPage] = useState(Number(params.get("page")) || 1);
    const totalPages = Math.ceil(data.length / itemsPerPage);

    const handlePageChange = (page) => {
        const newParams = new URLSearchParams(params.toString());
        setParams({locationpage: page.toString() });
        setCurrentPage(page);
    };

    const currentData = data.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);

    return (
        <div className="w-full">
            {/* Render dữ liệu của trang hiện tại */}
            <div className="mt-6 col-span-4">
                {currentData.map((el, idx) => (
                    <LongCard key={idx} {...el} />
                ))}
            </div>
            {/* Hiển thị nội dung kết quả và các nút chuyển trang */}
            <div className="flex justify-between items-center mt-6">
                <span className="text-gray-600">{`Hiển thị kết quả ${Math.min((currentPage - 1) * itemsPerPage + 1, data.length)} - ${Math.min(currentPage * itemsPerPage, data.length)} trong số ${data.length}`}</span>
                <div className="flex items-center gap-2">
                    {Array.from({ length: totalPages }, (_, idx) => (
                        <PagiItemBaseTotalData
                            key={idx}
                            page={idx + 1}
                            onClick={() => handlePageChange(idx + 1)}
                            className={`px-3 py-1 rounded-md transition-all duration-300 ${
                                currentPage === idx + 1 
                                    ? 'bg-pink-500 text-white'  // Đổi màu khi là trang hiện tại
                                    : 'bg-white text-pink-500 border border-pink-500 hover:bg-pink-100' // Màu mặc định
                            }`}
                        >
                            {idx + 1}
                        </PagiItemBaseTotalData>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default PaginationBaseTotalData;
