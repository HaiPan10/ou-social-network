import React, { useContext } from 'react'
import { AuthContext } from '../../context/AuthContext'
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import DarkModeOutlinedIcon from "@mui/icons-material/DarkModeOutlined";
import GridViewOutlinedIcon from "@mui/icons-material/GridViewOutlined";
import NotificationsOutlinedIcon from "@mui/icons-material/NotificationsOutlined";
import EmailOutlinedIcon from "@mui/icons-material/EmailOutlined";
import PersonOutlinedIcon from "@mui/icons-material/PersonOutlined";
import SearchOutlinedIcon from "@mui/icons-material/SearchOutlined";
import './navBar.scss'
import { DarkModeContext } from '../../context/DarkModeContext';

export const NavBar = () => {
  const [user, dispatch] = useContext(AuthContext)
  const { toggle } = useContext(DarkModeContext)

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
        <HomeOutlinedIcon/>
        <DarkModeOutlinedIcon onClick = {toggle}/>
        <GridViewOutlinedIcon/>
        <div className='search'>
          <SearchOutlinedIcon/>
          <input type="text" placeholder='Tìm kiếm' />
        </div>
      </div>
      <div className='right'>
        <PersonOutlinedIcon/>
        <EmailOutlinedIcon/>
        <NotificationsOutlinedIcon/>
        <button onClick={logout}>Đăng xuất</button>
        <div className='user'>
          <img src={require('../../images/default_avatar.png')} alt="" />
          <span>{user.firstName}</span>
        </div>
      </div>
    </div>
  )
}
