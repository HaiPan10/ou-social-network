import React, { useState } from 'react'
import "./post.scss"
import FavoriteBorderOutlinedIcon from "@mui/icons-material/FavoriteBorderOutlined";
import FavoriteOutlinedIcon from "@mui/icons-material/FavoriteOutlined";
import TextsmsOutlinedIcon from "@mui/icons-material/TextsmsOutlined";
import ShareOutlinedIcon from "@mui/icons-material/ShareOutlined";
import MoreHorizIcon from "@mui/icons-material/MoreHoriz";
import { Link } from "react-router-dom";
import { Comment } from '../comments/Comment';

export const Post = ({post}) => {
    const [commentOpen, setCommentOpen] = useState(false)
    const liked = false;

    return (
        <div className='post'>
            <div className="container">
                <div className="user">
                    <div className="userInfo">
                        <img src={post.avatar} />
                        <div className="details">
                            <Link to={`/profile/${post.userId}`} style={{textDecoration:"none", color:"inherit"}}>
                                <span className='name'>{post.name}</span>
                            </Link>
                            <span className='date'>1 phút trước</span>
                        </div>
                    </div>
                    <MoreHorizIcon/>
                </div>
                <div className="content">
                    <p>{post.desc}</p>
                    <img src={post.img} />
                </div>
                <div className="info">
                    <div className="item">
                        {liked? <FavoriteOutlinedIcon/> : <FavoriteBorderOutlinedIcon/>}
                        69 Likes
                    </div>
                    <div className="item" onClick={()=>setCommentOpen(!commentOpen)}>
                        <TextsmsOutlinedIcon />
                        12 Comments
                    </div>
                    <div className="item">
                        <ShareOutlinedIcon />
                        Share
                    </div>
                </div>
                {commentOpen && <Comment comments={post.comments}/>}
            </div>
        </div>
  )
}
