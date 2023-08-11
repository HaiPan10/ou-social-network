import { useContext, useEffect, useState } from "react"
import "./postLayout.scss"
import { AuthContext } from "../../context/AuthContext"
import { Form, ToggleButton } from "react-bootstrap";
import { DarkModeContext } from "../../context/DarkModeContext";
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { MDBSwitch } from 'mdb-react-ui-kit';
import AddPhotoAlternateIcon from '@mui/icons-material/AddPhotoAlternate';

const UploadPost = (props) => {
  const {darkMode} = useContext(DarkModeContext)
  const [disableButton, setDisableButton] = useState(false);
  const [user, userDispatch] = useContext(AuthContext)
  const [isActiveComment, setActiveComment] = useState(true)
  const [selectedFiles, setSelectedFiles] = useState([]);

  const handleFileChange = (e) => {
    const files = Array.from(e.target.files);
    setSelectedFiles(files);
  };

  const handleClick = () => setActiveComment(!isActiveComment)

  const uploadPost = (evt) => {
    evt.preventDefault()
    setDisableButton(true)
    const process = async () => {
      try {
        // let res = await authAPI().post(endpoints['update_information'] + `/${props.profileUser.id}`, {
        //   "dob": updateUser.dob
        // })
        // if (res.status === 200) {
        //   setDisableButton(false)
        //   save("current-user", res.data)
        //   close()
        //   userDispatch({
        //     "type": "LOGIN", 
        //     "payload": res.data
        //   })
        // }
      } catch (ex) {
        
      }
    }

    process()
    // if () {
    //   process()
    // } else {
    //   setDisableButton(false)
    // }
  }

  return (
      <Modal
        {...props}
        aria-labelledby="contained-modal-title-vcenter"
        centered
        className={`theme-${darkMode ? "dark" : "light"}`}
        id='modal-post'
      >
        <Form onSubmit={uploadPost}>
          <Modal.Header closeButton>
            <Modal.Title id="contained-modal-title-vcenter">
              Tạo bài viết
            </Modal.Title>
          </Modal.Header>
          <Modal.Body className="post-body">
            <div className="user">
              <div className="avatar">
                {user.avatar===null ? (
                  <img src={require('../../images/default_avatar.png')} />
                ) : ( 
                  <img src={user.avatar} alt="" />
                )}
              </div>
              <div className="info">
                <div>{user.lastName} {user.firstName}</div>
                <div className="comment-toggle">
                  <MDBSwitch id='flexSwitchCheckDefault' checked={isActiveComment} onClick={handleClick} label='Cho phép bình luận'/>
                </div>
              </div>
            </div>
            <div className="content">
              <textarea placeholder="Chia sẻ trạng thái của bạn" rows={3} maxlength="255"/>
            </div>
            <div className="image">
              {/* <input type="file" multiple accept="image/*"/> */}
              <label for="fileInput" class="custom-file-input"><AddPhotoAlternateIcon/> Chọn hình ảnh</label>
              <input type="file" id="fileInput" multiple accept="image/*" />
            </div>
          </Modal.Body>
          <Modal.Footer className="post-footer">
            <Button className="submit" type="submit">Đăng</Button>
          </Modal.Footer>
        </Form>
      </Modal>
  );
}

export const PostLayout = () => {
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
        <UploadPost show={uploadPostShow} onHide={() => setUploadPostShow(false)} />
    </div>
  )
}
