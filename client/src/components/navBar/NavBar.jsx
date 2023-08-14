import React, { useContext } from 'react'
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

export const NavBar = () => {
  const [user, dispatch] = useContext(AuthContext)
  const { toggle } = useContext(DarkModeContext)
  const {darkMode} = useContext(DarkModeContext)

  const logout = (evt) => {
    evt.preventDefault()
    dispatch({
      "type": "LOGOUT",
    })
  }

  return (
    <div className='navBar'>
      <div className='left'>
        <div className='logo'>
          <img />
          {/* <span>OU Social Network</span> */}
        </div>
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
        <EmailOutlinedIcon/>
        <NotificationsOutlinedIcon/>
        <div className="dropdown">
          <div className='user'>
            <img src={user.avatar} alt="" />
            <span>{user.firstName}</span>
          </div>
          <div className="dropdown-content">
            <div onClick={logout}>Đăng xuất <LogoutOutlinedIcon/></div>
            <Link to={`/profile/${user.id}`} style={{ textDecoration: 'none' }}>
              <div>Xem trang cá nhân <AccountBoxOutlinedIcon/></div>
            </Link>
            {!darkMode ? <div onClick = {toggle}>Chế độ tối <DarkModeOutlinedIcon/></div> : 
            <div onClick = {toggle}>Chế độ sáng <LightModeOutlinedIcon/></div>}            
          </div>
        </div>
        {/* <button onClick={logout}>Đăng xuất</button>         */}
      </div>
    </div>
  )
}
