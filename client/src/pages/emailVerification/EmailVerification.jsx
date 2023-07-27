import React from 'react'
import Api, { endpoints } from "../../configs/Api";
import './emailVerification.scss'

export const EmailVerification = (props) => {
    const account = {
        "id": props.account.id,
        "email": props.account.email,
        "verificationCode": props.account.verificationCode,
        "user": {
            "firstName": props.account.user.firstName,
            "lastName": props.account.user.lastName,
        }
    }

    const resend = (evt) => {
        evt.preventDefault()
        const process = async () => {
            try {
                let res = await Api.post(endpoints['verify'], {
                    "account": account,
                })
                if (res.status === 200) {
                    console.log("resent email!")
                }
            } catch (ex) {
                console.log("resent failed!")
            }
        }

        process()
      }

    return (
        <div>
            <div className="emailVerification">
                <div className="card">
                    <div className='title'>
                        <h1>Xác thực email</h1>
                    </div>
                    <div className='content'>
                        Chúng tôi đã gửi mã xác thực về email của bạn, vui lòng kiểm tra mã trong email của bạn. 
                        <span><a href='' onClick={resend}>Gửi lại email</a></span>
                    </div>
                </div>
            </div>
        </div>
    )
}
