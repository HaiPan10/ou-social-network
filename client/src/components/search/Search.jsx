import { useContext, useEffect, useState } from 'react';
import { SearchContext } from '../../context/SearchContext';
import './search.scss'
import SearchIcon from '@mui/icons-material/Search';
import { authAPI, endpoints } from '../../configs/Api';
import Loading from '../Loading';
import { Link } from 'react-router-dom';
export const Search = () => {
    const { showSearch } = useContext(SearchContext)
    const [searchContent, setSearchContent] = useState('')
    const [isLoading, setIsLoading] = useState(false)
    const [searchResult, setSearchResult] = useState()
    const { toggleSearch } = useContext(SearchContext)

    useEffect(() => {
        const search = async () => {
            setIsLoading(true)
            try {
              let res = await authAPI().get(endpoints['search'] + searchContent)
              setSearchResult(res.data)
            } catch (ex) {
            } finally {
              setIsLoading(false)
            }
          }
        
        if (searchContent !== '') {
            search()
        }
      }, [searchContent])

    if (!showSearch) {
        setSearchContent('')
    }

    return (
        <div className="search">
            <div className="search-title"><SearchIcon/> Tìm kiếm</div>
            <div className="searchbar">
                <div class="input-group">
                    <input type="search" value={searchContent} onChange={(e) => setSearchContent(e.target.value)} className="form-control" placeholder="Tìm kiếm ở đây" />
                    {/* <button type="button" class="btn btn-outline-primary"><SearchIcon/></button> */}
                </div>
            </div>
            <hr />
            <div className='search-result'>
                {searchContent==='' ?
                    <div className="empty-content">Nội dung tìm kiếm xuất hiện ở đây</div> :
                    isLoading ? <div className="loading"><Loading/></div> :
                    <div className="content">
                        {searchResult && searchResult.map(user=>(
                            <Link to={`/profile/${user.id}`} onClick={toggleSearch} className='turnoff-link-style'>
                                <div className="search-row">
                                    <div className="avatar">
                                        <img src={user.avatar} alt="" />
                                    </div>
                                    <div className="name">
                                        {user.lastName} {user.firstName}
                                    </div>
                                </div>
                            </Link>
                        ))}
                    </div>
                }
            </div>
        </div>
    )
}
