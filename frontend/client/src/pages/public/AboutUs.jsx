import React from "react";

const AboutUs = () => {
  return (
    <div className="bg-gray-100">
      {/* Background */}
      <div 
        id="home-background" 
        className="relative bg-cover bg-center h-80" 
        style={{ backgroundImage: "url('https://tranhoangkhang1212.github.io/travelix/assets/images/Turkey-Turkish-Cappadocia-hot-air-balloons-1149868-wallhere.com.jpg')" }}
      >
        <div className="absolute inset-0 bg-black opacity-40"></div>
        <div className="relative z-10 flex items-center justify-center h-full text-center text-pink-500">
          <h1 className="text-4xl font-extrabold mb-4">Giới thiệu về chúng tôi</h1>
        </div>
      </div>

      {/* Introduction */}
      <div className="flex flex-col md:flex-row p-8 max-w-6xl mx-auto">
        <div className="flex-1 mb-8 md:mb-0 md:pr-4">
          <img 
            src="https://7799wedding.vn/data/media/2458/images/tiec-cuoi-ngoai-troi%20(8).jpg" 
            alt="Resort" 
            className="w-full h-auto object-cover rounded-lg shadow-lg"
          />
        </div>
        <div className="flex-1 flex flex-col justify-center md:pl-4">
          <h2 className="text-3xl font-semibold mb-4">Dịch vụ của chúng tôi</h2>
          <p className="text-gray-700 mb-4">
            Chúng tôi cung cấp một nền tảng hoàn hảo để bạn có thể đăng tin về các dịch vụ tiệc cưới của mình. Với các tính năng quản lý tin đăng hiệu quả, bạn có thể dễ dàng theo dõi và cập nhật thông tin của mình.
          </p>
          <p className="text-gray-700">
            Chúng tôi cam kết cung cấp dịch vụ tốt nhất và tạo điều kiện thuận lợi cho việc tìm kiếm và lựa chọn dịch vụ tiệc cưới của bạn. Hãy cùng khám phá và trải nghiệm dịch vụ của chúng tôi ngay hôm nay.
          </p>
        </div>
      </div>

      <div className="flex flex-col md:flex-row p-8 bg-gray-200 max-w-6xl mx-auto rounded-lg shadow-md">
        <div className="flex-1 mb-8 md:mb-0 md:pr-4">
          <img 
            src="https://weddingplanner.vn/dichvu/wp-content/uploads/2014/08/VNWP-Tim-Hieu-Nghe-Wedding-Planner-O-Viet-Nam-01.jpg" 
            alt="Service" 
            className="w-full h-auto object-cover rounded-lg shadow-lg"
          />
        </div>
        <div className="flex-1 flex flex-col justify-center md:pl-4">
          <h2 className="text-3xl font-semibold mb-4">Dịch vụ quản lý tin đăng</h2>
          <p className="text-gray-700 mb-4">
            Hãy sử dụng các công cụ của chúng tôi để quản lý tin đăng của bạn một cách dễ dàng và hiệu quả. Bạn có thể thêm, chỉnh sửa và xóa tin đăng một cách linh hoạt, đồng thời theo dõi hiệu quả của các tin đăng để cải thiện dịch vụ của mình.
          </p>
          <p className="text-gray-700">
            Chúng tôi cung cấp một giao diện thân thiện và dễ sử dụng để bạn có thể dễ dàng cập nhật và quản lý dịch vụ của mình. Chúng tôi luôn sẵn sàng hỗ trợ bạn trong quá trình sử dụng dịch vụ của chúng tôi.
          </p>
        </div>
      </div>

    </div>
  );
};

export default AboutUs;
