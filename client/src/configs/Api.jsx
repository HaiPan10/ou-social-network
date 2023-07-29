import axios from "axios";
import { load } from 'react-cookies';

export const endpoints = {
    "register": "/accounts/register",
    "verify": "/email/verify",
    "login": "/accounts/login",
    "status": "/status"
}

export const authAPI = () => axios.create({
    baseURL: "http://127.0.0.1:8080/social_network/api",
    headers: {
        "Authorization": `Bearer ${ load("access-token") }`
    }
})

export default axios.create({
    baseURL: 'http://127.0.0.1:8080/social_network/api',

})