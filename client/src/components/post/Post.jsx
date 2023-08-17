import React, { useContext, useEffect, useRef, useState } from 'react'
import "./post.scss"
import FavoriteBorderOutlinedIcon from "@mui/icons-material/FavoriteBorderOutlined";
import FavoriteOutlinedIcon from "@mui/icons-material/FavoriteOutlined";
import TextsmsOutlinedIcon from "@mui/icons-material/TextsmsOutlined";
import ShareOutlinedIcon from "@mui/icons-material/ShareOutlined";
import MoreHorizIcon from "@mui/icons-material/MoreHoriz";
import { Link } from "react-router-dom";
import { Comment } from '../comments/Comment';
import Moment from 'react-moment';
import ImageInPost from '../imageInPost/ImageInPost'
import { AuthContext } from '../../context/AuthContext';
import 'moment/locale/vi'
import SpeakerNotesOffOutlinedIcon from '@mui/icons-material/SpeakerNotesOffOutlined';
import EditNoteOutlinedIcon from '@mui/icons-material/EditNoteOutlined';
import DeleteOutlineOutlinedIcon from '@mui/icons-material/DeleteOutlineOutlined';
import EditPost from '../postLayout/EditPost';
import { ReloadContext } from '../../context/ReloadContext';
import { authAPI, endpoints } from '../../configs/Api';
import { DarkModeContext } from '../../context/DarkModeContext';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { Form } from "react-bootstrap";

const DeleteConfirmation = (props) => {
    const {darkMode} = useContext(DarkModeContext)
    // const { reloadData } = useContext(ReloadContext)
    const [user, userDispatch] = useContext(AuthContext)
  
    const deletePost = (evt) => {
      evt.preventDefault()
      const process = async () => {
        props.onHide()
        props.setPosts(props.posts.filter(post => post.id !== props.post.id));
        try {
          let res = await authAPI().delete(endpoints['delete_post'] + `/${props.post.id}`)
          if (res.status === 204) {
            // reloadData()
          }
        } catch (ex) {
          
        }
      }

      if (user.id === props.post.userId.id) {
        process()
      }
    }
  
    return (
        <Modal
          {...props}
          aria-labelledby="contained-modal-title-vcenter"
          centered
          className={`theme-${darkMode ? "dark" : "light"}`}
        >
          <Modal.Header closeButton>
            <Modal.Title id="contained-modal-title-vcenter">
              <DeleteOutlineOutlinedIcon/> Xóa bài đăng
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            Bạn có chắc chắn muốn xóa bài đăng này ?
          </Modal.Body>
          <Modal.Footer>
            <Button onClick={props.onHide} className="close-btn">Đóng</Button>
            <Button type="submit" onClick={deletePost}>Xác nhận xóa</Button>
          </Modal.Footer>
        </Modal>
    );
  }

export const Post = ({post, posts, setPosts}) => {
    const [commentOpen, setCommentOpen] = useState(false)
    const liked = false;
    const images = post.imageInPostList.map(image => image.imageUrl);
    const [user, userDispatch] = useContext(AuthContext)
    const [dropdownVisible, setDropdownVisible] = useState(false)
    const [editPostShow, setEditPostShow] = useState(false)
    const [deletePostShow, setDeletePostShow] = useState(false)
    const dropdownRef = useRef(null);

    const toggleDropdown = () => {
        setDropdownVisible(!dropdownVisible);
    }

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

    const formattedDate = post.createdAt.replace(/(\d{2})-(\d{2})-(\d{4}) (\d{2}:\d{2}:\d{2})/, '$3-$2-$1 $4');

    return (
        <div className='post'>
            <div className="postContainer">
                <div className="user">
                    <div className="userInfo">
                        <Link to={`/profile/${post.userId.id}`}>
                          <img src={post.userId.avatar} alt=""/>
                        </Link>
                        
                        <div className="details">
                            <Link to={`/profile/${post.userId.id}`} style={{textDecoration:"none", color:"inherit"}}>
                                <span className='name'>{post.userId.lastName} {post.userId.firstName}</span>
                            </Link>
                            <span className='date'><Moment locale="vi" fromNow>{formattedDate}</Moment></span>
                        </div>
                    </div>
                    {post.userId.id===user.id &&
                        <>
                            <div className="dropdown" ref={dropdownRef} onClick={toggleDropdown}>
                                <div className='btn-edit' ><MoreHorizIcon/></div>
                                {dropdownVisible && <div className="dropdown-content">
                                    <div onClick={() => setEditPostShow(true)}><EditNoteOutlinedIcon/> Chỉnh sửa</div>
                                    <div onClick={() => setDeletePostShow(true)}><DeleteOutlineOutlinedIcon/> Xóa</div>
                                </div>}
                            </div>
                            <EditPost show={editPostShow} onHide={() => setEditPostShow(false)} setEditPostShow={setEditPostShow} post={post} />
                            <DeleteConfirmation show={deletePostShow} onHide={() => setDeletePostShow(false)} post={post} posts={posts} setPosts={setPosts}/>
                        </>
                    }
                </div>
                <div className="content">
                    <p>{post.content}</p>
                    <ImageInPost hideOverlay={true} images={images} />
                    {/* <img src={post.img} /> */}
                </div>
                <div className="info">
                    <div className="item">
                        {liked? <FavoriteOutlinedIcon/> : <FavoriteBorderOutlinedIcon/>}
                        {post.reactionTotal} tương tác
                    </div>
                    {post.isActiveComment ? <div className="item" onClick={()=>setCommentOpen(!commentOpen)}>
                        <TextsmsOutlinedIcon />
                        {post.commentTotal} bình luận
                    </div> :
                    <div className='lockComment'> <SpeakerNotesOffOutlinedIcon/> Bình luận bị khóa! </div> }
                    {/* <div className="item">
                        <ShareOutlinedIcon />
                        Share
                    </div> */}
                </div>
                {post.isActiveComment && commentOpen && <Comment post={post}/>}
            </div>
        </div>
  )
}
