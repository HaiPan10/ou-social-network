import React from 'react'
import './accountLocked.scss'

export const AccountLocked = () => {
    return (
        <div>
            <div className="accountLocked">
                <div className="card">
                    <div className='title'>
                        <img src={require('../../images/account_locked.png')} />
                        <h1>Tài khoản bị khóa</h1>
                    </div>
                    <div className='content'>
                        <p>Tài khoản của bạn đã bị khóa!</p>
                        <p>Chúng tôi rất tiếc phải thông báo tài khoản của bạn đã bị khóa.
                            Vui lòng liên hệ với quản trị viên để được mở khóa tài khoản.
                        </p>
                    </div>
                </div>
            </div>
        </div>
    )
}
