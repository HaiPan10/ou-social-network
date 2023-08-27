import { createContext, useEffect, useState } from "react";

export const SearchContext = createContext()

export const SearchContextProvider = ({children}) => {
    const [showSearch, setShowSearch] = useState (
        JSON.parse(localStorage.getItem("showSearch")) || false
    )

    const toggleSearch = () => {
        setShowSearch(!showSearch)
    }

    useEffect(() => {
        localStorage.setItem("showSearch", showSearch)
    }, [showSearch])

    return (
        <SearchContext.Provider value={{ showSearch, toggleSearch }}>
            {children}
        </SearchContext.Provider>
    )
}