import "./home.scss"
import { Post } from "../../components/post/Post"
import { useContext, useEffect, useRef, useState } from "react"
import { PostLayout } from "../../components/postLayout/PostLayout"
import { AuthContext } from "../../context/AuthContext"
import { authAPI, endpoints } from "../../configs/Api"
import { ReloadContext } from "../../context/ReloadContext"
// import {FireBaseTest} from "../../components/firebaseTest/FireBaseTest"
import Loading from "../../components/Loading"
import KeyboardCapslockIcon from '@mui/icons-material/KeyboardCapslock';
import { InView } from 'react-intersection-observer';
import { SearchContext } from "../../context/SearchContext"
import { PostInvitation } from "../../components/post/PostInvitation"
import { PostSurvey } from "../../components/post/PostSurvey"

export const Home = () => {
  const [posts, setPosts] = useState([])
  const [user, userDispatch] = useContext(AuthContext)
  const { reload } = useContext(ReloadContext)
  const [page, setPage] = useState(1)
  const [isLoading, setIsLoading] = useState(false)
  const [isCaughtUp, setIsCaughtUp] = useState(false)
  const { reloadData } = useContext(ReloadContext)
  const { showSearch } = useContext(SearchContext)

  useEffect(() => {
    const loadNewFeeds = async () => {
      setIsLoading(true)
      try {
        let res = await authAPI().get(endpoints['posts'] + `?page=1`)
        setIsLoading(false)
        setPosts(res.data)
      } catch (ex) {
        setIsCaughtUp(true)
        console.clear()
      } finally {
        setIsLoading(false)
      }
    }

    setPage(1)
    setPosts([])
    setIsCaughtUp(false)
    loadNewFeeds()
    window.scrollTo({ top: 0, left: 0, behavior: 'instant' })
  }, [reload])

  useEffect(() => {
    const loadNewFeeds = async () => {
      setIsLoading(true)
      try {
        let res = await authAPI().get(endpoints['posts'] + `?page=${page}`)
        setIsLoading(false)
        setPosts(prevPosts => [...prevPosts, ...res.data])
      } catch (ex) {
        setIsCaughtUp(true)
        console.clear()
      } finally {
        setIsLoading(false)
      }
    }

    if (page > 1) {
      loadNewFeeds()
    }
  }, [page])

  const handleIntersection = (inView) => {
    if (inView && !isLoading && !isCaughtUp) {
      setPage(page + 1);
    }
  }

  return (
    <div className="home">
      <div className="posts" style={{paddingLeft: showSearch ? "21.5px" : ""}}>
        {posts === null ? <Loading/> : 
        <>
          <PostLayout/>
            {posts.length !== 0 && posts.map(post=>(
              post.postSurvey !== null ?
              <PostSurvey post={post} key={post.id} posts={posts} setPosts={setPosts}/>
            :
            post.postInvitation !== null ?
              <PostInvitation post={post} key={post.id} posts={posts} setPosts={setPosts}/>
            :
            <Post post={post} key={post.id} posts={posts} setPosts={setPosts}/>
          ))}
          {isLoading && <div className="bottom-loading">
            <Loading/>
          </div>}
          {isCaughtUp && <div className="caught-up" onClick={() => {
            reloadData()
            setPage(1)
          }}>
            <div className="caught-up-content">Bạn đã xem hết nội dung hôm nay rồi!</div>
            <button><KeyboardCapslockIcon/> Đi tới trang đầu</button>
          </div>}
          {posts.length !== 0 && <InView onChange={handleIntersection}>
              {({ inView, ref }) => (
                <div ref={ref}>
                  {inView && !isLoading && !isCaughtUp && (
                    <div className="bottom-loading">
                      <Loading />
                    </div>
                  )}
                </div>
              )}
          </InView >}
        </>
        }
      </div>
    </div>
  )
}
