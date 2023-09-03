import { useContext, useEffect, useState } from 'react'
import './sideChat.scss'
import { SearchContext } from '../../context/SearchContext';
import MessageIcon from '@mui/icons-material/Message';
import { AuthContext } from '../../context/AuthContext';
import { collection, onSnapshot, orderBy, query, where } from 'firebase/firestore';
import { auth, db } from '../../configs/firebase';
import Loading from '../Loading';
import { Link } from 'react-router-dom';

export const SideChat = (props) => {
    const [users, setUsers] = useState([]);
    const [user, userDispatch] = useContext(AuthContext)
    const userRef = collection(db, "users")
    const [searchContent, setSearchContent] = useState('')

    useEffect(() => {
        const fetchUsers = () => {
            const queryUsers = query(
                userRef, 
                where("user_id", "!=", user.id), 
            )
            const unsuscribe = onSnapshot(queryUsers, (snapshot) => {
                let userList = []
                snapshot.forEach((doc) => {
                    console.log({...doc.data(), id:doc.id})
                    userList.push({...doc.data(), id:doc.id});
                })
                setUsers(userList)
            })

            return () => unsuscribe();
        }

        fetchUsers()
    }, [])

    const filteredUsers = users.filter(user =>
        user.displayName.toLowerCase().includes(searchContent.toLowerCase())
    );

    return (
        <div className="side-chat">
            <div className="title"><MessageIcon/> Nhắn tin</div>
            {props.status === "ACTIVE" &&
                <>
                            <div className="searchbar">
                    <div className="input-group">
                        <input type="search" value={searchContent} onChange={(e) => setSearchContent(e.target.value)} className="form-control" placeholder="Tên người dùng" />
                    </div>
                </div>
                <hr />
                {users.length === 0 ? <div className='loading'><Loading/></div> : 
                    <div className='item-wrapper'>
                    {filteredUsers.map((userChat) => {
                        return (
                            <Link to={`/chat/${userChat.user_id}`} className='turnoff-link-style'>
                                <div className="chat-room-item" >
                                    <div className="avatar"><img src={userChat.photoURL} alt="" /></div>
                                    <div className="chat-info">
                                        <div className="displayName">{userChat.displayName}</div>
                                    </div>
                                </div>
                            </Link>
                        )
                    })}
                    </div>
                }
            </>}
        </div>
    )
}
