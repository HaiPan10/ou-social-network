import { useContext, useEffect, useState } from 'react'
import './comment.scss'
import { AuthContext } from '../../context/AuthContext'
import { authAPI, endpoints } from '../../configs/Api'
import Loading from '../Loading'
import Moment from 'react-moment';
import 'moment/locale/vi'
import { Form } from 'react-bootstrap'

export const Comment = (props) => {
    const [user, dispatch] = useContext(AuthContext)
    const [comments, setComments] = useState(null)
    const [content, setContent] = useState('')
    const [reloadComment, setReloadComment] = useState(true)

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

    const uploadComment = (evt) => {
        evt.preventDefault()
        const process = async () => {
          try {
            setContent('')          
            let res = await authAPI().post(endpoints['comment'] + `/${props.post.id}` + `/${user.id}`, {
                "content": content
            })
            if (res.status === 201) {
              setReloadComment(true)
            }
          } catch (ex) {
          }
        }

        if (content !== '') {
            process()
        }
    }

    return (
        <div className='comments'>
            <div className="write">
                <img src={user.avatar} alt="" />
                <input type="text" value={content} maxlength="255" onChange={(e) => {setContent(e.target.value)}} placeholder="Viết bình luận" />
                <button onClick={uploadComment}>Gửi</button>
            </div>
            { comments !== null ? 
                <>
                    {comments.map((comment) => {
                        const formattedDate = comment.createdDate.replace(/(\d{2})-(\d{2})-(\d{4}) (\d{2}:\d{2}:\d{2})/, '$3-$2-$1 $4')
                        return (
                            <div className="comment">
                                <img src={comment.userId.avatar} alt="" />
                                <div className='comment-content'>
                                    <div className="info">
                                        <span>{comment.userId.lastName} {comment.userId.firstName}</span>
                                        <p>{comment.content}</p>
                                    </div>
                                    <div className='comment-action'>
                                        <span>Thích</span>
                                        <span>Phản hồi</span>
                                        <span className="date"><Moment locale="vi" fromNow>{formattedDate}</Moment></span>
                                    </div>
                                </div>
                            </div>
                        )
                    })}
                </>
                :
                <div className="loading">
                    <Loading/>
                </div>
            }            
        </div>
    )
}
