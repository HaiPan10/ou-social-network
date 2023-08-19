import "./home.scss"
import { Post } from "../../components/post/Post"
import { useContext, useEffect, useState } from "react"
import { PostLayout } from "../../components/postLayout/PostLayout"
import { AuthContext } from "../../context/AuthContext"
import { authAPI, endpoints } from "../../configs/Api"
import { ReloadContext } from "../../context/ReloadContext"
// import {FireBaseTest} from "../../components/firebaseTest/FireBaseTest"
import Loading from "../../components/Loading"

export const Home = () => {
  const [posts, setPosts] = useState(null)
  const [user, userDispatch] = useContext(AuthContext)
  const { reload } = useContext(ReloadContext)
  
  useEffect(() => {
    const loadNewFeeds = async () => {
      try {
        let res = await authAPI().get(endpoints['posts'] + `/${user.id}`) 
        setPosts(res.data)
        window.scrollTo({ top: 0, left: 0, behavior: 'instant' })
      } catch (ex) {
      }
    }
    loadNewFeeds()
  }, [reload])

  return (
    <div className="home">
      <div className="posts">
        {posts === null ?
        <Loading/> : <>
          <PostLayout/>
            {posts.map(post=>(
              <Post post={post} key={post.id}/>
          ))}
        </>
        }
      </div>
    </div>
  )
}
