import { NavBar } from "../../components/navBar/NavBar"
import { LeftBar } from "../../components/leftBar/LeftBar"
import { RightBar } from "../../components/rightBar/RightBar"
import "./home.scss"
import { Post } from "../../components/post/Post"
import { useEffect } from "react"

export const Home = () => {
  
  useEffect(() => {
    window.scrollTo({ top: 0, left: 0, behavior: 'auto' })
  }, [])

  const posts = [
    {
        id: 1,
        name: "Thanh Tâm",
        avatar: "https://scontent.fsgn2-4.fna.fbcdn.net/v/t39.30808-6/354454189_3645216862472761_947070355558899376_n.jpg?_nc_cat=109&cb=99be929b-59f725be&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=CRWUp3kohQ0AX9bCYUW&_nc_ht=scontent.fsgn2-4.fna&oh=00_AfAkRHBnlAOXOwRgz_MKlfVvbXS5UJX7Zye5HmuXKTrs7Q&oe=64D32065",
        userId: 1,
        desc: "Ông vua về nhì",
        img: "https://scontent.fsgn2-4.fna.fbcdn.net/v/t39.30808-6/341490760_623184145881646_601760022175555490_n.jpg?stp=dst-jpg_s960x960&_nc_cat=101&cb=99be929b-59f725be&ccb=1-7&_nc_sid=8bfeb9&_nc_ohc=VBlzcvFTB0EAX-Lp-U_&_nc_ht=scontent.fsgn2-4.fna&oh=00_AfA0Y5eqNspJ794kUP4gykk0wxQRHBfQ6CV7k19mGbfoag&oe=64D34FED",
        comments: [
          {
            id: 1,
            desc: "Anh Hải giỏi quá ❤️ ❤️",
            name: "Mai Nguyễn",
            userId: 1,
            avatar:
              "https://scontent.fsgn2-9.fna.fbcdn.net/v/t39.30808-6/277002539_1375356086260046_5160724194459668762_n.jpg?_nc_cat=105&ccb=1-7&_nc_sid=174925&_nc_ohc=d7EjbFj3HFYAX9S3SS9&_nc_ht=scontent.fsgn2-9.fna&oh=00_AfCrm2BS8U8tVO64URnFSG98ydiHt6rBAW5TJ-y2qvut1A&oe=64D266E2",
          },
          {
            id: 2,
            desc: "Tuỵt vời",
            name: "Phong Lại",
            userId: 2,
            avatar:
              "https://scontent.fsgn2-7.fna.fbcdn.net/v/t39.30808-1/361257784_1748612805595222_847078644766594281_n.jpg?stp=dst-jpg_p320x320&_nc_cat=108&cb=99be929b-59f725be&ccb=1-7&_nc_sid=7206a8&_nc_ohc=Hp1YhJkSU4YAX9ERBPJ&_nc_ht=scontent.fsgn2-7.fna&oh=00_AfAGLqd7zzgorPAEfHq77HAkZnLzDHeKXShz2bWuUIkxhA&oe=64D42675",
          },
        ]
    },
    {
        id: 2,
        name: "Phong Lại",
        avatar: "https://scontent.fsgn2-7.fna.fbcdn.net/v/t39.30808-1/361257784_1748612805595222_847078644766594281_n.jpg?stp=dst-jpg_p320x320&_nc_cat=108&cb=99be929b-59f725be&ccb=1-7&_nc_sid=7206a8&_nc_ohc=Hp1YhJkSU4YAX9ERBPJ&_nc_ht=scontent.fsgn2-7.fna&oh=00_AfAGLqd7zzgorPAEfHq77HAkZnLzDHeKXShz2bWuUIkxhA&oe=64D42675",
        userId: 2,
        img:
        "https://scontent.fsgn2-6.fna.fbcdn.net/v/t39.30808-6/348609765_1731357207320782_1190611563029584674_n.jpg?stp=dst-jpg_p843x403&_nc_cat=111&cb=99be929b-59f725be&ccb=1-7&_nc_sid=8bfeb9&_nc_ohc=9Q_Em4DuLZcAX8hEvBO&_nc_ht=scontent.fsgn2-6.fna&oh=00_AfCTDhP5SSLxe_IEaOR8MODZ26CeQ6sd2pQzB2unnVjuXQ&oe=64D31D9A",
        desc: "Cơm chó này @Phan Thanh Hải",
        comments: [
          {
            id: 1,
            desc: "Huhu chỉ biết ước",
            name: "Phan Thanh Hải",
            userId: 1,
            avatar:
              "https://scontent.fsgn2-6.fna.fbcdn.net/v/t39.30808-1/346479486_1919223765143706_3610828056285952976_n.jpg?stp=c0.107.320.320a_dst-jpg_p320x320&_nc_cat=110&cb=99be929b-59f725be&ccb=1-7&_nc_sid=7206a8&_nc_ohc=wpq5BIkJr2oAX8wrJJs&_nc_ht=scontent.fsgn2-6.fna&oh=00_AfBDIKd8ITu9wEP_LwYDAbDrQUpdkxdMxlNai8mu6mSuwg&oe=64D311D4",
          },
          {
            id: 2,
            desc: "Cho mượn vài triệu với",
            name: "Việt Quang",
            userId: 2,
            avatar:
              "https://scontent.fsgn2-4.fna.fbcdn.net/v/t39.30808-1/356142638_826468212229548_2690925930639153360_n.jpg?stp=dst-jpg_p320x320&_nc_cat=101&cb=99be929b-59f725be&ccb=1-7&_nc_sid=7206a8&_nc_ohc=1VcKOxQWP70AX_-teMD&_nc_ht=scontent.fsgn2-4.fna&oh=00_AfBSMZ_J06Kaddziv1ZO06msPUv8fxDpoZGfngVgwisWSw&oe=64D37E5D",
          },
        ]
    },
    {
      id: 3,
      name: "Bùi Tiến Phát",
      avatar: "https://scontent.fsgn2-4.fna.fbcdn.net/v/t39.30808-1/315256594_1475404026274288_2347858660450415372_n.jpg?stp=dst-jpg_s320x320&_nc_cat=101&cb=99be929b-59f725be&ccb=1-7&_nc_sid=7206a8&_nc_ohc=6Z1iwlD-KvEAX-H6tdc&_nc_ht=scontent.fsgn2-4.fna&oh=00_AfAgO8ke8Xy-UdIW_dG5Gdivu7NlyA_C0A2EK7fzMZRfqA&oe=64D3993E",
      userId: 2,
      img:
      "https://scontent.fsgn2-6.fna.fbcdn.net/v/t39.30808-6/357051138_1619656971848992_8779239806357283914_n.jpg?stp=dst-jpg_p600x600&_nc_cat=110&cb=99be929b-59f725be&ccb=1-7&_nc_sid=730e14&_nc_ohc=WFeL6vgrm2sAX8qWRGi&_nc_ht=scontent.fsgn2-6.fna&oh=00_AfC3CwWw3Fkx5Jb4PNM_tWhmyp2tviVLlFEYw78gyQmQ2w&oe=64D4258D",
      desc: "Top server vừa thêm 1 ảnh mới",
      comments: [
        {
          id: 1,
          desc: "Chồng yêu giỏi quá ❤️❤️",
          name: "Như Quỳnh",
          userId: 1,
          avatar:
            "https://scontent.fsgn2-6.fna.fbcdn.net/v/t39.30808-1/353379960_1306447316942411_1921747266404131645_n.jpg?stp=dst-jpg_s320x320&_nc_cat=111&cb=99be929b-59f725be&ccb=1-7&_nc_sid=7206a8&_nc_ohc=6JFejOH1QgwAX_DN3Ul&_nc_ht=scontent.fsgn2-6.fna&oh=00_AfBudbnuh-1PCgBd_TW9L6OQrRlbOFFqCslswjX9WbI1HQ&oe=64D3085A",
        }
      ]
    }
  ];
  return (
    <div className="home">
      <div className="posts">
        {posts.map(post=>(
          <Post post={post} key={post.id}/>
        ))}
      </div>
    </div>
  )
}
