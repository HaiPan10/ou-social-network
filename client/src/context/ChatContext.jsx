import { createContext, useEffect, useState } from "react";

export const ChatContext = createContext()

export const ChatContextProvider = ({children}) => {
    const [showChat, setShowChat] = useState (
        JSON.parse(localStorage.getItem("showChat")) || false
    )

    const toggleChat = () => {
        setShowChat(!showChat)
    }

    useEffect(() => {
        localStorage.setItem("showChat", showChat)
    }, [showChat])

    return (
        <ChatContext.Provider value={{ showChat, toggleChat }}>
            {children}
        </ChatContext.Provider>
    )
}