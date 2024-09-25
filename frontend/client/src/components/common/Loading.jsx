import React, { memo } from 'react'

const Loading = () => {
    return (
        <div className="flex justify-center items-center h-screen">
            <div className="flex flex-col items-center space-y-4">
                <div className="animate-spin rounded-full h-16 w-16 border-t-4 border-rose-500"></div>
                <p className="text-rose-500 text-lg font-semibold">Đang tải...</p>
            </div>
        </div>
    )
}

export default memo(Loading)