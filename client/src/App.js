import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { Login } from "./pages/login/Login";
import { Register } from "./pages/register/Register";
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
