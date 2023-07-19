import "./login.scss"

export const Login = () => {
  return (
    <div className="login">
      <div className="card">
        <div className="left">
          <span>Chưa có tài khoản?</span>
          <button>Đăng ký ngay</button>
        </div>
        <div className="right">
          <h1>Đăng nhập</h1>
          <form>
            <input type="text" placeholder="Gmail" />
            <input type="password" placeholder="Mật khẩu" />
            <button>Đăng nhập</button>
            {/* <button onClick={handleLogin}>Login</button> */}
          </form>
        </div>
      </div>
    </div>
  );
}
