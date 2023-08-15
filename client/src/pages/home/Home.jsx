import { NavBar } from "../../components/navBar/NavBar"
import { LeftBar } from "../../components/leftBar/LeftBar"
import { RightBar } from "../../components/rightBar/RightBar"
import "./home.scss"
import { Post } from "../../components/post/Post"
import { useEffect } from "react"
import { PostLayout } from "../../components/postLayout/PostLayout"
// import {FireBaseTest} from "../../components/firebaseTest/FireBaseTest"

export const Home = () => {
  
  useEffect(() => {
    window.scrollTo({ top: 0, left: 0, behavior: 'instant' })
  }, [])

  return (
    <div className="home">
      <div className="posts">
        <PostLayout/>
        {/* {posts.map(post=>(
          <Post post={post} key={post.id}/>
        ))} */}
      </div>
    </div>
  )
}
