import React from 'react'
import { FaArrowRight, FaPencilAlt, FaTrashAlt, FaChartBar } from 'react-icons/fa'

export default function AboutPage() {
  return (
    <div className="min-h-screen bg-gradient-to-b from-pink-50 to-white">
      <main className="container mx-auto px-4 py-16 sm:px-6 lg:px-8">
        <section className="mb-16 text-center">
          <h1 className="mb-4 text-4xl font-extrabold tracking-tight text-pink-400 sm:text-5xl">Về dịch vụ cưới của chúng tôi</h1>
          <p className="mx-auto max-w-2xl text-xl text-pink-300">
            Chúng tôi cung cấp một nền tảng hoàn hảo để bạn đăng dịch vụ cưới của mình. Với các tính năng quản lý đăng bài hiệu quả, bạn có thể dễ dàng theo dõi và cập nhật thông tin của mình.          </p>
        </section>

        <section className="mb-16">
          <h2 className="mb-6 text-3xl font-bold text-gray-900">Our Commitment</h2>
          <p>Chúng tôi cam kết cung cấp dịch vụ tốt nhất và tạo điều kiện thuận lợi cho việc tìm kiếm và lựa chọn dịch vụ cưới của bạn. Hãy cùng khám phá và trải nghiệm dịch vụ của chúng tôi ngay hôm nay.</p>
          <a href="#services" className="inline-flex items-center text-pink-600 hover:text-pink-700">
            Khám phá dịch vụ của chúng tôi
            <FaArrowRight className="ml-2 h-5 w-5" />
          </a>
        </section>
        <section id="services" className="mb-16">
          <h2 className="mb-8 text-3xl font-bold text-gray-900">Dịch vụ của chúng tôi</h2>
          <div className="grid gap-8 md:grid-cols-2 lg:grid-cols-3">
            <ServiceCard
              title="Quản lý dịch vụ bài viết"
              description="Thêm, chỉnh sửa và xóa bài đăng một cách linh hoạt để dịch vụ của bạn luôn được cập nhật."
              icon={<FaPencilAlt className="h-8 w-8 text-pink-500" />}
            />
            <ServiceCard
              title="Cập nhật dễ dàng"
              description="Giao diện thân thiện với người dùng để cập nhật dịch vụ nhanh chóng và dễ dàng."
              icon={<FaTrashAlt className="h-8 w-8 text-pink-500" />}
            />
            <ServiceCard
              title="Theo dõi hiệu suất"
              description="Theo dõi hiệu quả bài đăng của bạn để cải thiện dịch vụ của bạn."
              icon={<FaChartBar className="h-8 w-8 text-pink-500" />}
            />
          </div>
        </section>

        <section className="rounded-lg bg-pink-100 p-8">
          <h2 className="mb-4 text-2xl font-bold text-gray-900">Sẵn sàng để bắt đầu chưa?</h2>
          <p className="mb-6 text-lg text-gray-600">
            Hãy tham gia nền tảng của chúng tôi ngay hôm nay và bắt đầu giới thiệu dịch vụ cưới của bạn tới nhiều đối tượng hơn.
          </p>
          <a
            href="/login"
            className="inline-block rounded-md bg-pink-600 px-6 py-3 text-base font-medium text-white shadow-sm hover:bg-pink-700"
          >
            Đăng kí ngay
          </a>
        </section>
      </main>
    </div>
  )
}

function ServiceCard({ title, description, icon }) {
  return (
    <div className="rounded-lg bg-white p-6 shadow-md">
      <div className="mb-4">{icon}</div>
      <h3 className="mb-2 text-xl font-semibold text-gray-900">{title}</h3>
      <p className="text-gray-600">{description}</p>
    </div>
  )
}