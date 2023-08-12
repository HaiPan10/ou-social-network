import React, { useContext, useEffect, useState } from 'react'
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

export const Post = ({post}) => {
    const [commentOpen, setCommentOpen] = useState(false)
    const liked = false;
    const images = post.imageInPostList.map(image => image.imageUrl);
    const [user, userDispatch] = useContext(AuthContext)

    const formattedDate = post.createdAt.replace(/(\d{2})-(\d{2})-(\d{4}) (\d{2}:\d{2}:\d{2})/, '$3-$2-$1 $4');

    return (
        <div className='post'>
            <div className="postContainer">
                <div className="user">
                    <div className="userInfo">
                        {post.userId.id===null ? (
                            <img src={require('../../images/default_avatar.png')} alt=""/>
                        ) : post.userId.id === user.id ? (
                            <img src={user.avatar} alt=""/>
                        ) : (
                            <img src={post.userId.avatar} alt=""/>
                        )}
                        <div className="details">
                            <Link to={`/profile/${post.userId.id}`} style={{textDecoration:"none", color:"inherit"}}>
                                <span className='name'>{post.userId.lastName} {post.userId.firstName}</span>
                            </Link>
                            <span className='date'><Moment locale="vi" fromNow>{formattedDate}</Moment></span>
                        </div>
                    </div>
                    <MoreHorizIcon/>
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
                    <div> Bình luận bị khóa! </div> }
                    {/* <div className="item">
                        <ShareOutlinedIcon />
                        Share
                    </div> */}
                </div>
                {commentOpen && <Comment comments={post.comments}/>}
            </div>
        </div>
  )
}
