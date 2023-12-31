import axios from "axios";
import { load } from 'react-cookies';

export const endpoints = {
    "register": "/accounts/register",
    "verify": "/email/verify",
    "login": "/accounts/login",
    "status": "/accounts/status",
    "profile": "/users/profile",
    "update_avatar": "/users/update_avatar",
    "update_cover": "/users/update_cover",
    "update_information": "/users/update_information",
    "upload": "/posts/upload",
    "posts": "/posts",
    "comment": "/comments",
    "change_password": "/accounts/change_password",
    "post_reactions": "/post_reactions",
    "search": "/accounts/search?kw=",
    "responses": "/responses"
}

export const authAPI = () => axios.create({
    // baseURL: "http://127.0.0.1:8080/social_network/api",
    baseURL: "http://34.101.166.192:80/api",
    headers: {
        "Authorization": `Bearer ${ load("access-token") }`
    }
})

// export const authAPI = () => {
//     const accessToken = load("access-token");
//     console.log("Access Token:", accessToken);
  
//     return axios.create({
//       baseURL: "http://127.0.0.1:8080/social_network/api",
//       headers: {
//         "Authorization": `Bearer ${accessToken}`,
//         "Access-Control-Allow-Origin": "*"
//       }
//     });
//   };
  

export default axios.create({
    // baseURL: 'http://127.0.0.1:8080/social_network/api',
    baseURL: "http://34.101.166.192:80/api",
})