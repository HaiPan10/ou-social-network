import React from 'react'
import { AuthenBackground } from "../../components/authenBackground/AuthenBackground";
import './emailVerification.scss'

export const EmailVerification = () => {
  return (
    <div>
        <AuthenBackground/>
        <div className="emailVerification">
            <div className="card">
                <div className='title'>
                    <h1>Xác thực email</h1>
                </div>
                <div className='content'>
                    Chúng tôi đã gửi mã xác thực về email của bạn, vui lòng kiểm tra mã trong email của bạn.
                    <a href='#'> Gửi lại email</a>
                </div>
            </div>
        </div>
    </div>
  )
}
