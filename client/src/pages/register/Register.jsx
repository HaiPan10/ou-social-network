import { Link } from "react-router-dom"
import "./register.scss"
import axios from 'axios';
import { AuthenBackground } from "../../components/authenBackground/AuthenBackground";

export const Register = () => {
  // const [firstName, setFirstName] = useState('')
  // const [lastName, setLastName] = useState('')
  // const [email, setEmail] = useState('')
  // const [password, setPassword] = useState('')
  // const [studentIdentical, setStudentIdentical] = useState('')


  // const submitHandler = (event) => {
  //     event.preventDefault()
  //     fetch('https://localhost:8081/api/account/signup/', {
  //       method: 'POST',
  //       body: JSON.stringify({
  //           firstName: firstName,
  //           lastName: lastName,
  //           email: email,
  //           password: password,
  //           studentIdentical: studentIdentical,
  //       }),
  //       headers: {
  //           'Content-type': 'application/json; charset=UTF-8',
  //       },
  //     })
  //     .then((response) => {
  //       if (!response.ok()) {
  //           throw Error('could not fetch the data for that resource');
  //       }
  //       console.log(response.json());
  //       return response.json();
  //     })
  // }
  return (
    <div>
      <AuthenBackground/>
      <div className="register">
      <div className="card">
        <div className="right">
          <div className="right-top">
            {/* <h1>Mạng xã hội cựu sinh viên trường đại học Mở TP.HCM</h1> */}
            <p>
              Chức năng này giành riêng cho cựu sinh viên trường đại học Mở TP.HCM.
            </p>
          </div>
          <div className="right-bottom">
            <span>Bạn đã có tài khoản?</span>
            <Link to="/login">
              <button>Đăng nhập ngay</button>
            </Link>
          </div>
        </div>
        <div className="left">
          <h1>Đăng ký</h1>
          <form>
            {/* <input type="text" placeholder="Họ" value={firstName} onChange={(e) => setFirstName(e.target.value)}/>
            <input type="text" placeholder="Tên" value={lastName} onChange={(e) => setLastName(e.target.value)}/>
            <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)}/>
            <input type="password" placeholder="Mật khẩu" value={password} onChange={(e) => setPassword(e.target.value)}/>
            <input type="number" placeholder="Mã số sinh viên" value={studentIdentical} onChange={(e) => setStudentIdentical(e.target.value)}/> */}
            {/* <input type="text" placeholder="Họ"/>
            <input type="text" placeholder="Tên"/> */}
            <input type="email" placeholder="Email"/>
            <input type="password" placeholder="Mật khẩu"/>
            {/* <input type="number" placeholder="Mã số sinh viên"/> */}
            <button type="submit">Tiếp tục</button>
          </form>
        </div>
      </div>
    </div>
    </div>
  )
}
