import { json } from "react-router-dom";

const request = (option: any) => {
    const headers = new Headers({
        "Content-Type": "application/json",
    });

    if (localStorage.getItem("accessToken")) {
        headers.append(
            "Authorization",
            "Bearer " + localStorage.getItem("accessToken")
        );
    }

    const defaults = { headers: headers };
    option = Object.assign({}, defaults, option);

    return fetch(option.url, option).then((res) =>
        res.json().then((json) => {
            if (!res.ok) {
                return Promise.reject(json);
            }
            return json;
        })
    );
};

export const getCurrentUser = () => {
    if (!localStorage.getItem("accessToken")) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: "http://localhost:3010/api/user/profile",
        method: "GET",
    });
};
