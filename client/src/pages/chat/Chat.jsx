import { useContext, useEffect, useState } from 'react';
import './chat.scss'
import { addDoc, collection, onSnapshot, serverTimestamp, where, query, orderBy, doc, updateDoc, getDocs } from 'firebase/firestore';
import { auth, db } from '../../configs/firebase';
import { useParams } from 'react-router-dom';
import { SideChat } from '../../components/sideChat/SideChat';
import SendIcon from '@mui/icons-material/Send';
import { AuthContext } from '../../context/AuthContext';
import WestIcon from '@mui/icons-material/West';
import { authAPI, endpoints } from '../../configs/Api';
import BlockIcon from '@mui/icons-material/Block';

export const Chat = () => {
    const [newMessage, setNewMessage] = useState("")
    const messageRef = collection(db, "messages")
    const userRoomRef = collection(db, "user_rooms")
    const roomRef = collection(db, "rooms")
    const [messages, setMessages] = useState([]);
    const { id } = useParams();
    const userRef = collection(db, "users")
    const [targetUser, setTargetUser] = useState(null)
    const [user, userDispatch] = useContext(AuthContext)
    const roomName = (user.id < parseInt(id) ? user.id + "_" + parseInt(id) : parseInt(id) + "_" + user.id)
    const [available, setAvailable] = useState(null)

    // const createRoom = async (evt) => {
    //     await addDoc(roomRef, {
    //         updateAt: serverTimestamp(),
    //         name: (user.id < targetUser.user_id ? user.id + "_" + targetUser.user_id : targetUser.user_id + "_" + user.id)
    //     }).then(async docRef => {
    //         await addDoc(userRoomRef, {
    //             userRef: doc(db, "users", auth.currentUser.uid),
    //             roomRef: docRef
    //         })
    //         await addDoc(userRoomRef, {
    //             userRef: doc(db, "users", targetUser.uid),
    //             roomRef: docRef
    //         })
    //         await addDoc(messageRef, {
    //             text: newMessage,
    //             createAt: serverTimestamp(),
    //             user_id: user.id,
    //             room: roomName,
    //         })
    //         fetchRooms()
    //     })
    // }

    // const fetchRooms = () => {
    //     const queryRooms = query(
    //         roomRef, 
    //         where("name", "==", roomName), 
    //     )
    //     onSnapshot(queryRooms, (snapshot) => {
    //         snapshot.forEach((doc) => {
    //             setCurrentRoom(doc.id)
    //         })
    //     })
    // }

    const handleSubmit = async (evt) => {
        evt.preventDefault()

        if (newMessage === "") return;

        // if (currentRoom === null) {
        //     createRoom()
        // } else {
        //     const roomsDocRef = doc(db, "rooms", currentRoom);
        //     const updatedData = {
        //         updateAt: serverTimestamp(),
        //     }
        //     await updateDoc(roomsDocRef, updatedData);
        //     await addDoc(messageRef, {
        //         text: newMessage,
        //         createAt: serverTimestamp(),
        //         user_id: user.id,
        //         room: roomName
        //     })
        // }
        await addDoc(messageRef, {
            text: newMessage,
            createAt: serverTimestamp(),
            user_id: user.id,
            room: roomName
        })
        setNewMessage("")
    }
 
    useEffect(() => {
        const getStatus = async () => {
            try {
                let res = await authAPI().get(endpoints['status'])
                if (res.data === "ACTIVE") {
                    setAvailable(true)
                } else {
                    setAvailable(false)
                }
            } catch (ex) {  
            }
        }

        const fetchTargetUser = () => {
            const queryUser = query(userRef, where("user_id", "==", parseInt(id)));
            onSnapshot(queryUser, (snapshot) => {
                snapshot.forEach((doc) => {
                    setTargetUser({...doc.data()});
                })
            })
        }

        const fetchMessage = () => {
            const queryMessages = query(
                messageRef, 
                where("room", "==", roomName), 
                orderBy("createAt")
            )
            const unsuscribe = onSnapshot(queryMessages, (snapshot) => {
                let messages = [];
                snapshot.forEach((doc) => {
                    messages.push({...doc.data(), id:doc.id});
                })
                setMessages(messages)
            })

            return () => unsuscribe();
        }

        if (id !== null) {
            getStatus()
            fetchTargetUser()
            // fetchRooms()
            fetchMessage()
        }
    }, [id])

    return (
        <div className="chat-wrapper">
            <div className='chat'>
                <div className="target-user">
                    {targetUser && available &&
                        <div className="target-user-wrapper">
                            <div className="photoURL"><img src={targetUser.photoURL} alt="" /></div>
                            <div className="displayName">{targetUser.displayName}</div>
                        </div>
                    }
                </div>
                <div className="chat-content">
                    {available !== null &&
                        available === false ? <div className='empty'>
                            <BlockIcon/> Tài khoản của bạn không hợp lệ hoặc chưa đổi mật khẩu!
                        </div>
                        :
                        id !== undefined ?
                        <>
                        {available && messages.length !== 0 && messages.map((message) => 
                            <div className="message-item" key={message.id}>
                                {message.user_id === user.id ? 
                                    <div className="own-message message">
                                        {message.text}
                                    </div>
                                    :
                                    <div className="orther-message message">
                                        {message.text}
                                    </div>
                                }
                            </div>
                        )}                    
                        </> 
                        :
                        <div className='empty'>
                            <WestIcon/> Chọn để bắt đầu nhắn tin!
                        </div>
                    }
                    {/* {id === undefined ?
                        <div className='empty'>
                            <WestIcon/> Chọn để bắt đầu nhắn tin!
                        </div>
                        :
                        available !== null ?
                        <>
                        {available && messages.length !== 0 && messages.map((message) => 
                            <div className="message-item" key={message.id}>
                                {message.user_id === user.id ? 
                                    <div className="own-message message">
                                        {message.text}
                                    </div>
                                    :
                                    <div className="orther-message message">
                                        {message.text}
                                    </div>
                                }
                            </div>
                        )}                    
                        </> 
                        :
                        <div className='empty'>
                            <BlockIcon/> Tài khoản của bạn không hợp lệ hoặc chưa đổi mật khẩu!
                        </div>
                    } */}
                </div>
                {id !== undefined && targetUser && available &&
                <div className="chat-input">
                    <form className='form' onSubmit={handleSubmit}>
                        <input type='text' placeholder='Nhập tin nhắn' value={newMessage} onChange={(e) => setNewMessage(e.target.value)}/>
                        <button className='btn btn-chat' type='submit'><SendIcon/></button>
                    </form>
                </div>}
            </div>
        </div>
    )
}
