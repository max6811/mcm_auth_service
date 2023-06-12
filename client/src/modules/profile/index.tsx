import { FC } from "react";

interface ProfileProps {
    handleLogOut: () => void;
    currentUser: any;
}

const Profile: FC<ProfileProps> = ({ handleLogOut, currentUser }) => {
    return (
        <div>
            <h2>profile</h2>
            <h3>{currentUser.currentUser.name}</h3>
            <p>{currentUser.currentUser.email}</p>
            <br />
            <button onClick={() => handleLogOut}>LogOut</button>
        </div>
    );
};
export default Profile;
