import { Link } from "react-router-dom";
import "./login.scss"
import { AuthenBackground } from "../../components/authenBackground/AuthenBackground";
import { useState } from "react";
import Api, { endpoints } from "../../configs/Api";
import ErrorAlert from "../../components/ErrorAlert";
import { Home } from "../home/Home";
import { EmailVerification } from "../emailVerification/EmailVerification";
import { AccountLocked } from "../accountLocked/AccountLocked";
import { AccountPending } from "../accountPending/AccountPending";
import { AccountRejected } from "../accountRejected/AccountRejected";

export const Login = () => {
  const [err, setErr] = useState()
  const [disableButton, setDisableButton] = useState()
  const [session, setSession] = useState('LOGIN')

  const [account, setAccount] = useState({
    "id": "",
    "email": "",
    "password": "",
    "confirmPassword": "",
  })

  const login = (evt) => {
    evt.preventDefault()
    setDisableButton(true);

    const process = async () => {
        try {
            let res = await Api.post(endpoints['login'], {
                "account": account
            })
            
            if (res.status === 200) {
              setSession("HOME")
            }
        } catch (ex) {
          if (ex.response.data.id === undefined) {
            setErr(ex.response.data)
          } else {
            setAccount(account => ({...account, ["id"]:ex.response.data.id}))
            setSession(ex.response.data.status)
          }
          setDisableButton(false);
        }
    }

    process()
  }

  let pageContent;
  if (session === "LOGIN") {
    pageContent = (
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
              <form onSubmit={login}>
                {err?<ErrorAlert err={err} />:""}
                <input type="email" placeholder="Email" value={account.email} onChange={(e) => setAccount(account => ({...account, ["email"]:e.target.value}))} required/>
                <input type="password" placeholder="Mật khẩu" value={account.password} onChange={(e) => setAccount(account => ({...account, ["password"]:e.target.value}))} required/>
                <button disabled={disableButton}>Đăng nhập</button>
              </form>
              <p>Mạng xã hội cựu sinh viên trường đại học Mở TP.HCM được hình thành năm 2023 nhằm đáp ứng nhu cầu giao tiếp giữa trường và các cựu sinh viên sau khi ra trường.</p>
            </div>        
          </div>
        </div>
      </div>
    )
  } else if (session === "EMAIL_VERIFICATION_PENDING") {
    pageContent = (
      <div>
        <AuthenBackground/>
        <EmailVerification accountId={account.id}/>
      </div>
    )
  } else if (session === "LOCKED") {
    pageContent = (
      <div>
        <AuthenBackground/>
        <AccountLocked/>
      </div>
    )
  } else if (session === "AUTHENTICATION_PENDING") {
    pageContent = (
      <div>
        <AuthenBackground/>
        <AccountPending/>
      </div>
    )
  } else if (session === "REJECT") {
    pageContent = (
      <div>
        <AuthenBackground/>
        <AccountRejected/>
      </div>
    )
  } else if (session === "HOME") {
    pageContent = <Home/>
  }

  return (
    <div>
      {pageContent}
    </div>
  );
}
