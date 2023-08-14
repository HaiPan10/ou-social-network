import { useContext, useEffect, useRef, useState } from "react";
import { Post } from "../../components/post/Post";
import "./profile.scss"
import { AuthContext } from "../../context/AuthContext";
import { load, save } from "react-cookies";
import { useParams } from "react-router-dom";
import Api, { authAPI, endpoints } from "../../configs/Api";
import Loading from "../../components/Loading";
import EditIcon from '@mui/icons-material/Edit';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import MessageIcon from '@mui/icons-material/Message';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { DarkModeContext } from "../../context/DarkModeContext";
import { Form } from "react-bootstrap";
import { PostLayout } from "../../components/postLayout/PostLayout";
import { ReloadContext } from "../../context/ReloadContext";
import ImageModal from '../../components/imageInPost/ImageModal'

const UpdateAvatar = (props) => {
  const {darkMode} = useContext(DarkModeContext)
  const avatar = useRef()
  const [disableButton, setDisableButton] = useState(false);
  const [selectedAvatar, setSelectedAvatar] = useState(null);
  const [user, userDispatch] = useContext(AuthContext)

  const handleAvatarChange = (event) => {
    const selectedFile = event.target.files[0];
    setSelectedAvatar(URL.createObjectURL(selectedFile));
  };

  const close = () => {
    props.setEditAvatarShow(false);
    props.setEditProfileShow(true);
  };

  const updateAvatar = (evt) => {
    evt.preventDefault()
    setDisableButton(true)
    const process = async () => {
      try {
        if (selectedAvatar !== null) {
          let form = new FormData()
          if (avatar.current.files.length > 0)
              form.append("uploadAvatar", avatar.current.files[0])
          let res = await authAPI().post(endpoints['update_avatar'] + `/${props.profileUser.id}`, form, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })
          if (res.status === 200) {
            setDisableButton(false)
            save("current-user", res.data)
            userDispatch({
              "type": "LOGIN", 
              "payload": res.data
            })
          }
        } else {
          setDisableButton(false)
        }
      } catch (ex) {
        
      }
    }
    process()
    close()
  }

  return (
      <Modal
        {...props}
        aria-labelledby="contained-modal-title-vcenter"
        centered
        className={`theme-${darkMode ? "dark" : "light"}`}
      >
        <Form onSubmit={updateAvatar}>
          <Modal.Header closeButton>
            <Modal.Title id="contained-modal-title-vcenter">
              <EditIcon/> Cập nhật ảnh đại diện
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
              <div className="avatar">
                <div className="row-title">
                  <h6>Ảnh đại diện</h6>
                </div>
                <div className="avatar-container">
                  <img src={selectedAvatar || props.profileUser.avatar} alt="" />
                </div>
                <div style={{display:"flex", justifyContent:"center"}}><input type="file" ref={avatar} name="uploadAvatar" onChange={handleAvatarChange} accept="image/png, image/jpeg"/></div>
              </div>
          </Modal.Body>
          <Modal.Footer>
            <Button onClick={close} className="close-btn">Đóng</Button>
            <Button type="submit" disabled={disableButton}>Lưu</Button>
          </Modal.Footer>
        </Form>
      </Modal>
  );
}

const UpdateCover = (props) => {
  const {darkMode} = useContext(DarkModeContext)
  const cover = useRef()
  const [disableButton, setDisableButton] = useState(false);
  const [selectedCover, setSelectedCover] = useState(null);
  const [user, userDispatch] = useContext(AuthContext)

  const handleCoverChange = (event) => {
    const selectedFile = event.target.files[0];
    setSelectedCover(URL.createObjectURL(selectedFile));
  };

  const close = () => {
    props.setEditCoverShow(false);
    props.setEditProfileShow(true);
  };

  const updateCover = (evt) => {
    evt.preventDefault()
    setDisableButton(true)
    const process = async () => {
      if (selectedCover !== null) {
        try {
          let form = new FormData()
          if (cover.current.files.length > 0)
              form.append("uploadCover", cover.current.files[0])
          let res = await authAPI().post(endpoints['update_cover'] + `/${props.profileUser.id}`, form, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })
          if (res.status === 200) {
            setDisableButton(false)
            save("current-user", res.data)
            userDispatch({
              "type": "LOGIN", 
              "payload": res.data
            })
          }
        } catch (ex) {
          
        }
      } else {
        setDisableButton(false)
      }
    }
    process()
    close()
  }

  return (
      <Modal
        {...props}
        aria-labelledby="contained-modal-title-vcenter"
        centered
        className={`theme-${darkMode ? "dark" : "light"}`}
      >
        <Form onSubmit={updateCover}>
          <Modal.Header closeButton>
            <Modal.Title id="contained-modal-title-vcenter">
              <EditIcon/> Cập nhật ảnh bìa
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
              <div className="cover">
                <div className="row-title">
                  <h6>Ảnh bìa</h6>
                </div>
                <div className="cover-container">
                  <img src={selectedCover || props.profileUser.coverAvatar} alt="" />
                </div>
                <div style={{display:"flex", justifyContent:"center"}}><input type="file" ref={cover} name="uploadCover" onChange={handleCoverChange} accept="image/png, image/jpeg"/></div>
              </div>
          </Modal.Body>
          <Modal.Footer>
            <Button onClick={close} className="close-btn">Đóng</Button>
            <Button type="submit" disabled={disableButton}>Lưu</Button>
          </Modal.Footer>
        </Form>
      </Modal>
  );
}

const UpdateInformation = (props) => {
  const {darkMode} = useContext(DarkModeContext)
  const [disableButton, setDisableButton] = useState(false);
  const [user, userDispatch] = useContext(AuthContext)
  const [updateUser, setUpdateUser] = useState({
    "dob": "",
  })

  const close = () => {
    props.setEditInformationShow(false);
    props.setEditProfileShow(true);
  };

  const updateInformation = (evt) => {
    evt.preventDefault()
    setDisableButton(true)
    const process = async () => {
      try {
        let res = await authAPI().patch(endpoints['update_information'] + `/${props.profileUser.id}`, {
          "dob": updateUser.dob
        })
        if (res.status === 200) {
          setDisableButton(false)
          save("current-user", res.data)
          userDispatch({
            "type": "LOGIN", 
            "payload": res.data
          })
        }
      } catch (ex) {
        
      }
    }

    if (updateUser.dob !== "" && updateUser.dob !== props.profileUser.dob) {
      process()
      close()
    } else {
      setDisableButton(false)
    }
  }

  return (
      <Modal
        {...props}
        aria-labelledby="contained-modal-title-vcenter"
        centered
        className={`theme-${darkMode ? "dark" : "light"}`}
      >
        <Form onSubmit={updateInformation}>
          <Modal.Header closeButton>
            <Modal.Title id="contained-modal-title-vcenter">
              <EditIcon/> Cập nhật thông tin
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <div className="user-info">
              <div className="info-row">
                <div className="info-title">Họ và tên:</div>
                <div>{props.profileUser.lastName} {props.profileUser.firstName}</div>
              </div>
              <div className="info-row">
                <div className="info-title">Ngày tháng năm sinh:</div>
                <div><input type="date" onChange={(e) => 
                  {
                    const selectedDateTime = e.target.value;
                    setUpdateUser(updateUser => ({ ...updateUser, dob: selectedDateTime }));
                  }
                } /></div>
              </div>
            </div>
          </Modal.Body>
          <Modal.Footer>
            <Button onClick={close} className="close-btn">Đóng</Button>
            <Button type="submit" disabled={disableButton}>Lưu</Button>
          </Modal.Footer>
        </Form>
      </Modal>
  );
}

const EditProfile = (props) => {
  const {darkMode} = useContext(DarkModeContext)
  const [user, userDispatch] = useContext(AuthContext)

  const editAvatar = () => {
    props.setEditAvatarShow(true);
    props.setEditProfileShow(false);
  };
  const editCover = () => {
    props.setEditCoverShow(true);
    props.setEditProfileShow(false);
  };
  const editInformation = () => {
    props.setEditInformationShow(true);
    props.setEditProfileShow(false);
  };
  return (
      <Modal
        {...props}
        aria-labelledby="contained-modal-title-vcenter"
        centered
        className={`theme-${darkMode ? "dark" : "light"}`}
      >
        <Modal.Header closeButton>
          <Modal.Title id="contained-modal-title-vcenter">
            <EditIcon/> Chỉnh sửa trang cá nhân
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div className="avatar">
            <div className="row-title">
              <h6>Ảnh đại diện</h6>
              <div className="btn-edit" onClick={editAvatar}>Chỉnh sửa</div>
            </div>
            <div className="avatar-container">
              {props.profileUser.id === user.id ? (
                <img src={user.avatar} alt="" className="profilePic" />
              ) : (
                <img src={props.profileUser.avatar} alt="" className="profilePic" />
              )}
            </div>
          </div>
          <div className="cover">
            <div className="row-title">
              <h6>Ảnh bìa</h6>
              <div className="btn-edit" onClick={editCover}>Chỉnh sửa</div>
            </div>
            <div className="cover-container">
              {props.profileUser.id === user.id ? (
                <img src={user.coverAvatar} alt="" className="cover" />
              ) : (
                  <img src={props.profileUser.coverAvatar} alt="" className="cover" />
              )}
            </div>
          </div>
          <div className="user-information">
            <div className="row-title">
              <h6>Thông tin cá nhân</h6>
              <div className="btn-edit" onClick={editInformation}>Chỉnh sửa</div>
            </div>
            <div className="user-info">
              <div className="info-row">
                <div className="info-title">Họ và tên:</div>
                <div>{props.profileUser.lastName} {props.profileUser.firstName}</div>
              </div>
              <div className="info-row">
                <div className="info-title">Ngày tháng năm sinh:</div>
                {props.profileUser.dob === null ? (
                  <div>Chưa cập nhật</div>
                ) : props.profileUser.id === user.id ? (
                  <div>{user.dob}</div>
                ) : (
                  <div>{props.profileUser.dob}</div>
                )}
              </div>
            </div>
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={props.onHide}>Đóng</Button>
        </Modal.Footer>
      </Modal>
  );
}

export const Profile = () => {
  const { id } = useParams();
  const [user, userDispatch] = useContext(AuthContext)
  const [role, setRole] = useState(null)
  const [profileUser, setProfileUser] = useState(null)
  const [isValidUser, setValidUser] = useState(true)  
  const [editProfileShow, setEditProfileShow] = useState(false)
  const [editAvatarShow, setEditAvatarShow] = useState(false)
  const [editCoverShow, setEditCoverShow] = useState(false)
  const [editInformationShow, setEditInformationShow] = useState(false)
  const [posts, setPosts] = useState()
  const { reload } = useContext(ReloadContext)
  const { reloadData } = useContext(ReloadContext)
  const [showAvatarModal, setShowAvatarModal] = useState(false);
  const [showCoverModal, setShowCoverModal] = useState(false);

  const toggleAvatarModal = () => setShowAvatarModal(!showAvatarModal);
  const toggleCoverModal = () => setShowCoverModal(!showCoverModal);

  useEffect(() => {
    const loadProfile = async () => {
      try {
        let res = await authAPI().get(endpoints['profile'] + `/${id}`)
        setRole(res.data.role)
        setProfileUser(res.data.user)       
        setPosts(res.data.posts)
      } catch (ex) {
        setValidUser(false)
      }
    }

    window.scrollTo({ top: 0, left: 0, behavior: 'instant' })
    loadProfile()
  }, [id, reload])

  if ((profileUser === null || role === null) && isValidUser === true) {
    return <div className="profile">
      <div className="loading">
        <Loading/>
      </div>
    </div>
  } else if (isValidUser === false) {
    return <div className="profile">
      <div className="page_404">
        Bạn hiện không xem được nội dung này
      </div>
    </div>
  }
  return (
    <div className="profile">
      <div className="images">
        {profileUser.id === user.id ? (
          <>
            <img src={user.coverAvatar} alt="" className="cover" onClick={toggleCoverModal}/>
            {showCoverModal && (
              <ImageModal
                  images={[user.coverAvatar]}
                  index={0}
                  onClose={toggleCoverModal}
              />
            )}
          </>
        ) : (
          <>
            <img src={profileUser.coverAvatar} alt="" className="cover" onClick={toggleCoverModal}/>
            {showCoverModal && (
              <ImageModal
                  images={[profileUser.coverAvatar]}
                  index={0}
                  onClose={toggleCoverModal}
              />
            )}
          </>
        )}
      </div>
      <div className="profileContainer">
        <div className="uInfo">
          <div className="left">
            {profileUser.id === user.id ? (
              <>
                <img src={user.avatar} alt="" className="profilePic" onClick={toggleAvatarModal} />
                {showAvatarModal && (
                    <ImageModal
                        images={[user.avatar]}
                        index={0}
                        onClose={toggleAvatarModal}
                    />
                )}
              </>
            ) : (
              <>
                <img src={profileUser.avatar} alt="" className="profilePic" />
                {showAvatarModal && (
                  <ImageModal
                      images={[profileUser.avatar]}
                      index={0}
                      onClose={toggleAvatarModal}
                  />
                )}
              </>
            )}
            
            <span className="user-name">{profileUser.lastName} {profileUser.firstName}</span>
            {role.id === 1 ? (
              <span className="user-role">Cựu sinh viên</span>
            ) : role.id === 2 ? (
              <span className="user-role">Giảng viên</span>
            ) : (
              <span className="user-role">Quản trị viên</span>
            )}
          </div>
          <div className="center">
          </div>
          {profileUser.id !== user.id ? 
            <div className="right">
              <button className="softColor"><MessageIcon/> Nhắn tin</button>
            </div> :
            <div className="right">
              <button className="softColor" onClick={() => setEditProfileShow(true)}><EditIcon/> Sửa hồ sơ</button>
            </div>
          }
          <EditProfile show={editProfileShow} onHide={() => setEditProfileShow(false)} profileUser={profileUser}
            setEditProfileShow={setEditProfileShow} setEditAvatarShow={setEditAvatarShow} setEditCoverShow={setEditCoverShow} setEditInformationShow={setEditInformationShow}
          />
          <UpdateAvatar show={editAvatarShow} onHide={() => setEditAvatarShow(false)} profileUser={profileUser}
            setEditProfileShow={setEditProfileShow} setEditAvatarShow={setEditAvatarShow}
          />
          <UpdateCover show={editCoverShow} onHide={() => setEditCoverShow(false)} profileUser={profileUser}
            setEditProfileShow={setEditProfileShow} setEditCoverShow={setEditCoverShow}
          />
          <UpdateInformation show={editInformationShow} onHide={() => setEditInformationShow(false)} profileUser={profileUser}
            setEditProfileShow={setEditProfileShow} setEditInformationShow={setEditInformationShow}
          />
        </div>      
      </div>
      <div className="posts">
        {profileUser.id === user.id ? <PostLayout/> : <></>}        
        {posts.map(post=>(
          <Post post={post} key={post.id} posts={posts} setPosts={setPosts}/>
        ))}
    </div>
  </div>
  )
}
