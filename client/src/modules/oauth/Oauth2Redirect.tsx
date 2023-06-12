import { Navigate, useLocation } from "react-router-dom";
import { ACCESS_TOKEN } from "../../constants";

const Oauth2Redirect = () => {
    const location = useLocation();
    console.log(location);
    const getUrlParameter = (name: any) => {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        const regex = new RegExp("[\\?&]" + name + "=([^&#]*)");

        const results = regex.exec(location.search);
        console.log(name);
        console.log(regex);
        console.log(results);
        return results === null
            ? ""
            : decodeURIComponent(results[1].replace(/\+/g, " "));
    };

    const token = getUrlParameter("token");
    console.log(token);
    const error = getUrlParameter("error");
    console.log(error);

    if (token) {
        localStorage.setItem(ACCESS_TOKEN, token);
        return <Navigate to={"/profile"} />;
    } else {
        return <Navigate to={"/login"} />;
    }
};
export default Oauth2Redirect;
