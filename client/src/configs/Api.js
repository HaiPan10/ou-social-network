import axios from "axios";

export let endpoints = {
    'account': '/account/'
}

export default axios.create({
    baseURL: 'http://127.0.0.1:8080/',

})