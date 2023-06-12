import { FC } from "react";
import { Navigate, Outlet, Route } from "react-router-dom";
import Profile from "../../modules/profile";

interface PrivateRoutesProps {
    handleLogOut: () => void;
    authenticated: boolean;
    currentUser: any;
}
const PrivateRoutes: FC<PrivateRoutesProps> = ({
    handleLogOut,
    authenticated,
    currentUser
}) => {
    return (
        <div>
            {!authenticated ? (
                // <Route
                //     path='/profile'
                //     element={<Profile handleLogOut={handleLogOut} currentUser={currenUser} />}
                // />
                <Outlet/>
            ) : (
                <Navigate to={"/login"} />
            )}
        </div>
    );
};
export default PrivateRoutes;
