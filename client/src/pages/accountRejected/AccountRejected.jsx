import React from 'react'
import './accountRejected.scss'

export const AccountRejected = () => {
    return (
        <div>
            <div className="accountRejected">
                <div className="card">
                    <div className='title'>
                        <img src={require('../../images/rejected_account.png')} />
                        <h1>Tài khoản bị từ chối</h1>
                    </div>
                    <div className='content'>
                        <p>Tài khoản của bạn đã bị từ chối!</p>
                        <p>Chúng tôi rất tiếc phải thông báo tài khoản của bạn đã bị từ chối.
                            Vui lòng liên hệ với quản trị viên nếu cần hỗ trợ.
                        </p>
                    </div>
                </div>
            </div>
        </div>
    )
}
