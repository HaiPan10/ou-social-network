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
import SearchIcon from '@mui/icons-material/Search';
import { Search } from '../search/Search';
import { SearchContext } from '../../context/SearchContext';

export const LeftBar = (props) => {
  const [user, dispatch] = useContext(AuthContext)
  const { toggle } = useContext(DarkModeContext)
  const {darkMode} = useContext(DarkModeContext)
  const [uploadPostShow, setUploadPostShow] = useState(false)
  const [dropdownVisible, setDropdownVisible] = useState(false)
  const dropdownRef = useRef(null)
  const { reloadData } = useContext(ReloadContext)
  const [changePasswordShow, setChangePasswordShow] = useState(false)
  const { showSearch } = useContext(SearchContext)
  const { toggleSearch } = useContext(SearchContext)

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
    <div className='leftBar' style={{flex: showSearch ? "3": "2"}}>
      <div className='container' style={{width: showSearch ? "20%": "100%"}}>
        <div className='menu'>
          <div className='menu-top'>
            <div className='item'>
              <div className="logo" >
                <img/>
              </div>
            </div>
            <Link to="/" className='turnoff-link-style'>
              <div className="item" onClick={reloadData} style={{justifyContent: showSearch ? "center": ""}}>
                <HomeIcon className='icon' />
                <span style={{display: showSearch ? "none": "block"}}>Trang chủ</span>
              </div>
            </Link>
            <Link to={`/profile/${user.id}`} className='turnoff-link-style' >
              <div className="user item" onClick={reloadData} style={{justifyContent: showSearch ? "center": ""}}>
                <img src={user.avatar} alt="" />
                <span style={{display: showSearch ? "none": "block"}}>Trang cá nhân</span>
              </div>
            </Link>
            <div className="item" onClick={() => setUploadPostShow(true)} style={{justifyContent: showSearch ? "center": ""}}>
              <AddCircleOutlineIcon className='icon' />
              <span style={{display: showSearch ? "none": "block"}}>Đăng bài</span>
            </div>
            <Link to={`/profile/1`} className='turnoff-link-style' >
              <div className="user item" onClick={reloadData}  style={{justifyContent: showSearch ? "center": ""}}>
                <img src="https://ou.edu.vn/wp-content/uploads/2019/01/OpenUniversity.png" alt="" />
                <span style={{display: showSearch ? "none": "block"}} >Trang quản trị viên</span>
              </div>
            </Link>
            <div className={showSearch ? 'item active' : 'item'} onClick={toggleSearch}  style={{justifyContent: showSearch ? "center": ""}}>
              <SearchIcon className='icon' />
              <span style={{display: showSearch ? "none": "block"}}>Tìm kiếm</span>
            </div>
            <UploadPost show={uploadPostShow} onHide={() => setUploadPostShow(false)} setUploadPostShow={setUploadPostShow} />
          </div>
          <div className='menu-bottom'>
            <div className="dropdown" ref={dropdownRef}>
              <div className="setting item" onClick={toggleDropdown}  style={{justifyContent: showSearch ? "center": ""}}>
                <div className='icon-wrapper'>
                  <SettingsIcon className='icon' />
                  {props.status === "PASSWORD_CHANGE_REQUIRED" && <div className='piority'/> }
                </div>
                {!showSearch && <span>Cài đặt</span>}
              </div>
              {dropdownVisible && <div className="dropdown-content">
                <div className='icon-wrapper'  onClick={logout}  style={{justifyContent: showSearch ? "center": ""}}> <LogoutOutlinedIcon/> <span style={{display: showSearch ? "none": "block"}}>Đăng xuất</span></div>
                <div className='icon-wrapper' onClick={() =>setChangePasswordShow(true)}  style={{justifyContent: showSearch ? "center": ""}}>
                  <div > <LockPersonIcon/> <span style={{display: showSearch ? "none": ""}}>Đổi mật khẩu</span></div>
                  {props.status === "PASSWORD_CHANGE_REQUIRED" && <div className='piority-setting'>
                    <div className="piority-item"></div>
                  </div> }
                </div>
                {!darkMode ? <div className='icon-wrapper' onClick = {toggle}  style={{justifyContent: showSearch ? "center": ""}}> <DarkModeOutlinedIcon/> <span style={{display: showSearch ? "none": ""}}>Chế độ tối</span></div> : 
                <div className='icon-wrapper' onClick = {toggle}  style={{justifyContent: showSearch ? "center": ""}}> <LightModeOutlinedIcon/> <span style={{display: showSearch ? "none": ""}}>Chế độ sáng</span></div>}            
              </div>}
              <ChangePassword show={changePasswordShow} setChangePasswordShow={setChangePasswordShow}/>
            </div>
          </div>
        </div>
      </div>
      <div style={{width: showSearch ? "80%": "0%", transition: ".1s"}}>
        { showSearch && <Search/>}
      </div>
    </div>
  )
}
