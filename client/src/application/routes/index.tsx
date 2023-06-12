import { FC, useEffect, useState } from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import Home from "../../modules/home/Home";
import Login from "../../modules/user/Login";
import PrivateRoutes from "./PrivateRoutes";
import Oauth2Redirect from "../../modules/oauth/Oauth2Redirect";
import { ACCESS_TOKEN } from "../../constants";
import { getCurrentUser } from "../utils/UserUtils";
import Profile from "../../modules/profile";

const BussinessRoutes: FC = () => {
    const [auth, setAuth] = useState(false);
    const [data, setData] = useState({
        currentUser: null,
        authenticated: false,
        loading: true,
    });
    const PAGES = [
        {
            path: "/profile",
            component: Profile,
        },
    ];

    const logout = () => {
        localStorage.removeItem(ACCESS_TOKEN);
        setAuth(false);
    };

    const getUserAuth = () => {
        getCurrentUser()
            .then((res) => {
                setData({
                    ...data,
                    currentUser: res,
                    authenticated: true,
                    loading: false,
                });
                setAuth(true);
            })
            .catch((error) => {
                console.error("error to get user: " + error);
                setData({ ...data, loading: false });
            });
    };

    useEffect(() => {
        getUserAuth();
    }, []);

    // const privateRoutes = PAGES.map((page, index) => {
    //     return
    //     <Route key={index} element={
    //         <PrivateRoutes
    //                     authenticated={auth}
    //                     handleLogOut={logout}
    //                     currentUser={data}
    //                 />
    //     }>

    //     </Route>;
    // });

    return (
        <Routes>
            <Route path='/' element={<Home />} />
            <Route path='/login' element={<Login authenticated={auth} />} />
            <Route
                path='/profile'
                element={
                    auth ? (
                        <Profile handleLogOut={logout} currentUser={data} />
                    ) : (
                        <Navigate to={"/login"} />
                    )
                }
            />
            <Route path='/oauth2/redirect' element={<Oauth2Redirect />} />
        </Routes>
    );
};
export default BussinessRoutes;
