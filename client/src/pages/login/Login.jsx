import { Link } from "react-router-dom";
import "./login.scss"
import { AuthenBackground } from "../../components/authenBackground/AuthenBackground";

export const Login = () => {
  return (
    <div>
      <AuthenBackground/>
      <div className="login">
      <div className="card">
        <div className="left">
        <div className="left-top">
            {/* <h1>Mạng xã hội cựu sinh viên trường đại học Mở TP.HCM</h1>
            <p>
              Chức năng này giành riêng cho cựu sinh viên trường đại học Mở TP.HCM. Vui lòng cung cấp đủ thông tin cần thiết!
            </p> */}
          </div>
          <div className="left-bottom">
            <span>Bạn chưa có tài khoản?</span>
            <Link to="/register">
              <button>Đăng ký ngay</button>
            </Link>
          </div>
        </div>
        <div className="right">
          <h1>Đăng nhập</h1>
          <form>
            <input type="text" placeholder="Gmail" />
            <input type="password" placeholder="Mật khẩu" />
            <button>Đăng nhập</button>
            {/* <button onClick={handleLogin}>Login</button> */}
          </form>
          <p>Mạng xã hội cựu sinh viên trường đại học Mở TP.HCM được hình thành năm 2023 nhằm đáp ứng nhu cầu giao tiếp giữa trường và các cựu sinh viên sau khi ra trường.</p>
        </div>        
      </div>
    </div>
    </div>
  );
}
