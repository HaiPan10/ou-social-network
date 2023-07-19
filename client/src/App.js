import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { Login } from "./pages/login/Login";
import { Register } from "./pages/register/Register";
import { Header } from "./Header";
import { AuthenBackground } from "./components/authenBackground/AuthenBackground";
import { Home } from "./pages/home/Home";

const App = () => {
  const Layout = () => {
    return (
      <div>
      </div>
    )
  }

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
      path:"/authen",
      element:<AuthenBackground/>,
    },
    {
      path:"/home",
      element:<Home/>,
    },
  ])

  return (
    // <Header/>
    <div>
      <RouterProvider router={router}/>
    </div>
  );
}

export default App;
