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

export const Layout = () => {
    const [user, dispatch] = useContext(AuthContext)
    const [status, setStatus] = useState()

    useEffect(() => {
        if (user !== null) {
            const getStatus = async () => {
                try {
                    let res = await authAPI().get(endpoints['status'] + `/${user.id}`)
                    setStatus(res.data)
                } catch (ex) {  
                    setStatus("ERROR")      
                }  
            }
    
            getStatus()
        } else {
            console.log("LOGIN")
            setStatus("LOGIN")
        }
    }, [])

    if (user === null) {
        return <Navigate to="/login" />
    }

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
    } else if (status === "ERROR") {
        pageContent = <NavBar/>
    } else if (status === "LOGIN") {
        return <Navigate to="/login" />
    }

    return (
        <div>
            {pageContent}
        </div>
    )
}
