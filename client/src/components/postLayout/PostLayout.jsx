import { useContext, useState } from "react"
import "./postLayout.scss"
import { AuthContext } from "../../context/AuthContext"
import { Form } from "react-bootstrap";
import { DarkModeContext } from "../../context/DarkModeContext";
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

const UploadPost = (props) => {
  const {darkMode} = useContext(DarkModeContext)
  const [disableButton, setDisableButton] = useState(false);
  const [user, userDispatch] = useContext(AuthContext)

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
      >
        <Form onSubmit={uploadPost}>
          <Modal.Header closeButton>
            <Modal.Title id="contained-modal-title-vcenter">
              Tạo bài viết
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <div>Bài viết</div>
          </Modal.Body>
          <Modal.Footer>
            <Button onClick={props.onHide}>Đóng</Button>
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
        <div className="container">
            <div className="avatar"><img src={user.avatar} alt="" /></div>
            <div className="show-modal" onClick={() => setUploadPostShow(true)}>Chia sẻ trạng thái của bạn</div>
        </div>
        <UploadPost show={uploadPostShow} onHide={() => setUploadPostShow(false)} />
    </div>
  )
}
