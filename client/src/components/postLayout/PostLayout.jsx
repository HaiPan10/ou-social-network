import { useContext, useEffect, useRef, useState } from "react"
import "./postLayout.scss"
import { AuthContext } from "../../context/AuthContext"
import UploadPost from "./UploadPost"

export const PostLayout = (props) => {
  const [user, userDispatch] = useContext(AuthContext)
  const [uploadPostShow, setUploadPostShow] = useState(false)
  return (
    <div className='post-layout'>
        <div className="postLayoutContainer">
            <div className="avatar">
              {user.avatar===null ? (
                <img src={require('../../images/default_avatar.png')} />
              ) : (
                <img src={user.avatar} alt="" />
              )}
            </div>
            <div className="show-modal" user={user} onClick={() => setUploadPostShow(true)}>Chia sẻ trạng thái của bạn</div>
        </div>
        <UploadPost show={uploadPostShow} onHide={() => setUploadPostShow(false)} setReloadData={props.setReloadData} setUploadPostShow={setUploadPostShow} />
    </div>
  )
}
