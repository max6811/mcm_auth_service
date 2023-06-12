import { FC } from "react";
import SampleLogin from "../../components/SampleLogin";
import { Navigate } from "react-router-dom";

interface LoginProps {
    authenticated: boolean;
}

const Login: FC<LoginProps> = ({ authenticated }) => {
    const loginOptions = [
        {
            uri: "http://localhost:3010/login/oauth2/authorization/google?redirect_uri=http://localhost:5173/oauth2/redirect",
            logoUri: "",
            imgAlt: "google",
            text: "Login with Google",
        },
        {
            uri: "http://localhost:3010/login/oauth2/authorization/github?redirect_uri=http://localhost:5173/oauth2/redirect",
            logoUri: "",
            imgAlt: "github",
            text: "Login with GitHub",
        },
    ];

    if (authenticated) {
        return <Navigate to={"/profile"} />;
    }

    return (
        <div className='container'>
            <div className='content'>
                <h1>Login</h1>
                {loginOptions.map((option) => (
                    <SampleLogin
                        key={option.imgAlt}
                        uri={option.uri}
                        logo={option.logoUri}
                        imgAlt={option.imgAlt}
                        text={option.text}
                    />
                ))}
            </div>
        </div>
    );
};
export default Login;
