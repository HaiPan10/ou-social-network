import React, { useContext, useEffect, useState } from 'react'
import { AuthContext } from '../../context/AuthContext';
import { Navigate, Outlet } from 'react-router-dom';
import { NavBar } from '../navBar/NavBar';
import { LeftBar } from '../leftBar/LeftBar';
import { RightBar } from '../rightBar/RightBar';
import { authAPI, endpoints } from '../../configs/Api';
import { EmailVerification } from '../../pages/emailVerification/EmailVerification';
import { AccountLocked } from '../../pages/accountLocked/AccountLocked';
import { AccountPending } from '../../pages/accountPending/AccountPending';
import { AccountRejected } from '../../pages/accountRejected/AccountRejected';
import { AuthenBackground } from '../authenBackground/AuthenBackground';
import '../../style.scss'
import './layout.scss'
import { DarkModeContext } from '../../context/DarkModeContext';
import 'react-image-lightbox/style.css';
import { ReloadContext } from "../../context/ReloadContext";
import { SearchContext } from '../../context/SearchContext';

export const Layout = () => {
    const [user, dispatch] = useContext(AuthContext)
    const [status, setStatus] = useState()
    const {darkMode} = useContext(DarkModeContext)
    const { reload } = useContext(ReloadContext)
    const { showSearch } = useContext(SearchContext)
    
    useEffect(() => {
        if (user !== null) {
            const getStatus = async () => {
                try {
                    let res = await authAPI().get(endpoints['status'])
                    setStatus(res.data)
                } catch (ex) {  
                    setStatus("ERROR")
                }  
            }
    
            getStatus()
        } else {
            setStatus("LOGIN")
        }
    }, [reload])

    if (user === null) {
        return <Navigate to="/login" />
    }

    let pageContent;
    if (status === "ACTIVE" || status === "PASSWORD_CHANGE_REQUIRED") {
        pageContent = (
            <div className={`theme-${darkMode ? "dark" : "light"}`}>
                <NavBar />
                <div style={{ display: "flex" }}>
                    <LeftBar status={status}/>
                    <div style={{flex: showSearch ? "5" : "6", position: "relative"}}><Outlet/></div>
                    <RightBar />
                </div>
            </div>
        )
    } else if (status === "EMAIL_VERIFICATION_PENDING") {
        pageContent = (
            <div>
                <AuthenBackground/>
                <EmailVerification accountId={user.id}/>
            </div>
        )

    } else if (status === "LOCKED") {
        pageContent = (
            <div>
                <AuthenBackground/>
                <AccountLocked/>
            </div>
        )
    } else if (status === "AUTHENTICATION_PENDING") {
        pageContent = (
            <div>
                <AuthenBackground/>
                <AccountPending/>
            </div>
        )
    } else if (status === "REJECT") {
        pageContent = (
            <div>
                <AuthenBackground/>
                <AccountRejected/>
            </div>
        )
    } else if (status === "ERROR") {
        pageContent = <NavBar/>
    } else if (status === "LOGIN") {
        return <Navigate to="/login" />
    }

    return (
        <div className='layout'>
            {pageContent}
        </div>
    )
}
