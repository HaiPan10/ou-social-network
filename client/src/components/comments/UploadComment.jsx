import React, { useContext, useState } from 'react'
import './comment.scss'
import { authAPI, endpoints } from '../../configs/Api'
import SendIcon from '@mui/icons-material/Send';
import { AuthContext } from '../../context/AuthContext';
import Input from "@material-ui/core/Input";
import { IconButton, InputAdornment } from '@material-ui/core';

export const UploadComment = (props) => {
    const [user, dispatch] = useContext(AuthContext)
    const [content, setContent] = useState('')
    const uploadComment = (evt) => {
        evt.preventDefault()
        const process = async () => {
          try {
            setContent('')          
            let res = await authAPI().post(endpoints['comment'] + `/${props.post.id}`, {
                "content": content
            })
            if (res.status === 201) {
                props.setReloadComment(true)
            }
          } catch (ex) {
          }
        }
    
        process()
    }
    return (
        <div className="write">
            <form onSubmit={uploadComment}>
                <img src={user.avatar} alt="" />
                <input type="text" value={content} maxLength="255" required onChange={(e) => {setContent(e.target.value)}} placeholder="Viết bình luận" />
                <button type='submit'><SendIcon/></button>
            </form>
        </div>
    )
}
