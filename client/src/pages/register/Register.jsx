import { Link } from "react-router-dom"
import "./register.scss"
import axios from 'axios';
import { AuthenBackground } from "../../components/authenBackground/AuthenBackground";
import { useState } from "react";
import Api, { endpoints } from "../../configs/Api";
import ErrorAlert from "../../components/ErrorAlert";

export const Register = () => {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [rePassword, setRePassword] = useState('')
  const [err, setErr] = useState()

  const register = (evt) => {
    evt.preventDefault()

    const process = async () => {
        try {
            let res = await Api.post(endpoints['signin'], {
                "email": email,
                "password": password,
            })
            
            if (res.status === 200) {
              console.log(res.data)
            }
        } catch (ex) {
            setErr(Object.values(ex.response.data).at(3))
        }
    }

    if (email === "" || password === "")
      setErr("Phải nhập username và password!")
    else if (password !== rePassword) {
      setErr("Mật khẩu không khớp!")
    }
    else {
      process()
    }
}

  return (
    <div>
      <AuthenBackground/>
      <div className="register">
        <div className="card">
        <div className="right">
          <div className="right-top">
            {/* <h1>Mạng xã hội cựu sinh viên trường đại học Mở TP.HCM</h1> */}
            {/* <p>
              Chức năng này giành riêng cho cựu sinh viên trường đại học Mở TP.HCM.
            </p> */}
          </div>
          <div className="right-bottom">
            <span>Bạn đã có tài khoản?</span>
            <Link to="/login">
              <button>Đăng nhập ngay</button>
            </Link>
          </div>
        </div>
        <div className="left">
          <h1>Đăng ký</h1>
          {err?<ErrorAlert err={err} />:""}
          <form onSubmit={register}>
            <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)}/>
            <input type="password" placeholder="Mật khẩu" value={password} onChange={(e) => setPassword(e.target.value)}/>
            <input type="password" placeholder="Xác nhận mật khẩu" value={rePassword} onChange={(e) => setRePassword(e.target.value)}/>
            <button type="submit">Tiếp tục</button>
          </form>
        </div>
      </div>
    </div>
    </div>
  )
}
