import React, { useContext, useEffect, useRef, useState } from 'react'
import { AuthContext } from '../../context/AuthContext'
import './leftBar.scss'
import { Link } from 'react-router-dom'
import HomeIcon from '@mui/icons-material/Home';
import SettingsIcon from '@mui/icons-material/Settings';
import { DarkModeContext } from '../../context/DarkModeContext';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';
import DarkModeOutlinedIcon from "@mui/icons-material/DarkModeOutlined";
import LightModeOutlinedIcon from '@mui/icons-material/LightModeOutlined';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import UploadPost from '../postLayout/UploadPost';
import { ReloadContext } from '../../context/ReloadContext';
import LockPersonIcon from '@mui/icons-material/LockPerson';
import ChangePassword from '../changePassword/ChangePassword';

export const LeftBar = (props) => {
  const [user, dispatch] = useContext(AuthContext)
  const { toggle } = useContext(DarkModeContext)
  const {darkMode} = useContext(DarkModeContext)
  const [uploadPostShow, setUploadPostShow] = useState(false)
  const [dropdownVisible, setDropdownVisible] = useState(false)
  const dropdownRef = useRef(null)
  const { reloadData } = useContext(ReloadContext)
  const [changePasswordShow, setChangePasswordShow] = useState(false)

  const handleClickOutside = (event) => {
    if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
      setDropdownVisible(false)
    }
  
  }

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside)
    }
  }, [])

  const toggleDropdown = () => {
    setDropdownVisible(!dropdownVisible);
  }

  const logout = (evt) => {
    evt.preventDefault()
    
    dispatch({
      "type": "LOGOUT"
    })
  }


  return (
    <div className='leftBar'>
      <div className='container'>
        <div className='menu'>
          <div className='menu-top'>
            <div className='item logo'>
              <img />
            </div>
            <Link to="/" className='turnoff-link-style'>
              <div className="item" onClick={reloadData}>
                <HomeIcon className='icon' />
                <span>Trang chủ</span>
              </div>
            </Link>
            <Link to={`/profile/${user.id}`} className='turnoff-link-style' >
              <div className="user item" onClick={reloadData}>
                <img src={user.avatar} alt="" />
                <span>Trang cá nhân</span>
              </div>
            </Link>
            <div className="item" onClick={() => setUploadPostShow(true)}>
              <AddCircleOutlineIcon className='icon' />
              <span>Đăng bài</span>
            </div>
            <UploadPost show={uploadPostShow} onHide={() => setUploadPostShow(false)} setUploadPostShow={setUploadPostShow} />
          </div>
          <div className='menu-bottom'>
            <div className="dropdown" ref={dropdownRef}>
              <div className="setting item" onClick={toggleDropdown}>
                <div className='icon-wrapper'>
                  <SettingsIcon className='icon' />
                  {props.status === "PASSWORD_CHANGE_REQUIRED" && <div className='piority'/> }
                </div>
                <span>Cài đặt</span>
              </div>
              {dropdownVisible && <div className="dropdown-content">
                <div className='icon-wrapper'  onClick={logout}> <LogoutOutlinedIcon/> Đăng xuất</div>
                <div className='icon-wrapper' onClick={() =>setChangePasswordShow(true)}>
                  <div> <LockPersonIcon/> Đổi mật khẩu</div>
                  {props.status === "PASSWORD_CHANGE_REQUIRED" && <div className='piority-setting'>
                    <div className="piority-item"></div>
                  </div> }
                </div>
                {!darkMode ? <div className='icon-wrapper' onClick = {toggle}> <DarkModeOutlinedIcon/> Chế độ tối</div> : 
                <div className='icon-wrapper' onClick = {toggle}> <LightModeOutlinedIcon/> Chế độ sáng</div>}            
              </div>}
              <ChangePassword show={changePasswordShow} setChangePasswordShow={setChangePasswordShow}/>
            </div>
          </div>
        </div>
      </div>     
    </div>
  )
}
