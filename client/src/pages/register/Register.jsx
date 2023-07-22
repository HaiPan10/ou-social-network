import { Link } from "react-router-dom"
import "./register.scss"
import axios from 'axios';
import { AuthenBackground } from "../../components/authenBackground/AuthenBackground";
import { useState } from "react";
import Api, { endpoints } from "../../configs/Api";
import ErrorAlert from "../../components/ErrorAlert";

export const Register = () => {
  // const [email, setEmail] = useState('')
  // const [password, setPassword] = useState('')
  // const [rePassword, setRePassword] = useState('')
  const [err, setErr] = useState()

  const [account, setAccount] = useState({
    "email": "",
    "password": "",
    "confirmPassword": "",
    "roleId": {
      "id": 1,
      "name": "FORMER_STUDENT"
    }
  })

  const [user, setUser] = useState({
    "firstName": "",
    "lastName": "",
  })

  const [userStudent, setUserStudent] = useState({
    "studentIdentical": "",
  })

  const register = (evt) => {
    evt.preventDefault()

    const process = async () => {
        try {
            let res = await Api.post(endpoints['register'], {
                "account": account,
                "user": user,
                "userStudent": userStudent
            })
            
            if (res.status === 200) {
              console.log(res.data)
            }
        } catch (ex) {
          setErr(ex.response.data)
        }
    }

    if (account.password !== account.confirmPassword) {
      setErr("Mật khẩu không khớp!")
    }
    else {
      process()
    }
  }

  const [step, setStep] = useState(1);

  const nextStep = () => {
    setStep(step + 1);
  };

  const prevStep = () => {
    setStep(step - 1);
  };

  const renderForm = () => {
    switch (step) {
      case 1:
        return (
          <div className="input-field">
            <input type="email" placeholder="Email" value={account.email} onChange={(e) => setAccount(account => ({...account, ["email"]:e.target.value}))} required/>
            <input type="password" placeholder="Mật khẩu" value={account.password} onChange={(e) => setAccount(account => ({...account, ["password"]:e.target.value}))} required/>
            <input type="password" placeholder="Xác nhận mật khẩu" value={account.confirmPassword} onChange={(e) => setAccount(account => ({...account, ["confirmPassword"]:e.target.value}))} required/>
          </div>
        );
      case 2:
        return (
          <div className="input-field">
              <input type="text" placeholder="Họ" value={user.lastName} minLength="1" maxLength="20" onChange={(e) => setUser(user => ({...user, ["lastName"]:e.target.value}))} required/>
              <input type="text" placeholder="Tên" value={user.firstName} minLength="1" maxLength="10" onChange={(e) => setUser(user => ({...user, ["firstName"]:e.target.value}))} required/>
              <input type="text" placeholder="Mã số sinh viên" minLength="10" maxLength="10" value={userStudent.studentIdentical} onChange={(e) => setUserStudent(userStudent => ({...userStudent, ["studentIdentical"]:e.target.value}))} required/>
            </div>
        );
      default:
        return null;
    }
  };

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
          <form onSubmit={register}>
            {renderForm()}

            {err?<ErrorAlert err={err} />:""}
            
            <div className="btn-field">
              {step > 1 && <button className="btn-back" onClick={prevStep}>Quay lại</button>}
              {step < 2 ? (
                <button className="btn-next" onClick={nextStep}>Tiếp tục</button>
              ) : (
                <button className="btn-submit" type="submit">Đăng ký</button>
              )}
            </div>
          </form>

          <p>Mạng xã hội cựu sinh viên trường đại học Mở TP.HCM được hình thành năm 2023 nhằm đáp ứng nhu cầu giao tiếp giữa trường và các cựu sinh viên sau khi ra trường.</p>
        </div>
      </div>
    </div>
    </div>
  )
}
