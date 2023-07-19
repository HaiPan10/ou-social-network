// import React from "react";
// import Api, { endpoints } from "./Api";
// import { Link } from "react-router-dom";

// class Header extends React.Component {
//     constructor() {
//         super()
//         this.state = {"account": []}
//     }
//     componentDidMount() {
//         Api.get(endpoints['accounts']).then(res => {   
//             console.info(res.data.results)
//             this.setState({
//                 "account": res.data.results
//             })
//         })
//     }
//     render() {
//         return (
//             <>
//                 <ul>
//                     {this.state.account.map(c => <li><Link>{c.email}</Link></li>)}
//                 </ul>
//             </>
//         )
//     }
// }

// export default Header