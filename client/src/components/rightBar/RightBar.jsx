import React from 'react'
import './rightBar.scss'

export const RightBar = () => {
  return (
    <div className='rightBar'>
      <div className="container">

        <div className="item">
          <span>Lời mời kết bạn</span>
          <div className='user'>
            <div className="userInfo">
              <img src={require('../../images/default_avatar.png')} alt="" />
              <span>User 1</span>
            </div>
            <div className="button">
              <button>Thêm bạn bè</button>
              <button>Bỏ qua</button>
            </div>
          </div>
          <div className='user'>
            <div className="userInfo">
              <img src={require('../../images/default_avatar.png')} alt="" />
              <span>User 2</span>
            </div>
            <div className="button">
              <button>Thêm bạn bè</button>
              <button>Bỏ qua</button>
            </div>
          </div>
        </div>

        {/* <div className="item">
          <span>Gợi ý bạn bè</span>
          <div className='user'>
            <div className="userInfo">
              <img src={require('../../images/default_avatar.png')} alt="" />
              <span>User 1</span>
            </div>
            <div className="button">
              <button>Thêm bạn bè</button>
              <button>Bỏ qua</button>
            </div>
          </div>
          <div className='user'>
            <div className="userInfo">
              <img src={require('../../images/default_avatar.png')} alt="" />
              <span>User 2</span>
            </div>
            <div className="button">
              <button>Thêm bạn bè</button>
              <button>Bỏ qua</button>
            </div>
          </div>
        </div> */}

        {/* <div className="item">
          <span>Hoạt động gần đây</span>
          <div className='user'>
            <div className="userInfo">
              <img src={require('../../images/default_avatar.png')} alt="" />
              <p>
                <span>User 1</span> đã cập nhật ảnh đại diện
              </p>
            </div>
            <span>1 phút trước</span>
          </div>
          <div className='user'>
            <div className="userInfo">
              <img src={require('../../images/default_avatar.png')} alt="" />
              <p>
                <span>User 1</span> đã cập nhật ảnh đại diện
              </p>
            </div>
            <span>1 phút trước</span>
          </div>
          <div className='user'>
            <div className="userInfo">
              <img src={require('../../images/default_avatar.png')} alt="" />
              <p>
                <span>User 1</span> đã cập nhật ảnh đại diện
              </p>
            </div>
            <span>1 phút trước</span>
          </div>
        </div> */}

        <div className="item">
          <span>Liên hệ</span>
          <div className='user'>
            <div className="userInfo">
              <img src={require('../../images/default_avatar.png')} alt="" />
              <div className="online"/>                
              <span>User 1</span>
            </div>
          </div>
          <div className='user'>
            <div className="userInfo">
              <img src={require('../../images/default_avatar.png')} alt="" />
              <div className="online"/>
              <span>User 2</span>
            </div>
          </div>
          <div className='user'>
            <div className="userInfo">
              <img src={require('../../images/default_avatar.png')} alt="" />
              <div className="online"/>
              <span>User 3</span>
            </div>
          </div>
          <div className='user'>
            <div className="userInfo">
              <img src={require('../../images/default_avatar.png')} alt="" />
              <div className="online"/>
              <span>User 4</span>
            </div>
          </div>
          <div className='user'>
            <div className="userInfo">
              <img src={require('../../images/default_avatar.png')} alt="" />
              <div className="online"/>
              <span>User 5</span>
            </div>
          </div>
          <div className='user'>
            <div className="userInfo">
              <img src={require('../../images/default_avatar.png')} alt="" />
              <div className="online"/>
              <span>User 6</span>
            </div>
          </div>
          <div className='user'>
            <div className="userInfo">
              <img src={require('../../images/default_avatar.png')} alt="" />
              <div className="online"/>
              <span>User 7</span>
            </div>
          </div>
          <div className='user'>
            <div className="userInfo">
              <img src={require('../../images/default_avatar.png')} alt="" />
              <div className="online"/>
              <span>User 8</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
