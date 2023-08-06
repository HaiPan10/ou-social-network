import React, { useContext } from 'react'
import { AuthContext } from '../../context/AuthContext'
import './leftBar.scss'

export const LeftBar = () => {
  const [user, dispatch] = useContext(AuthContext)

  return (
    <div className='leftBar'>
      <div className='container'>
        <div className='menu'>
          <div className="user">
            <img src={require('../../images/default_avatar.png')} alt="" />
            <span>{user.firstName}</span>
          </div>
          <div className="item">
            <img id='friend' />
            <span>Bạn bè</span>
          </div>
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
