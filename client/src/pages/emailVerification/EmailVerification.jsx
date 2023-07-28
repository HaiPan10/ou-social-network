import React from 'react'
import Api, { endpoints } from "../../configs/Api";
import './emailVerification.scss'

export const EmailVerification = (props) => {
    const resend = (evt) => {
        evt.preventDefault()
        const process = async () => {
            try {
                let res = await Api.get(endpoints['verify'] + `/${props.accountId}`)
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
                        <img src={require('../../images/email_verification.png')} />
                        <h1>Xác thực email</h1>
                    </div>
                    <div className='content'>
                        <p>Tài khoản của bạn cần được xác thực email!</p>
                        <p>Chúng tôi đã gửi mail xác thực về email của bạn, vui lòng nhấn vào đường link trong mail. 
                        <span><a href='' onClick={resend}>Gửi lại email</a></span>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    )
}
