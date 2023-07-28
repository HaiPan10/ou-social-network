import React from 'react'
import './accountPending.scss'

export const AccountPending = () => {
    return (
        <div>
            <div className="accountPending">
                <div className="card">
                    <div className='title'>
                        <img src={require('../../images/account_pending.png')} />
                        <h1>Tài khoản đang chờ duyệt</h1>
                    </div>
                    <div className='content'>
                        <p>Tài khoản của bạn chưa được duyệt!</p>
                        <p> Chúng tôi sẽ duyệt tài khoản sớm nhất có thể.
                            Chúng tôi sẽ gửi email phản hồi ngay khi hoàn thành quá trình kiểm duyệt tài khoản.
                        </p>
                    </div>
                </div>
            </div>
        </div>
    )
}
