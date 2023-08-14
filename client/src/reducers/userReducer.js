import { remove } from "react-cookies"
import { load } from "react-cookies";

const userReducer = (state, action) => {
    const user = load("current-user")
    const paths = ['/', `/profile/${user.id}`];

    switch (action.type) {
        case "LOGIN":
            return action.payload
        case "LOGOUT":
            paths.forEach(path => {
                remove('access-token', path)
                remove('current-user', path)
                remove('role', path)
                remove('theme', path)
            })
            return null
        default:
            return state
    }
}

export default userReducer