import { Form } from "react-bootstrap";
import { DarkModeContext } from "../../context/DarkModeContext";
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import ErrorAlert from "../ErrorAlert"
import "./changePassword.scss"
import { AuthContext } from "../../context/AuthContext"
import { useContext, useEffect, useRef, useState } from "react"
import { ReloadContext } from "../../context/ReloadContext";
import LockPersonIcon from '@mui/icons-material/LockPerson';
import IconButton from "@material-ui/core/IconButton";
import InputAdornment from "@material-ui/core/InputAdornment";
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import Input from "@material-ui/core/Input";
import { authAPI, endpoints } from "../../configs/Api";

const ChangePassword = (props) => {
    const {darkMode} = useContext(DarkModeContext)
    const [user, userDispatch] = useContext(AuthContext)
    const { reloadData } = useContext(ReloadContext)
    const [disableButton, setDisableButton] = useState(false)
    const [err, setErr] = useState()
    const [authPassword, setAuthPassword] = useState()
    const [showOldPassword, setShowOldPassword] = useState(false)
    const [showNewPassword, setShowNewPassword] = useState(false)
    const [showRePassword, setShowRePassword] = useState(false)
    const [account, setAccount] = useState({
        "email": "",
        "password": "",
        "confirmPassword": ""
    })
    
    const clear = () => {
        props.setChangePasswordShow(false)
        setShowOldPassword(false)
        setShowNewPassword(false)
        setShowRePassword(false)
        setAuthPassword("")
        setAccount(account => ({...account, ["password"]:""}))
        setAccount(account => ({...account, ["confirmPassword"]:""}))
        setAccount(account => ({...account, ["email"]:""}))
        setErr()
    }

    const changePassword = (evt) => {
        evt.preventDefault()
        console.log(account)

        const process = async () => {
            setDisableButton(true)
            try {
                let res = await authAPI().post(endpoints['change_password'], {
                    "account": account,
                    "authPassword": authPassword
                })
                
                if (res.status === 200) {
                    clear()
                    reloadData()
                    setDisableButton(false)
                }
            } catch (ex) {
                setErr(ex.response.data)
                setDisableButton(false)
            }
        }
    
        if (account.password !== account.confirmPassword) {
            setErr("Mật khẩu không khớp!")
        } else {
            process()
        }
      }

    return (
        <Modal
          {...props}
          aria-labelledby="contained-modal-title-vcenter"
          centered
          className={`theme-${darkMode ? "dark" : "light"}`}
          id='modal-change-password'
          onHide={clear}
        >
          <Form onSubmit={changePassword}>
            <Modal.Header closeButton>
              <Modal.Title id="contained-modal-title-vcenter">
                <LockPersonIcon/> Đổi mật khẩu
              </Modal.Title>
            </Modal.Header>
            <Modal.Body className="post-body">
                <Input className="email input"
                    type={"text"}
                    onChange={(e) => setAccount(account => ({...account, ["email"]:e.target.value}))}
                    value={account.email}
                    placeholder="Nhập mật email"
                    required
                />

                <Input className="oldPassword input"
                    type={showOldPassword ? "text" : "password"}
                    onChange={(e) => setAuthPassword(e.target.value)}
                    value={authPassword}
                    placeholder="Nhập mật khẩu cũ"
                    required
                    endAdornment={
                        <InputAdornment position="end">
                            <IconButton
                                onClick={() => setShowOldPassword(!showOldPassword)}
                                onMouseDown={(e) => e.preventDefault()}
                            >
                                {showOldPassword ? <VisibilityIcon className="icon" /> : <VisibilityOffIcon className="icon" />}
                            </IconButton>
                        </InputAdornment>
                    }
                />

                <Input className="newPassword input"
                    type={showNewPassword ? "text" : "password"}
                    onChange={(e) => setAccount(account => ({...account, ["password"]:e.target.value}))}
                    value={account.password}
                    placeholder="Nhập mật khẩu mới"
                    required
                    endAdornment={
                        <InputAdornment position="end">
                            <IconButton
                                onClick={() => setShowNewPassword(!showNewPassword)}
                                onMouseDown={(e) => e.preventDefault()}
                            >
                                {showNewPassword ? <VisibilityIcon className="icon" /> : <VisibilityOffIcon className="icon" />}
                            </IconButton>
                        </InputAdornment>
                    }
                />

                <Input className="rePassword input"
                    type={showRePassword ? "text" : "password"}
                    onChange={(e) => setAccount(account => ({...account, ["confirmPassword"]:e.target.value}))}
                    value={account.confirmPassword}
                    placeholder="Nhập lại mật khẩu mới"
                    required
                    endAdornment={
                        <InputAdornment position="end">
                            <IconButton
                                onClick={() => setShowRePassword(!showRePassword)}
                                onMouseDown={(e) => e.preventDefault()}
                            >
                                {showRePassword ? <VisibilityIcon className="icon" /> : <VisibilityOffIcon className="icon" />}
                            </IconButton>
                        </InputAdornment>
                    }
                />
                {err?<ErrorAlert err={err} />:""}
            </Modal.Body>
            <Modal.Footer className="post-footer">
              <Button className="submit" type="submit" disabled={disableButton}>Xác nhận</Button>
            </Modal.Footer>
          </Form>
        </Modal>
    )
}

export default ChangePassword