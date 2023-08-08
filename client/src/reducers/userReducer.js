import cookie from "react-cookies"

const userReducer = (state, action) => {
    switch (action.type) {
        case "LOGIN":
            return action.payload
        case "LOGOUT":
            cookie.remove('access-token')
            cookie.remove('current-user')
            cookie.remove('role')
            cookie.remove('theme')
            return null
        default:
            return state
    }
}

export default userReducer