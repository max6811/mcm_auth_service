import type { FC } from "react";

interface SampleLoginProps {
    uri: string
    logo:string
    imgAlt: string
    text: string
}

const SampleLogin: FC<SampleLoginProps> = ({uri, logo, imgAlt, text}) => {
    return (
        <div className='github-login'>
            <a href={uri}>
                <img src={logo} alt={imgAlt} /> {text}
            </a>
        </div>
    );
};
export default SampleLogin;
