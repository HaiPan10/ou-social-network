import { useContext } from 'react'
import './comment.scss'
import { AuthContext } from '../../context/AuthContext'

export const Comment = (props) => {
    const [user, dispatch] = useContext(AuthContext)
    return (
        <div className='comments'>
            <div className="write">
                <img src={user.avatar} alt="" />
                <input type="text" placeholder="Viết bình luận" />
                <button>Gửi</button>
            </div>
            {/* {props.comments.map((comment) => (
                <div className="comment">
                    <img src={comment.avatar} alt="" />
                    <div className='content'>
                        <div className="info">
                            <span>{comment.name}</span>
                            <p>{comment.desc}</p>
                        </div>
                        <div className='comment-action'>
                            <span>Thích</span>
                            <span>Phản hồi</span>
                            <span className="date">1 phút trước</span>
                        </div>
                    </div>
                </div>
            ))} */}
        </div>
    )
}
