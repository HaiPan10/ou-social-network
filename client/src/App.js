import { Navigate, Outlet, RouterProvider, createBrowserRouter } from "react-router-dom";
import { Login } from "./pages/login/Login";
import { Register } from "./pages/register/Register";
import { Profile } from "./pages/profile/Profile";
import { Home } from "./pages/home/Home";
import { AuthContext } from "./context/AuthContext";
import { useContext, useEffect, useReducer } from "react";
import userReducer from "./reducers/userReducer";
import { load, save } from 'react-cookies';
import { Layout } from "./components/layout/Layout";
import { DarkModeContext, DarkModeContextProvider } from "./context/DarkModeContext";
import { ReloadContextProvider } from "./context/ReloadContext";
import './index.css'
import { SearchContextProvider } from "./context/SearchContext";
import { Chat } from "./pages/chat/Chat";
import { ChatContextProvider } from "./context/ChatContext";


const App = () => {
  const [user, dispatch] = useReducer(userReducer, load('current-user') || null)
  const router = createBrowserRouter([
    {
      path:"/",
      element: (
        <DarkModeContextProvider>
          <ReloadContextProvider>
            <SearchContextProvider>
              <ChatContextProvider>
                <Layout/>
              </ChatContextProvider>
            </SearchContextProvider>
          </ReloadContextProvider>
        </DarkModeContextProvider>
      ),
      children: [
        {
          path: "/",
          element: <Home />,
        },
        {
          path: "/profile/:id",
          element: <Profile />,
        },
        {
          path: "/chat",
          element: <Chat />,
        },
        {
          path: "/chat/:id",
          element: <Chat />,
        }
      ],
    },
    {
      path:"/login",
      element:<Login/>,
    },
    {
      path:"/register",
      element:<Register/>,
    },
  ])

  return (    
    <div>
      <AuthContext.Provider value={[user, dispatch]}>
        <RouterProvider router={router}/>        
      </AuthContext.Provider>
    </div>
  );
}

export default App;
