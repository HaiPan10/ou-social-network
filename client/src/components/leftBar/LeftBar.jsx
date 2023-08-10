import React, { useContext } from 'react'
import { AuthContext } from '../../context/AuthContext'
import './leftBar.scss'
import { Link } from 'react-router-dom'
import HomeIcon from '@mui/icons-material/Home';

export const LeftBar = () => {
  const [user, dispatch] = useContext(AuthContext)

  return (
    <div className='leftBar'>
      <div className='container'>
        <div className='menu'>
          <Link to={`/profile/${user.id}`} className='turnoff-link-style' >
            <div className="user">
              <img src={user.avatar} alt="" />
              <span>{user.firstName}</span>
            </div>
          </Link>
          <Link to="/" className='turnoff-link-style'>
            <div className="item">
              <HomeIcon className='icon' />
              <span>Trang chủ</span>
            </div>
          </Link>
          {/* <div className="item">
            <img id='friend' />
            <span>Bạn bè</span>
          </div> */}
          {/* <div className="item">
            <img src={require('../../images/friends.png')} alt="" />
            <span>Bạn bè</span>
          </div>
          <div className="item">
            <img src={require('../../images/friends.png')} alt="" />
            <span>Bạn bè</span>
          </div> */}
        </div>
        <hr/>
        {/* <div className='menu'>
          <span>Shortcuts</span>
          <div className="item">
            <img src={require('../../images/friends.png')} alt="" />
            <span>Bạn bè</span>
          </div>
          <div className="item">
            <img src={require('../../images/friends.png')} alt="" />
            <span>Bạn bè</span>
          </div>
        </div>
        <hr/>
        <div className='menu'>
          <span>Shortcuts</span>
          <div className="item">
            <img src={require('../../images/friends.png')} alt="" />
            <span>Bạn bè</span>
          </div>
          <div className="item">
            <img src={require('../../images/friends.png')} alt="" />
            <span>Bạn bè</span>
          </div>
        </div> */}
      </div>     
    </div>
  )
}
