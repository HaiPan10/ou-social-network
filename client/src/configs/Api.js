import axios from "axios";

export const endpoints = {
    "signin": "/api/account/signin",
}

export default axios.create({
    baseURL: 'http://127.0.0.1:8080/',

})