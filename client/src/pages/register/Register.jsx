import "./register.scss"

export const Register = () => {
  return (
    <div className="register">
      <div className="card">
        <div className="right">
          <div className="right-top">
            <h1>Mạng xã hội cựu sinh viên trường đại học Mở TP.HCM</h1>
            <p>
              Chức năng này giành riêng cho cựu sinh viên trường đại học Mở TP.HCM. Vui lòng cung cấp đủ thông tin cần thiết!
            </p>
          </div>
          <div className="right-bottom">
            <span>Bạn đã có tài khoản?</span>
            <button>Đăng nhập ngay</button>
          </div>
        </div>
        <div className="left">
          <h1>Đăng ký</h1>
          <form>
            <input type="text" placeholder="Họ" />
            <input type="text" placeholder="Tên" />
            <input type="email" placeholder="Email" />
            <input type="password" placeholder="Mật khẩu" />
            <input type="text" placeholder="Mã số sinh viên" />
            <button>Đăng ký</button>
          </form>
        </div>
      </div>
    </div>
  )
}
