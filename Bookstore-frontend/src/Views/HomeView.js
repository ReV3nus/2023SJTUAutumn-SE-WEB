import React, { useState, useEffect } from 'react';
import BreadCrumb from '../Components/BreadCrumb';
import HomeBooksCarousel from '../Components/HomeBooksCarousel';
import BookGrids from '../Components/BookGrids';
import {Space, Spin, Input, FloatButton, notification} from 'antd';
import { Navigate } from 'react-router-dom';
import axios from 'axios';
import BookAdder from '../Components/BookAdder';
import {gql, useLazyQuery} from "@apollo/client";

const { Search } = Input;

const GET_BOOKS_BY_NAME = gql`
    query GetBooksByName($name: String!) {
        findBooksByName(name: $name) {
            bookId
            isbn
            name
            type
            author
            price
            description
            inventory
            image
        }
    }
`;
const HomeView = () => {
    const [isAuth, setIsAuth] = useState(false);
    const [isLoading, setIsLoading] = useState(true);
    const [books, setBooks] = useState([]);
    const [filterKeyword, setFilterKeyword] = useState('');
    const [usertype, setUsertype] = useState('');
    const [isLoadingType, setIsLoadingType] = useState(true);
    const [filteredBooks, setFilteredBooks] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const authResponse = await axios.get('/apiAccount/checkLogin');
                if (authResponse.status >= 0) {
                    setIsAuth(true);
                    setIsLoading(false);

                    const userTypeResponse = await axios.get('/apiAccount/checkAdmin');
                    if (userTypeResponse.status >= 0) {
                        setUsertype(userTypeResponse.data);
                        setIsLoadingType(false);
                    }
                }
            } catch (error) {
                console.log('Error:', error);
                setIsAuth(false);
                setIsLoading(false);
            }
        };

        fetchData();

        axios
            .get('/apiBook/getbooks')
            .then((response) => {
                setBooks(response.data);
                setFilteredBooks(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    }, []); // Empty dependency array to run the effect only once

    // const handleSearchByName = (value) => {
    //     setFilterKeyword(value);
    //     setFilteredBooks(books.filter((book) => book.name.includes(value)));
    // };
    const [getBooksByName, { loading: booksLoading, data: booksData }] = useLazyQuery(GET_BOOKS_BY_NAME);
    const [api, contextHolder] = notification.useNotification();

    const handleSearchByName = (value) => {
        setFilterKeyword(value);

        // Use GraphQL to search books by name
        getBooksByName({variables: {name: value}})
             .then((res) => {
                console.log("graphql req:",res);
            });
    };

    useEffect(() => {
        // Check if data is available from GraphQL query
        if (booksData && booksData.findBooksByName) {
            setFilteredBooks(booksData.findBooksByName);
        }
    }, [booksData]);
    const handleSearchByAuthor = (value) => {
        if (value === '') {
            setFilteredBooks(books);
        } else {
            setFilteredBooks([]);
            books.forEach((book) => {
                axios
                    .get('http://localhost:8763/getAuthor', { params: { title: book.name } })
                    .then((res) => {
                        if (res.data === value) {
                            setFilteredBooks((prevBooks) => [...prevBooks, book]);
                        }
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            });
        }
    };

    const handleSearchByTag = (value) => {
        if (value === '') {
            setFilteredBooks(books);
        } else {
            setFilteredBooks([]);
            axios
                .get('/apiBook/GetBooksByTag', { params: { tagName: value } })
                .then((res) => {
                    console.log('Searching tag');
                    console.log(res.data);
                    setFilteredBooks(res.data);
                })
                .catch((err) => {
                    console.log(err);
                });
        }
    };

    const handleFloatButtonClick = async () => {
        api.info({
            message: `Notification`,
            description: "Spark is doing the MapReduce job...",
            placement: "topRight",
        });
        try {
            const apiResponse = await fetch('http://192.168.202.128:5000/run-spark-job');
            const data = await apiResponse.json();
            if (data.status === 'success') {
                api.info({
                    message: `Success!`,
                    description: data.result,
                    placement: "topRight",
                });
                console.log(data.result);
            } else {
                api.info({
                    message: `Failed.`,
                    description: data.message,
                    placement: "topRight",
                });
                console.log(data.message);
            }
        } catch (error) {
            console.log("fetch error:");
            console.log(error);
        }
    }

    if (isLoading) {
        return (
            <Spin tip="Loading" size="small" id="SpinCSS">
                <div className="content" />
            </Spin>
        );
    }

    if (!isAuth) {
        return <Navigate to="/Login" />;
    }

    if (isLoadingType || booksLoading) {
        return (
            <Spin tip="Loading" size="small" id="SpinCSS">
                <div className="content" />
            </Spin>
        );
    }

    return (
        <>
            {contextHolder}
            <BreadCrumb />
            <FloatButton onClick={handleFloatButtonClick}/>;
            <div id="pageDiv">
                <Space direction="vertical" id="HomePageSpace" size="large" className="HomeSpace" align="center">
                    <HomeBooksCarousel />
                    <Search
                        placeholder="输入书名以搜索"
                        onSearch={handleSearchByName}
                        enterButton
                        allowClear
                        style={{ width: 400 }}
                    />
                    <Search
                        placeholder="输入作者以搜索"
                        onSearch={handleSearchByAuthor}
                        enterButton
                        allowClear
                        style={{ width: 400 }}
                    />
                    <Search
                        placeholder="输入标签名以搜索"
                        onSearch={handleSearchByTag}
                        enterButton
                        allowClear
                        style={{ width: 400 }}
                    />
                    {usertype === 'Admin' && <BookAdder />}
                </Space>
                {usertype === 'Admin' && (
                    <>
                        <br />
                        <br />
                    </>
                )}
                <div style={{ minWidth: 500 }}>
                    <BookGrids books={filteredBooks} usertype={usertype} />
                </div>
            </div>
        </>
    );
};

export default HomeView;



// import React from 'react';
// import BreadCrumb from '../Components/BreadCrumb';
// import HomeBooksCarousel from "../Components/HomeBooksCarousel";
// import BookGrids from '../Components/BookGrids'
// import '../CSS/HomeCSS.css'
// import {Space, Spin} from "antd";
// import {Navigate} from "react-router-dom";
// import axios from "axios";
// import Search from "antd/es/input/Search";
// import BookAdder from "../Components/BookAdder";
// import books from "../Components/Books";
//
//
// class HomeView extends React.Component{
//
//
//     constructor(props) {
//         super(props);
//         this.state={
//             isAuth:false,
//             isLoading:true,
//             books:[],
//             filterKeyword:'',
//             usertype:'',
//             isLoadingType:true,
//             FilteredBooks:[],
//         };
//     }
//     componentDidMount() {
//         axios.get('/apiAccount/checkLogin')
//             .then(res => {
//                 if (res.status >= 0) {
//                     this.setState(() => ({
//                         isAuth: true,
//                         isLoading:false,
//                     }), () => {
//                         console.log(this.state.isAuth);
//                         console.log(res);
//                     });
//                     axios.get('/apiAccount/checkAdmin')
//                         .then(res => {
//                             if (res.status >= 0) {
//                                 this.setState(() => ({
//                                     usertype: res.data,
//                                     isLoadingType: false,
//                                 }));
//                             }
//                         });
//                 }
//             })
//             .catch(err => {
//                 console.log('err:', err);
//                 this.setState({
//                     isAuth: false ,
//                     isLoading:false,
//                 }, () => {
//                     console.log(this.state.isAuth);
//                 });
//             });
//         axios.get("/apiBook/getbooks")
//             .then(response => {
//                 this.setState({ books: response.data });
//                 this.setState({ FilteredBooks: response.data});
//             })
//             .catch(error => {
//                 console.log(error);
//             });
//     }
//     render(){
//         const { isLoading, isAuth , usertype, isLoadingType} = this.state;
//         if (isLoading) {
//             // 如果仍在等待响应，则返回一个loading状态
//             return(
//                 <Spin tip="Loading" size="small" id='SpinCSS'>
//                     <div className="content" />
//                 </Spin>
//             );
//         }
//         if(!isAuth)
//         {
//             return(<Navigate to='/Login'/>);
//         }
//         if(isLoadingType) {
//             // 如果仍在等待响应，则返回一个loading状态
//             return(
//                 <Spin tip="Loading" size="small" id='SpinCSS'>
//                     <div className="content" />
//                 </Spin>
//             );
//         }
//         return(
//             <>
//                 <BreadCrumb/>
//                 <div id="pageDiv">
//                     <Space direction='vertical' id='HomePageSpace' size='large' className="HomeSpace" align='center'>
//                         <HomeBooksCarousel/>
//                             <Search
//                                 placeholder="输入书名以搜索"
//                                 //  value={this.state.filterKeyword}
//                                 onSearch={(value)=>{
//                                     this.setState({
//                                         FilteredBooks:this.state.books.filter((book) =>
//                                             book.name.includes(value))
//                                     })}
//                                 }
//                                 enterButton
//                                 allowClear
//                                 style={{width: 400,}}
//                             />
//                             <Search
//                                 placeholder="输入作者以搜索"
//                                 onSearch={(value)=>{
//                                     if(value==='')this.setState({FilteredBooks:this.state.books});
//                                     else {
//                                         this.setState({FilteredBooks:[]});
//                                         this.state.books.forEach(book=>{
//                                             axios.get("http://localhost:8763/getAuthor", {params:{title:book.name}})
//                                                 .then(res => {
//                                                     if(res.data===value){
//                                                         this.setState((prevState) => ({
//                                                             FilteredBooks: [...prevState.FilteredBooks, book],
//                                                         }));
//                                                     }
//                                                 })
//                                                 .catch(err =>{
//                                                     console.log(err);
//                                                 })
//                                         })
//                                     }}
//                                 }
//                                 enterButton
//                                 allowClear
//                                 style={{width: 400,}}
//                             />
//                         <Search
//                             placeholder="输入标签名以搜索"
//                             onSearch={(value)=>{
//                                 // this.setState({
//                                 //     FilteredBooks:this.state.books.filter((book) =>
//                                 //         book.name.includes(value))
//                                 // })
//                                 if(value==='')this.setState({FilteredBooks:this.state.books});
//                                 else {
//                                     this.setState({FilteredBooks:[]});
//                                     axios.get("/apiBook/GetBooksByTag", {params:{tagName:value}})
//                                         .then(res => {
//                                             console.log("searching tag");
//                                             console.log(res.data);
//                                             this.setState({FilteredBooks:res.data});
//                                             })
//                                         .catch(err =>{
//                                             console.log(err);
//                                         })
//                                     }
//                                 }}
//                             enterButton
//                             allowClear
//                             style={{width: 400,}}
//                         />
//                         {(this.state.usertype==="Admin")&&(
//                             <BookAdder/>
//                         )}
//                     </Space>
//                     {(this.state.usertype==="Admin")&&(
//                         <><br/><br/></>
//                     )}
//                     <div style={{minWidth:500,}}>
//                         <BookGrids books={this.state.FilteredBooks} usertype={usertype}/>
//                     </div>
//                 </div>
//             </>
//         );
//     }
// }
// export default HomeView;