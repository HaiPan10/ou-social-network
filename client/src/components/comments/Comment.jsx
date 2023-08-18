import { useContext, useEffect, useState } from 'react'
import './comment.scss'
import { AuthContext } from '../../context/AuthContext'
import { authAPI, endpoints } from '../../configs/Api'
import Loading from '../Loading'
import Moment from 'react-moment';
import 'moment/locale/vi'
import SendIcon from '@mui/icons-material/Send';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { Link } from 'react-router-dom'
import { UploadComment } from './UploadComment'
import MoreHorizIcon from "@mui/icons-material/MoreHoriz";
import { EditComment } from './EditComment'
import { DarkModeContext } from '../../context/DarkModeContext'
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import DeleteOutlineOutlinedIcon from '@mui/icons-material/DeleteOutlineOutlined';

const DeleteConfirmation = (props) => {
    const {darkMode} = useContext(DarkModeContext)
    const deleteComment = (evt) => {
      evt.preventDefault()
      const process = async () => {
        props.onHide()
        props.setComments(props.comments.filter(comment => comment.id !== props.comment.id));
        props.setComment(null);
        try {
          let res = await authAPI().delete(endpoints['comment'] + `/${props.comment.id}`)
          if (res.status === 204) {
            // reloadData()
          }
        } catch (ex) {
          
        }
      }

      process()
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
              <DeleteOutlineOutlinedIcon/> Xóa bình luận
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            Bạn có chắc chắn muốn xóa bình luận này ?
          </Modal.Body>
          <Modal.Footer>
            <Button onClick={props.onHide} className="close-btn">Đóng</Button>
            <Button type="submit" onClick={deleteComment}>Xác nhận xóa</Button>
          </Modal.Footer>
        </Modal>
    );
  }

export const Comment = (props) => {
    const [user, dispatch] = useContext(AuthContext)
    const [comments, setComments] = useState(null)
    // const [content, setContent] = useState('')
    const [reloadComment, setReloadComment] = useState(true)
    const [editCommentId, setEditCommentId] = useState(null)
    const [deleteCommentShow, setDeleteCommentShow] = useState(false)
    const [commment, setComment] = useState(null)

    useEffect(() => {
        const loadComment = async () => {
          try {
            let res = await authAPI().get(endpoints['comment'] + `/${props.post.id}`)
            if (res.status === 200) {
                setComments(res.data)
                setReloadComment(false)
            }
          } catch (ex) {
          }
        }
    
        if (reloadComment) {
            loadComment()
        }
    }, [props.post.Id, reloadComment])

    return (
        <div className='comments'>
            <UploadComment post={props.post} setReloadComment={setReloadComment}/>
            { comments !== null ? 
                <>
                    {comments.map((comment) => {
                        const formattedDate = comment.createdDate.replace(/(\d{2})-(\d{2})-(\d{4}) (\d{2}:\d{2}:\d{2})/, '$3-$2-$1 $4')
                        return (
                            <div key={comment.id}>
                                {editCommentId === comment.id ? 
                                    <div className="edit-comment">
                                        <EditComment setEditCommentId={setEditCommentId} post={props.post} comment={comment} setReloadComment={setReloadComment}/>
                                    </div>
                                :
                                    <div className="comment">
                                        <Link to={`/profile/${comment.userId.id}`}>
                                            <img src={comment.userId.avatar} alt="" />
                                        </Link>
                                        <div className='comment-content'>
                                            <div className="info">
                                                <Link to={`/profile/${comment.userId.id}`} style={{textDecoration:"none", color:"inherit"}}>
                                                <span>{comment.userId.lastName} {comment.userId.firstName}</span>
                                                </Link>
                                                <p>{comment.content}</p>
                                            </div>
                                            <div className='comment-action'>
                                                <span className="date"><Moment locale="vi" fromNow>{formattedDate}</Moment></span>
                                                {(props.post.userId.id === user.id || comment.userId.id === user.id) &&
                                                <>
                                                    <div className="dropdown">
                                                        <MoreHorizIcon className='comment-more'/>
                                                    <div className="dropdown-content">
                                                        { props.post.userId.id === user.id ? <>
                                                            {comment.userId.id === user.id && <span onClick={() => setEditCommentId(comment.id)}><EditIcon/> Sửa</span>}
                                                            <span onClick={() => {
                                                                setDeleteCommentShow(true)
                                                                setComment(comment)
                                                            }}> <DeleteIcon/> Xóa</span>
                                                        </> : comment.userId.id === user.id && <>
                                                            <span><EditIcon onClick={() => setEditCommentId(comment.id)}/> Sửa</span>
                                                            <span onClick={() => {
                                                                setDeleteCommentShow(true)
                                                                setComment(comment)
                                                            }}> <DeleteIcon/> Xóa</span>
                                                        </>}
                                                    </div>
                                                </div>
                                                </>}
                                            </div>
                                        </div>
                                    </div>
                                }
                            </div>
                        )
                    })}
                    <DeleteConfirmation show={deleteCommentShow} onHide={() => setDeleteCommentShow(false)} comment={commment} comments={comments} setComment={setComment} setComments={setComments} />
                </>
                :
                <div className="loading">
                    <Loading/>
                </div>
            }
        </div>
    )
}
