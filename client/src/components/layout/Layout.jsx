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
import { signInWithEmailAndPassword } from 'firebase/auth';
import '../../style.scss'
import './layout.scss'
import { DarkModeContext } from '../../context/DarkModeContext';
import 'react-image-lightbox/style.css';
import { ReloadContext } from "../../context/ReloadContext";
import { SearchContext } from '../../context/SearchContext';
import { auth } from '../../configs/firebase';
import { load, save } from 'react-cookies';
import { ChatContext } from '../../context/ChatContext';

export const Layout = () => {
    const [user, dispatch] = useContext(AuthContext)
    const [status, setStatus] = useState()
    const {darkMode} = useContext(DarkModeContext)
    const { reload } = useContext(ReloadContext)
    const { showSearch } = useContext(SearchContext)
    const firebaseToken = load("firebase-token");
    const { showChat } = useContext(ChatContext)
    
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

            const authFirebase = async () => {
                try {
                    await signInWithEmailAndPassword(auth, load("firebase-email"), load("firebase-password"));
                } catch (ex) {
                }
            }
    
            getStatus()
            authFirebase()
        } else {
            setStatus("LOGIN")
        }


    }, [reload])

    if (user === null || firebaseToken === null) {
        return <Navigate to="/login" />
    }

    let pageContent;
    if (status === "ACTIVE" || status === "PASSWORD_CHANGE_REQUIRED") {
        pageContent = (
            <div className={`theme-${darkMode ? "dark" : "light"}`}>
                <NavBar />
                <div style={{ display: "flex" }}>
                    <LeftBar status={status}/>
                    {/* <div style={{flex: showSearch ? "5" : showChat ? "9.5" : "6", position: "relative"}}><Outlet/></div> */}
                    <div style={{flex: showSearch || showChat ? "5" : "6", position: "relative"}}><Outlet/></div>
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
