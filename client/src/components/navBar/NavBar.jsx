import React, { useContext, useEffect, useRef, useState } from 'react'
import { AuthContext } from '../../context/AuthContext'
import DarkModeOutlinedIcon from "@mui/icons-material/DarkModeOutlined";
import GridViewOutlinedIcon from "@mui/icons-material/GridViewOutlined";
import NotificationsOutlinedIcon from "@mui/icons-material/NotificationsOutlined";
import EmailOutlinedIcon from "@mui/icons-material/EmailOutlined";
import LightModeOutlinedIcon from '@mui/icons-material/LightModeOutlined';
import SearchOutlinedIcon from "@mui/icons-material/SearchOutlined";
import './navBar.scss'
import { DarkModeContext } from '../../context/DarkModeContext';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';
import AccountBoxOutlinedIcon from '@mui/icons-material/AccountBoxOutlined';
import { Link } from 'react-router-dom';
import LockPersonIcon from '@mui/icons-material/LockPerson';
import ChangePassword from '../changePassword/ChangePassword';
import { ReloadContext } from '../../context/ReloadContext';
import SupervisorAccountIcon from '@mui/icons-material/SupervisorAccount';

export const NavBar = (props) => {
  const [user, dispatch] = useContext(AuthContext)
  const { toggle } = useContext(DarkModeContext)
  const {darkMode} = useContext(DarkModeContext)
  const [menuVisible, setMenuVisible] = useState(false)
  const [changePasswordShow, setChangePasswordShow] = useState(false)
  const navbarRef = useRef(null)
  const { reloadData } = useContext(ReloadContext)

  const handleClickOutside = (event) => {
    if (navbarRef.current && !navbarRef.current.contains(event.target)) {
      setMenuVisible(false)
    }
  }

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside)
    }
  }, [])

  const toggleDropdown = () => {
    setMenuVisible(!menuVisible);
  }

  const logout = (evt) => {
    evt.preventDefault()
    dispatch({
      "type": "LOGOUT",
    })
  }

  return (
    <div className='navBar'>
      <div className='left'>
        <Link to="/" className='turnoff-link-style'>
          <div className='logo' onClick={reloadData}>
            <img />
            {/* <span>OU Social Network</span> */}
          </div>
        </Link>
        {/* <Link to="/">
          <HomeOutlinedIcon/>
        </Link> */}
        {/* <GridViewOutlinedIcon/> */}
        <div className='search'>
          <SearchOutlinedIcon/>
          <input type="text" placeholder='Tìm kiếm' />
        </div>
      </div>
      <div className='right'>
        {/* <EmailOutlinedIcon/>
        <NotificationsOutlinedIcon/> */}
        <div className="dropdown" ref={navbarRef}>
          <div className='user' onClick={toggleDropdown}>
            <img src={user.avatar} alt="" />
            <span>{user.firstName}</span>
          </div>
          {menuVisible && <div className="dropdown-content">
            <div onClick={logout}><LogoutOutlinedIcon/> Đăng xuất</div>
            <div onClick={() =>setChangePasswordShow(true)}><LockPersonIcon/> Đổi mật khẩu</div>
            <Link to={`/profile/${user.id}`} style={{ textDecoration: 'none' }}>
              <div><AccountBoxOutlinedIcon/>Trang cá nhân </div>
            </Link>
            {!darkMode ? <div onClick = {toggle}><DarkModeOutlinedIcon/> Chế độ tối</div> : 
            <div onClick = {toggle}><LightModeOutlinedIcon/> Chế độ sáng</div>}
            <Link to={`/profile/1`} style={{ textDecoration: 'none' }}>
              <div><SupervisorAccountIcon/> Trang quản trị viên</div>
            </Link>
          </div>}
          <ChangePassword show={changePasswordShow} setChangePasswordShow={setChangePasswordShow}/>
        </div>
        {/* <button onClick={logout}>Đăng xuất</button>         */}
      </div>
    </div>
  )
}
