<script>
  // Thêm hiệu ứng khi cuộn trang
  document.addEventListener('DOMContentLoaded', function() {
    
    // === 1. XỬ LÝ CHUYỂN TRANG ===
    // Danh sách các trang
    const pages = {
      'trang-chu': 'index.html',
      'cau-chuyen': 'cauchuyen.html',
      'dat-banh': 'datbanh.html',
      'cac-loai-banh': 'loaibanh.html'
    };
    
    // Lưu trạng thái hiện tại
    let currentPage = 'trang-chu';
    
    // Xử lý nút Trang chủ
    const homeButton = document.querySelector('.trangch-thq-button-elm1');
    if (homeButton) {
      homeButton.addEventListener('click', function(e) {
        e.preventDefault();
        navigateToPage('trang-chu');
      });
    }
    
    // Xử lý nút Câu chuyện
    const storyButton = document.querySelector('.trangch-thq-text-elm35');
    if (storyButton) {
      storyButton.addEventListener('click', function(e) {
        e.preventDefault();
        navigateToPage('cau-chuyen');
      });
    }
    
    // Xử lý nút Đặt bánh
    const orderCakeButton = document.querySelector('.trangch-thq-text-elm36');
    if (orderCakeButton) {
      orderCakeButton.addEventListener('click', function(e) {
        e.preventDefault();
        navigateToPage('dat-banh');
      });
    }
    
    // Xử lý nút Các loại bánh
    const cakeTypesButton = document.querySelector('.trangch-thq-text-elm37');
    if (cakeTypesButton) {
      cakeTypesButton.addEventListener('click', function(e) {
        e.preventDefault();
        navigateToPage('cac-loai-banh');
      });
    }
    
    // Xử lý nút Đặt hàng ngay lớn
    const mainOrderButton = document.querySelector('.trangch-thq-button-elm6');
    if (mainOrderButton) {
      mainOrderButton.addEventListener('click', function(e) {
        e.preventDefault();
        navigateToPage('dat-banh');
      });
    }
    
    // Hàm chuyển trang
    function navigateToPage(pageKey) {
      if (pages[pageKey] && currentPage !== pageKey) {
        // Hiệu ứng chuyển trang
        showPageTransition(() => {
          // Chuyển đến trang mới
          window.location.href = pages[pageKey];
        });
        currentPage = pageKey;
      }
    }
    
    // === 2. TẠO HIỆU ỨNG CHUYỂN TRANG ===
    function showPageTransition(callback) {
      // Tạo overlay chuyển trang
      const transitionOverlay = document.createElement('div');
      transitionOverlay.style.cssText = `
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: linear-gradient(135deg, #ff5c5c 0%, #ff9966 100%);
        z-index: 99999;
        display: flex;
        justify-content: center;
        align-items: center;
        opacity: 0;
        transition: opacity 0.4s ease;
      `;
      
      // Tạo hiệu ứng loading
      const loader = document.createElement('div');
      loader.style.cssText = `
        width: 80px;
        height: 80px;
        border-radius: 50%;
        border: 5px solid rgba(255, 255, 255, 0.3);
        border-top-color: white;
        animation: spin 1s linear infinite;
      `;
      
      // Thêm keyframes cho animation
      const style = document.createElement('style');
      style.textContent = `
        @keyframes spin {
          to { transform: rotate(360deg); }
        }
      `;
      document.head.appendChild(style);
      
      transitionOverlay.appendChild(loader);
      document.body.appendChild(transitionOverlay);
      
      // Hiệu ứng fade in
      setTimeout(() => {
        transitionOverlay.style.opacity = '1';
      }, 10);
      
      // Thực hiện callback sau 1 giây
      setTimeout(() => {
        if (callback) callback();
      }, 1000);
    }
    
    // === 3. GIỎ HÀNG ẢO ===
    let cartCount = 0;
    function updateCartCount() {
      cartCount++;
      // Tạo/thay đổi badge giỏ hàng
      let cartBadge = document.querySelector('.cart-badge');
      if (!cartBadge) {
        cartBadge = document.createElement('span');
        cartBadge.className = 'cart-badge';
        cartBadge.style.cssText = `
          position: absolute;
          top: -5px;
          right: -5px;
          background: #ff5c5c;
          color: white;
          border-radius: 50%;
          width: 20px;
          height: 20px;
          font-size: 12px;
          display: flex;
          align-items: center;
          justify-content: center;
          font-weight: bold;
        `;
        // Tìm vị trí để gắn badge (có thể là icon giỏ hàng nếu có)
        const nav = document.querySelector('.trangch-thq-navigation-elm');
        if (nav) {
          nav.appendChild(cartBadge);
        }
      }
      cartBadge.textContent = cartCount;
      cartBadge.style.display = 'flex';
    }

    // === 4. XỬ LÝ THÊM VÀO GIỎ HÀNG ===
    // Tìm tất cả các sản phẩm có thể thêm vào giỏ
    const productCards = document.querySelectorAll('.trangch-thq-card-elm1, .trangch-thq-card-elm2, .trangch-thq-card-elm3');
    productCards.forEach(card => {
      // Thêm nút "Thêm vào giỏ" vào mỗi card
      const addToCartBtn = document.createElement('button');
      addToCartBtn.innerHTML = '🛒 Thêm vào giỏ';
      addToCartBtn.style.cssText = `
        position: absolute;
        bottom: 15px;
        right: 15px;
        background: #ff5c5c;
        color: white;
        border: none;
        padding: 8px 12px;
        border-radius: 20px;
        font-size: 12px;
        cursor: pointer;
        opacity: 0;
        transform: translateY(10px);
        transition: all 0.3s;
        z-index: 10;
      `;
      
      card.style.position = 'relative';
      card.appendChild(addToCartBtn);
      
      // Hiển thị nút khi hover
      card.addEventListener('mouseenter', function() {
        addToCartBtn.style.opacity = '1';
        addToCartBtn.style.transform = 'translateY(0)';
      });
      
      card.addEventListener('mouseleave', function() {
        addToCartBtn.style.opacity = '0';
        addToCartBtn.style.transform = 'translateY(10px)';
      });
      
      // Xử lý click thêm vào giỏ
      addToCartBtn.addEventListener('click', function(e) {
        e.stopPropagation(); // Ngăn sự kiện bubble lên card
        
        // Hiệu ứng
        this.style.transform = 'scale(0.9)';
        this.style.backgroundColor = '#4CAF50';
        this.innerHTML = '✓ Đã thêm';
        
        updateCartCount();
        
        // Reset sau 1.5 giây
        setTimeout(() => {
          this.style.transform = 'scale(1)';
          this.style.backgroundColor = '#ff5c5c';
          this.innerHTML = '🛒 Thêm vào giỏ';
        }, 1500);
      });
      
      // Click card để xem chi tiết (sẽ chuyển trang loại bánh)
      card.addEventListener('click', function() {
        const cakeType = this.querySelector('.trangch-thq-text-elm41, .trangch-thq-text-elm43, .trangch-thq-text-elm45')?.textContent || '';
        if (cakeType) {
          // Lưu loại bánh được chọn vào sessionStorage
          sessionStorage.setItem('selectedCake', cakeType);
          // Chuyển đến trang các loại bánh
          navigateToPage('cac-loai-banh');
        }
      });
    });

    // === 5. PRELOAD CÁC TRANG LIÊN QUAN ===
    function preloadPages() {
      const pageUrls = Object.values(pages);
      pageUrls.forEach(url => {
        if (url !== 'index.html') {
          const link = document.createElement('link');
          link.rel = 'prefetch';
          link.href = url;
          link.as = 'document';
          document.head.appendChild(link);
        }
      });
    }
    
    // Gọi preload sau khi trang load xong
    window.addEventListener('load', preloadPages);

    // === 6. BACK TO TOP BUTTON ===
    const backToTop = document.createElement('button');
    backToTop.innerHTML = '↑';
    backToTop.style.cssText = `
      position: fixed;
      bottom: 30px;
      right: 30px;
      width: 50px;
      height: 50px;
      background: #ff5c5c;
      color: white;
      border: none;
      border-radius: 50%;
      font-size: 20px;
      cursor: pointer;
      opacity: 0;
      transform: translateY(20px);
      transition: all 0.3s;
      z-index: 999;
      box-shadow: 0 4px 15px rgba(255, 92, 92, 0.3);
      display: flex;
      align-items: center;
      justify-content: center;
    `;
    
    document.body.appendChild(backToTop);
    
    window.addEventListener('scroll', function() {
      if (window.scrollY > 300) {
        backToTop.style.opacity = '1';
        backToTop.style.transform = 'translateY(0)';
      } else {
        backToTop.style.opacity = '0';
        backToScrollY(20px)';
      }
    });
    
    backToTop.addEventListener('click', function() {
      window.scrollTo({
        top: 0,
        behavior: 'smooth'
      });
    });

    // === 7. TẠO CÁC TRANG HTML MẪU (Nếu chưa có) ===
    // Tạo nút để tạo trang mẫu (chỉ dùng trong development)
    if (window.location.href.includes('localhost') || window.location.href.includes('127.0.0.1')) {
      const createPagesBtn = document.createElement('button');
      createPagesBtn.textContent = 'Tạo trang mẫu';
      createPagesBtn.style.cssText = `
        position: fixed;
        bottom: 90px;
        right: 30px;
        background: #2196F3;
        color: white;
        border: none;
        padding: 10px 15px;
        border-radius: 25px;
        cursor: pointer;
        z-index: 1000;
        font-size: 12px;
      `;
      createPagesBtn.addEventListener('click', createSamplePages);
      document.body.appendChild(createPagesBtn);
    }
    
    function createSamplePages() {
      // Tạo trang "Câu chuyện"
      if (!pagesExist('cauchuyen.html')) {
        createPage('cauchuyen.html', 'Câu Chuyện Của Chúng Tôi', `
          <div style="max-width: 800px; margin: 0 auto; padding: 40px 20px;">
            <h1 style="color: #ff5c5c; margin-bottom: 30px;">Câu Chuyện Thương Hiệu</h1>
            <p style="margin-bottom: 20px; line-height: 1.6;">
              Từ một tiệm bánh nhỏ trong con hẻm nhỏ, chúng tôi đã xây dựng nên thương hiệu Cake Bakery với đam mê và tình yêu dành cho nghệ thuật làm bánh.
            </p>
            <img src="./assets/css/images/image1.png" style="width: 100%; border-radius: 10px; margin: 20px 0;">
            <h2 style="color: #333; margin: 30px 0 20px;">Hành Trình 10 Năm</h2>
            <p style="margin-bottom: 15px;">
              🎂 2014: Khởi nghiệp với 1 lò nướng và 2 nhân viên
            </p>
            <p style="margin-bottom: 15px;">
              🏆 2018: Nhận giải "Tiệm bánh sáng tạo nhất năm"
            </p>
            <p style="margin-bottom: 15px;">
              📈 2022: Mở rộng 5 chi nhánh tại TP.HCM
            </p>
            <p style="margin-bottom: 15px;">
              🌟 2024: Phục vụ hơn 100,000 khách hàng hài lòng
            </p>
            <button onclick="window.location.href='index.html'" style="
              background: #ff5c5c;
              color: white;
              border: none;
              padding: 12px 30px;
              border-radius: 25px;
              cursor: pointer;
              margin-top: 30px;
              font-size: 16px;
            ">← Quay về Trang chủ</button>
          </div>
        `);
      }
      
      // Tạo trang "Đặt bánh"
      if (!pagesExist('datbanh.html')) {
        createPage('datbanh.html', 'Đặt Bánh Online', `
          <div style="max-width: 800px; margin: 0 auto; padding: 40px 20px;">
            <h1 style="color: #ff5c5c; margin-bottom: 30px;">Đặt Bánh Online</h1>
            <form id="orderForm" style="background: #f9f9f9; padding: 30px; border-radius: 10px;">
              <div style="margin-bottom: 20px;">
                <label style="display: block; margin-bottom: 8px; font-weight: bold;">Chọn loại bánh</label>
                <select style="width: 100%; padding: 10px; border-radius: 5px; border: 1px solid #ddd;">
                  <option>Tiramisu - 180.000đ</option>
                  <option>Mousse Dâu - 200.000đ</option>
                  <option>Macaron - 250.000đ</option>
                  <option>Chocolate - 250.000đ</option>
                  <option>Croissant - 200.000đ</option>
                </select>
              </div>
              
              <div style="margin-bottom: 20px;">
                <label style="display: block; margin-bottom: 8px; font-weight: bold;">Số lượng</label>
                <input type="number" min="1" value="1" style="width: 100px; padding: 10px; border-radius: 5px; border: 1px solid #ddd;">
              </div>
              
              <div style="margin-bottom: 20px;">
                <label style="display: block; margin-bottom: 8px; font-weight: bold;">Thông tin giao hàng</label>
                <input type="text" placeholder="Họ và tên" style="width: 100%; padding: 10px; margin-bottom: 10px; border-radius: 5px; border: 1px solid #ddd;">
                <input type="tel" placeholder="Số điện thoại" style="width: 100%; padding: 10px; margin-bottom: 10px; border-radius: 5px; border: 1px solid #ddd;">
                <textarea placeholder="Địa chỉ giao hàng" rows="3" style="width: 100%; padding: 10px; border-radius: 5px; border: 1px solid #ddd;"></textarea>
              </div>
              
              <button type="button" onclick="submitOrder()" style="
                background: #ff5c5c;
                color: white;
                border: none;
                padding: 15px 40px;
                border-radius: 25px;
                cursor: pointer;
                font-size: 16px;
                width: 100%;
              ">Xác nhận đặt hàng</button>
            </form>
            
            <div style="margin-top: 40px; background: #fff3cd; padding: 20px; border-radius: 10px;">
              <h3>📞 Hotline đặt bánh: 1900 1234</h3>
              <p>⏰ Thời gian giao hàng: 2-4 giờ trong nội thành</p>
              <p>🚚 Miễn phí giao hàng cho đơn từ 500.000đ</p>
            </div>
            
            <button onclick="window.location.href='index.html'" style="
              background: #6c757d;
              color: white;
              border: none;
              padding: 12px 30px;
              border-radius: 25px;
              cursor: pointer;
              margin-top: 30px;
              font-size: 16px;
            ">← Quay về Trang chủ</button>
          </div>
          
          <script>
            function submitOrder() {
              alert('Đơn hàng đã được ghi nhận! Chúng tôi sẽ liên hệ với bạn trong 5 phút.');
              setTimeout(() => {
                window.location.href = 'index.html';
              }, 2000);
            }
          </script>
        `);
      }
      
      // Tạo trang "Các loại bánh"
      if (!pagesExist('loaibanh.html')) {
        createPage('loaibanh.html', 'Các Loại Bánh', `
          <div style="max-width: 1200px; margin: 0 auto; padding: 40px 20px;">
            <h1 style="color: #ff5c5c; margin-bottom: 30px; text-align: center;">Danh Mục Bánh</h1>
            
            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 30px; margin-top: 40px;">
              ${['Tiramisu', 'Mousse Dâu', 'Macaron', 'Chocolate', 'Croissant', 'Bánh Kem Sinh Nhật', 'Cheesecake', 'Red Velvet'].map((cake, index) => `
                <div style="background: white; border-radius: 15px; overflow: hidden; box-shadow: 0 5px 15px rgba(0,0,0,0.1);">
                  <img src="./assets/css/images/image${(index % 4) + 2}.png" style="width: 100%; height: 200px; object-fit: cover;">
                  <div style="padding: 20px;">
                    <h3 style="margin-bottom: 10px;">${cake}</h3>
                    <p style="color: #666; margin-bottom: 15px;">Mô tả chi tiết về ${cake}</p>
                    <div style="display: flex; justify-content: space-between; align-items: center;">
                      <span style="color: #ff5c5c; font-weight: bold;">${(180 + index * 20).toLocaleString()}đ</span>
                      <button onclick="addToCart('${cake}')" style="
                        background: #ff5c5c;
                        color: white;
                        border: none;
                        padding: 8px 15px;
                        border-radius: 20px;
                        cursor: pointer;
                      ">Thêm vào giỏ</button>
                    </div>
                  </div>
                </div>
              `).join('')}
            </div>
            
            <div style="text-align: center; margin-top: 50px;">
              <button onclick="window.location.href='datbanh.html'" style="
                background: #ff5c5c;
                color: white;
                border: none;
                padding: 15px 40px;
                border-radius: 25px;
                cursor: pointer;
                font-size: 16px;
                margin-right: 20px;
              ">Đặt bánh ngay</button>
              
              <button onclick="window.location.href='index.html'" style="
                background: #6c757d;
                color: white;
                border: none;
                padding: 15px 40px;
                border-radius: 25px;
                cursor: pointer;
                font-size: 16px;
              ">← Quay về Trang chủ</button>
            </div>
          </div>
          
          <script>
            function addToCart(cakeName) {
              alert('Đã thêm ' + cakeName + ' vào giỏ hàng!');
            }
          </script>
        `);
      }
    }
    
    function pagesExist(filename) {
      // Kiểm tra đơn giản - trong thực tế nên kiểm tra thật
      return false;
    }
    
    function createPage(filename, title, content) {
      const pageHTML = `
        <!DOCTYPE html>
        <html lang="vi">
        <head>
          <title>${title} - Cake Bakery</title>
          <meta charset="utf-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <style>
            body {
              font-family: 'Inter', sans-serif;
              margin: 0;
              padding: 0;
              background: #f8f9fa;
            }
            header {
              background: white;
              padding: 20px;
              box-shadow: 0 2px 10px rgba(0,0,0,0.1);
              display: flex;
              justify-content: space-between;
              align-items: center;
            }
            .logo {
              color: #ff5c5c;
              font-size: 24px;
              font-weight: bold;
              text-decoration: none;
            }
            nav a {
              margin-left: 20px;
              text-decoration: none;
              color: #333;
              font-weight: 500;
            }
            nav a:hover {
              color: #ff5c5c;
            }
          </style>
        </head>
        <body>
          <header>
            <a href="index.html" class="logo">Cake Bakery</a>
            <nav>
              <a href="index.html">Trang chủ</a>
              <a href="cauchuyen.html">Câu chuyện</a>
              <a href="datbanh.html">Đặt bánh</a>
              <a href="loaibanh.html">Các loại bánh</a>
            </nav>
          </header>
          ${content}
        </body>
        </html>
      `;
      
      // Tạo file (chỉ hoạt động trong môi trường development)
      const blob = new Blob([pageHTML], { type: 'text/html' });
      const url = URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = filename;
      a.click();
      URL.revokeObjectURL(url);
      
      alert(`Đã tạo file ${filename}. Vui lòng đặt file này trong cùng thư mục với index.html`);
    }
    
    // === 8. SOCIAL MEDIA INTERACTION ===
    const socialButtons = document.querySelectorAll('.trangch-thq-buttons-icon-elm1, .trangch-thq-buttons-icon-elm2, .trangch-thq-buttons-icon-elm3, .trangch-thq-buttons-icon-elm4');
    socialButtons.forEach(button => {
      button.addEventListener('click', function() {
        const platform = this.querySelector('img').alt.toLowerCase();
        const urls = {
          facebook: 'https://facebook.com',
          instagram: 'https://instagram.com',
          twitter: 'https://twitter.com',
          youtube: 'https://youtube.com'
        };
        
        window.open(urls[platform] || 'https://example.com', '_blank');
      });
    });
  });

  // === 9. XỬ LÝ TRƯỚC KHI RỜI TRANG ===
  window.addEventListener('beforeunload', function() {
    // Lưu trạng thái giỏ hàng
    const cartCount = document.querySelector('.cart-badge')?.textContent || '0';
    localStorage.setItem('cakeBakeryCart', cartCount);
  });
  
  // === 10. KHI TRANG ĐƯỢC TẢI LẠI ===
  window.addEventListener('load', function() {
    // Khôi phục giỏ hàng
    const savedCart = localStorage.getItem('cakeBakeryCart');
    if (savedCart && parseInt(savedCart) > 0) {
      const cartBadge = document.querySelector('.cart-badge');
      if (cartBadge) {
        cartBadge.textContent = savedCart;
        cartBadge.style.display = 'flex';
      }
    }
  });
</script>