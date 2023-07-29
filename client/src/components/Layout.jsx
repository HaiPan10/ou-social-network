import React, { useContext, useEffect, useState } from 'react'
import { AuthContext } from '../context/AuthContext';
import { Navigate, Outlet } from 'react-router-dom';
import { NavBar } from './navBar/NavBar';
import { LeftBar } from './leftBar/LeftBar';
import { RightBar } from './rightBar/RightBar';
import { authAPI, endpoints } from '../configs/Api';
import { EmailVerification } from '../pages/emailVerification/EmailVerification';
import { AccountLocked } from '../pages/accountLocked/AccountLocked';
import { AccountPending } from '../pages/accountPending/AccountPending';
import { AccountRejected } from '../pages/accountRejected/AccountRejected';
import { load } from 'react-cookies';

export const Layout = () => {
    const [user, dispatch] = useContext(AuthContext)
    const [status, setStatus] = useState()

    useEffect(() => {
        if (user !== null) {
            console.log("GET STATUS")
            console.log(load("access-token"))
            const getStatus = async () => {
                try {
                    let res = await authAPI().get(endpoints['status'] + `/${user.id}`)
                    setStatus(res.data)
                } catch (ex) {                    
                }  
            }
    
            getStatus()
        } else {
            console.log("LOGIN")
            return <Navigate to="/login" />
        }
    }, [])
    
    let pageContent;
    if (status === "ACTIVE") {
        pageContent = (
            <div>
                <NavBar />
                <div style={{ display: "flex" }}>
                    <LeftBar />
                    <Outlet />
                    <RightBar />
                </div>
            </div>
        )
    } else if (status === "EMAIL_VERIFICATION_PENDING") {
    pageContent = <EmailVerification accountId={user.id}/>
    } else if (status === "LOCKED") {
    pageContent = <AccountLocked/>
    } else if (status === "AUTHENTICATION_PENDING") {
    pageContent = <AccountPending/>
    } else if (status === "REJECT") {
    pageContent = <AccountRejected/>
    }

    return (
        <div>
            {pageContent}
        </div>
    )
}
