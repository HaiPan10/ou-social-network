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
import MessageIcon from '@mui/icons-material/Message';
import { SideChat, sideChat } from '../sideChat/SideChat';
import { ChatContext } from '../../context/ChatContext';
import 'animate.css'

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
  const { showChat } = useContext(ChatContext)
  const { toggleChat } = useContext(ChatContext)

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

  const navigate = () => {
    reloadData()
    console.log(showChat)
    if (showChat) {
      toggleChat()
    }
    console.log(showChat)
  }

  const setChatTrue = () => {
    if (!showChat) {
      toggleChat()
    }
  }

  return (
    // <div className='leftBar' style={{flex: showSearch ? "3": showChat ? "0.7" : "2"}}>
    <div className='leftBar' style={{flex: showSearch || showChat ? "3" : "2"}}>
      <div className='container' style={{width: showSearch || showChat ? "20%": "100%"}}>
        <div className='menu'>
          <div className='menu-top'>
            <div className='item'>
              <div className="logo" >
                <img/>
              </div>
            </div>
            <Link to="/" className='turnoff-link-style'>
              <div className="item" onClick={navigate} style={{justifyContent: showSearch || showChat ? "center": ""}}>
                <HomeIcon className='icon' />
                <span style={{display: showSearch || showChat ? "none": "block"}}>Trang chủ</span>
              </div>
            </Link>
            <Link to={`/profile/${user.id}`} className='turnoff-link-style' >
              <div className="user item" onClick={navigate} style={{justifyContent: showSearch || showChat ? "center": ""}}>
                <img src={user.avatar} alt="" />
                <span style={{display: showSearch || showChat ? "none": "block"}}>Trang cá nhân</span>
              </div>
            </Link>
            <div className="item" onClick={() => setUploadPostShow(true)} style={{justifyContent: showSearch || showChat ? "center": ""}}>
              <AddCircleOutlineIcon className='icon' />
              <span style={{display: showSearch || showChat ? "none": "block"}}>Đăng bài</span>
            </div>
            <Link to={`/profile/1`} className='turnoff-link-style' >
              <div className="user item" onClick={navigate}  style={{justifyContent: showSearch || showChat ? "center": ""}}>
                <img src="https://ou.edu.vn/wp-content/uploads/2019/01/OpenUniversity.png" alt="" />
                <span style={{display: showSearch || showChat ? "none": "block"}} >Trang quản trị viên</span>
              </div>
            </Link>
            <div className={showSearch ? 'item active' : 'item'} onClick={toggleSearch}  style={{justifyContent: showSearch || showChat ? "center": ""}}>
              <SearchIcon className='icon' />
              <span style={{display: showSearch || showChat ? "none": "block"}}>Tìm kiếm</span>
            </div>
            <Link to={`/chat`} className='turnoff-link-style' >
              <div className={showChat ? 'item active' : 'item'} onClick={setChatTrue} style={{justifyContent: showSearch || showChat ? "center": ""}}>
                <MessageIcon className='icon' />
                <span style={{display: showSearch || showChat ? "none": "block"}}>Nhắn tin</span>
              </div>
            </Link>
            <UploadPost show={uploadPostShow} onHide={() => setUploadPostShow(false)} setUploadPostShow={setUploadPostShow} />
          </div>
          <div className='menu-bottom'>
            <div className="dropdown" ref={dropdownRef}>
              <div className="setting item" onClick={toggleDropdown}  style={{justifyContent: showSearch || showChat ? "center": ""}}>
                <div className='icon-wrapper'>
                  <SettingsIcon className='icon' />
                  {props.status === "PASSWORD_CHANGE_REQUIRED" && <div className='piority'/> }
                </div>
                {!(showSearch || showChat) && <span>Cài đặt</span>}
              </div>
              {dropdownVisible && <div className="dropdown-content">
                <div className='icon-wrapper'  onClick={logout}  style={{justifyContent: showSearch || showChat ? "center": ""}}> <LogoutOutlinedIcon/> <span style={{display: showSearch || showChat ? "none": "block"}}>Đăng xuất</span></div>
                <div className='icon-wrapper' onClick={() =>setChangePasswordShow(true)}  style={{justifyContent: showSearch || showChat ? "center": ""}}>
                  <div > <LockPersonIcon/> <span style={{display: showSearch || showChat ? "none": ""}}>Đổi mật khẩu</span></div>
                  {props.status === "PASSWORD_CHANGE_REQUIRED" && <div className='piority-setting'>
                    <div className="piority-item"></div>
                  </div> }
                </div>
                {!darkMode ? <div className='icon-wrapper' onClick = {toggle}  style={{justifyContent: showSearch || showChat ? "center": ""}}> <DarkModeOutlinedIcon/> <span style={{display: showSearch || showChat ? "none": ""}}>Chế độ tối</span></div> : 
                <div className='icon-wrapper' onClick = {toggle}  style={{justifyContent: showSearch || showChat ? "center": ""}}> <LightModeOutlinedIcon/> <span style={{display: showSearch || showChat ? "none": ""}}>Chế độ sáng</span></div>}            
              </div>}
              <ChangePassword status={props.status} show={changePasswordShow} setChangePasswordShow={setChangePasswordShow}/>
            </div>
          </div>
        </div>
      </div>
      {/* <div style={{width: showSearch || showChat ? "80%": "0%", transition: ".1s"}}>
        { showSearch && 
          <div className="transition-item">
            <Search/>
          </div>
        }
        { showChat && <SideChat status={props.status}/>}
      </div> */}
      <div  style={{width: showSearch || showChat  ? "80%": "0%", position: "relative"}}>
        {/* <div className={`animate__animated animate__fadeInLeft`} style={{width: "100%", transition: "width 1s", position: "absolute", zIndex:"1", left: "0"}}>
          { showSearch && 
              <Search/>
          }
        </div>
        <div style={{width: "100%", transition: "width 1s", position: "absolute", left: "0"}}>
          {showChat && <SideChat status={props.status}/>}
        </div> */}
        
      {showSearch && (
        <div
          className={showSearch ? `animate__animated animate__slideInDown` : `animate__animated animate__slideOutUp`}
          style={{
            width: '100%',
            transition: '1s',
            position: 'absolute',
            zIndex: '1',
            left: '0',
          }}
        >
          <Search />
        </div>
      )}
      {showChat && (
        <div
          className={`animate__animated animate__slideInDown`}
          style={{
            width: '100%',
            transition: '1s',
            position: 'absolute',
            left: '0',
          }}
        >
          <SideChat status={props.status} />
        </div>
      )}
      </div>
      {/* <div style={{width: showSearch  ? "80%": "0%", transition: "1s", position: "absolute", zIndex:"1", left: "80px"}}>
        { showSearch && 
            <Search/>
        }
      </div> */}
      {/* <div style={{width: showChat ? "80%": "0%", transition: ".1s"}}>
        {showChat && <SideChat status={props.status}/>}
      </div> */}
    </div>
  )
}
