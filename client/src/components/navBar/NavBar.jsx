import React, { useContext } from 'react'
import { AuthContext } from '../../context/AuthContext'

export const NavBar = () => {
  const [user, dispatch] = useContext(AuthContext)

  const logout = (evt) => {
    evt.preventDefault()
    dispatch({
      "type": "LOGOUT",
    })
  }

  return (
    <div>
      navBar
      <button onClick={logout}>Log out</button>
    </div>
    
  )
}
