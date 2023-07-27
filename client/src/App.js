import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { Login } from "./pages/login/Login";
import { Register } from "./pages/register/Register";
import { Home } from "./pages/home/Home";
import { EmailVerification } from "./pages/emailVerification/EmailVerification";
import { useState } from "react";

const App = () => {
  const [account, setAccount] = useState({
    "id": "",
    "email": "",
    "password": "",
    "verificationCode": ""
  })
  

  const router = createBrowserRouter([
    {
      path:"/login",
      element:<Login/>,
    },
    {
      path:"/register",
      element:<Register/>,
    },
    {
      path:"/register/email_verification",
      element:<EmailVerification/>,
    },
    {
      path:"/home",
      element:<Home/>,
    },
  ])

  return (
    <div>
      <RouterProvider router={router}/>
    </div>
  );
}

export default App;
