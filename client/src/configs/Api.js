import axios from "axios";

export const endpoints = {
    "register": "/accounts/register",
    "verify": "/email/verify",
    "login": "/accounts/login",
}

export default axios.create({
    baseURL: 'http://127.0.0.1:8080/social_network/api',

})