import React, { useContext, useEffect, useRef, useState } from 'react'
import "./post.scss"
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
import SentimentVerySatisfiedRoundedIcon from '@mui/icons-material/SentimentVerySatisfiedRounded';
import ThumbUpOutlinedIcon from '@mui/icons-material/ThumbUpOutlined';
import ThumbUpRoundedIcon from '@mui/icons-material/ThumbUpRounded';
import FavoriteOutlinedIcon from '@mui/icons-material/FavoriteBorderTwoTone';
import FavoriteRoundedIcon from '@mui/icons-material/FavoriteRounded';
import Reaction from '../reaction/Reaction';
import KeyboardDoubleArrowDownIcon from '@mui/icons-material/KeyboardDoubleArrowDown';
import KeyboardDoubleArrowUpIcon from '@mui/icons-material/KeyboardDoubleArrowUp';
import TaskAltIcon from '@mui/icons-material/TaskAlt';
import PollIcon from '@mui/icons-material/Poll';
import 'animate.css'

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
          let res = await authAPI().delete(endpoints['posts'] + `/${props.post.id}`)
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

export const PostSurvey = ({post, posts, setPosts}) => {
  const [commentOpen, setCommentOpen] = useState(false)
    const images = post.imageInPostList.map(image => image.imageUrl);
    const [user, userDispatch] = useContext(AuthContext)
    const [dropdownVisible, setDropdownVisible] = useState(false)
    const [editPostShow, setEditPostShow] = useState(false)
    const [deletePostShow, setDeletePostShow] = useState(false)
    const [currentReaction, setCurrentReaction] = useState(post.currentReaction?.id || null);
    const [reactionShow, setReactionShow] = useState(false)
    const [reaction, setReaction] = useState(null)
    const [reloadReaction, setReloadReaction] = useState(false);
    const [total, setTotal] = useState(null);
    const dropdownRef = useRef(null);
    const sortedQuestions = post.postSurvey.questions.sort((a, b) => a.questionOrder - b.questionOrder);
    const [showMore, setShowMore] = useState(false)

    const toggleDropdown = () => {
        setDropdownVisible(!dropdownVisible);
    }

    const handleClickOutside = (event) => {
        if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
          setDropdownVisible(false)
        }
    }

    const getReactionTotal = (reactionId) => {
      const total = post.reactionTotal[reactionId];
      return total !== undefined ? total : 0;
    }

    const [like, setLike] = useState(getReactionTotal(1))
    const [lol, setLol] = useState(getReactionTotal(2))
    const [favourite, setFavourite] = useState(getReactionTotal(3))

    const react = (evt, reaction_id) => {
      if (reaction_id === 1) {
        setLike(like + 1)
      } else if (reaction_id === 2) {
        setLol(lol + 1)
      } else if (reaction_id === 3) {
        setFavourite(favourite + 1)
      }
      if (currentReaction === 1) {
        setLike(like - 1)
      } else if (currentReaction === 2) {
        setLol(lol - 1)
      } else if (currentReaction === 3) {
        setFavourite(favourite - 1)
      }
      setCurrentReaction(reaction_id)
      evt.preventDefault()
      const postReaction = async () => {
        try {
          let res = await authAPI().post(endpoints['post_reactions'] + `/${post.id}`, {
            "id": reaction_id
          })
          if (res.status === 201) {
            setReloadReaction(!reloadReaction)
          }
        } catch (ex) {
        }
      }

      const putReaction = async () => {
        try {
          let res = await authAPI().put(endpoints['post_reactions'] + `/${post.id}`, {
            "id": reaction_id
          })
          if (res.status === 200) {
            setReloadReaction(!reloadReaction)
          }
        } catch (ex) {
        }
      }

      if (currentReaction === null) {
        postReaction()
      } else if (currentReaction !== reaction_id) {
        putReaction()
      }
    }

    const deleteReaction = (evt, reaction_id) => {
      if (reaction_id === 1) {
        setLike(like - 1)
      } else if (reaction_id === 2) {
        setLol(lol - 1)
      } else if (reaction_id === 3) {
        setFavourite(favourite - 1)
      }
      setCurrentReaction(null)
      evt.preventDefault()
      const process = async () => {
        try {
          let res = await authAPI().delete(endpoints['post_reactions'] + `/${post.id}`)
          if (res.status === 204) {
            setReloadReaction(!reloadReaction)
          }
        } catch (ex) {
        }
      }
      process()
    }

    useEffect(() => {
      document.addEventListener('mousedown', handleClickOutside);
      return () => {
        document.removeEventListener('mousedown', handleClickOutside)
      }
    }, [])

    const showReactionInformation = (reaction_id) => {
      setReaction(reaction_id)
      if (reaction_id === 1) {
        setTotal(like)
      } else if (reaction_id === 2) {
        setTotal(lol)
      } else {
        setTotal(favourite)
      }
      setReactionShow(true)
    }

    const closeReactionInformation = () => {
      setReactionShow(false)
      // setReaction(null)
      // setTotal(null)
    }
    const formattedDate = post.createdAt.replace(/(\d{2})-(\d{2})-(\d{4}) (\d{2}:\d{2}:\d{2})/, '$3-$2-$1 $4');

    const getAnswers = () => {
      const questions = document.querySelectorAll(".question-wrapper")
      const answers = []
    
      questions.forEach((question) => {
        const questionId = question.getAttribute("data-key")
        const questionType = question.getAttribute("data-type")
        const answer = { questionId: { id: questionId } }
    
        if (questionType === "2") {
          const textarea = question.querySelector("textarea")
          answer.value = textarea.value
        } else if (questionType === "1") {
          const radioInput = question.querySelector("input[type=radio]:checked");
          if (radioInput) {
            answer.answerOptions = [{
              questionOptionId: { id: radioInput.value }            
            }]
          }
        } else if (questionType === "3") {
          const checkboxInputs = question.querySelectorAll("input[type=checkbox]:checked");
          if (checkboxInputs) {
            answer.answerOptions = Array.from(checkboxInputs).map((checkbox) => ({
              questionOptionId: { id: parseInt(checkbox.value) },
            }));
          }
        }
    
        answers.push(answer)
      });
      return answers
    }

    const submitSurvey = (evt) => {
      evt.preventDefault();
      const process = async () => {
        try {
          let res = await authAPI().post(endpoints['responses'], {
            "surveyId": {
              "id": post.id
            },
            "answers": getAnswers()
          })
          if (res.status === 201) {
            setShowMore(false)
            setPosts(posts.filter(p => p.id !== post.id))
          }
        } catch (ex) {
        }
      }
      process()
    }

    return (
        <div className='post'>
            <div className="postContainer">
                <div className="user">
                    <div className="userInfo">
                        <Link to={`/profile/${post.userId.id}`}>
                          {post.userId.id === user.id ? 
                            <img src={user.avatar} alt=""/> :
                            <img src={post.userId.avatar} alt=""/>
                          }
                        </Link>
                        
                        <div className="details">
                            <Link to={`/profile/${post.userId.id}`} style={{textDecoration:"none", color:"inherit"}}>
                                <span className='name'>{post.userId.lastName} {post.userId.firstName} <span>đã tiến hành cuộc khảo sát <PollIcon/></span></span>
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
                    <h3 className='title'>{post.postSurvey.surveyTitle}</h3>
                    <p style={{marginBottom: "2px"}}>{post.content}</p>
                    <div className="row">
                      {!showMore ?
                        <div className='show-more text-center'>
                          <div onClick={() => setShowMore(true)} className='move-icon' ><KeyboardDoubleArrowDownIcon className=' animate__animated animate__infinite animate__flip'/> Mở khảo sát</div>
                        </div>
                        :
                        <form onSubmit={submitSurvey} className="" style={{width: "100%"}} action="">
                        {sortedQuestions.map( (question) => {
                          return (
                            <div className="question-wrapper" key={question.id} data-key={question.id} data-type={question.questionTypeId.id}>
                              {question.questionTypeId.id === 2 && 
                                <div className="input-question">
                                  <div className="form-outline mb-4">
                                    <label className="fw-bold form-label">Câu hỏi {question.questionOrder}: {question.questionText}
                                      {question.isMandatory && <span className='piority-question'>câu hỏi bắt buộc (*)</span>}
                                    </label>
                                    <textarea className="form-control" rows="2" maxLength={255} id={`textarea_${question.id}`} name={question.id} required={question.isMandatory ? true : false}></textarea>
                                  </div>
                                </div>
                              }
                              {question.questionTypeId.id === 1 && 
                                <div className="multiple-question form-outline mb-4">
                                  <p className="fw-bold form-label">Câu hỏi {question.questionOrder}: {question.questionText}
                                    {question.isMandatory && <span className='piority-question'>câu hỏi bắt buộc (*)</span>}
                                  </p>
                                  {question.questionOptions.map( (option) => {
                                  return (
                                    <div className="form-check mb-2 question-option" key={option.id}>
                                      <input className="form-check-input" type="radio" name={question.id} value={option.id} required={question.isMandatory ? true : false}/>
                                      <label className="form-check-label">
                                        {option.value}
                                      </label>
                                    </div>
                                  )
                                  })}
                                </div>
                              }
                              {question.questionTypeId.id === 3 && 
                                <div className="checkbox-question form-outline mb-4">
                                  <p className="fw-bold form-label">Câu hỏi {question.questionOrder}: {question.questionText}
                                    {question.isMandatory && <span className='piority-question'>câu hỏi bắt buộc (*)</span>}
                                  </p>
                                  {question.questionOptions.map( (option) => {
                                    return (
                                      <div className="question-option form-check" key={option.id}>
                                        <input className="form-check-input" type="checkbox" name={question.id} value={option.id}/>
                                        <label className="form-check-label">
                                          {option.value}
                                        </label>
                                      </div>
                                    )
                                  })}
                                </div>
                              }
                            </div>
                          )
                        })}
                        <div className="text-center pb-4">
                          <button type="submit" className="btn btn-primary"><TaskAltIcon/> Hoàn thành khảo sát</button>
                        </div>
                        <div className='show-more text-center'>
                          <div onClick={() => setShowMore(false)} className='move-icon' ><KeyboardDoubleArrowUpIcon className=' animate__animated animate__infinite animate__flip'/>  Đóng khảo sát</div>
                        </div>
                        </form>
                      }
                    </div>
                    {/* <ImageInPost hideOverlay={true} images={images} /> */}
                    {/* <img src={post.img} /> */}
                </div>
                <div className="info">
                    <div className="item">
                      {currentReaction === 1 ? <ThumbUpRoundedIcon onClick={(e) => deleteReaction(e, 1)} className='reaction-icon selected'/> : <ThumbUpOutlinedIcon onClick={(e) => react(e, 1)} className='reaction-icon' /> }
                      <div className="reaction_number" onClick={() => showReactionInformation(1)}>{like}</div>
                      {currentReaction === 2 ? <SentimentVerySatisfiedRoundedIcon onClick={(e) => deleteReaction(e, 2)} className='reaction-icon selected'/> : <SentimentVerySatisfiedRoundedIcon onClick={(e) => react(e, 2)} className='reaction-icon'/>}
                      <div className="reaction_number" onClick={() => showReactionInformation(2)}>{lol}</div>
                      {currentReaction === 3 ? <FavoriteRoundedIcon onClick={(e) => deleteReaction(e, 3)} className='reaction-icon selected'/> : <FavoriteOutlinedIcon onClick={(e) => react(e, 3)} className='reaction-icon'/> }
                      <div className="reaction_number" onClick={() => showReactionInformation(3)}>{favourite}</div>
                      <Reaction show={reactionShow} onHide={closeReactionInformation} reloadReaction={reloadReaction} post={post} reaction={reaction} total={total}/>
                    </div>
                    {post.isActiveComment ? <div className="item reaction-icon" onClick={()=>setCommentOpen(!commentOpen)}>
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
