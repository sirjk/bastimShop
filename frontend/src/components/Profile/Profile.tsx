import React, {FunctionComponent, useEffect, useState} from 'react'
import profileClasses from './profile.module.css'
import appClasses from '../../app.module.css'
import {EditOutlined, EyeOutlined, HistoryOutlined, KeyOutlined, LogoutOutlined} from "@ant-design/icons";
import {Button, DatePicker, Divider, Input, Menu, notification} from "antd";
import {useNavigate} from "react-router-dom";
import registerClasses from "../RegisterPanel/register.module.css";
import moment from 'moment';
import {useDispatch, useSelector} from "react-redux";
import {useCookies} from "react-cookie";
import {setIsLogged} from "../../redux/actions/loginActions"
import {isLoggedReducer, RootStateIsLogged} from "../../redux/reducers/loginReducers";
import auth from "../../actions/auth";
import user1 from '../../actions/user'
import {RootStateUserId} from "../../redux/reducers/userReducers";
import {UserInfo} from "./UserInfo/UserInfo";
import {UserChangePassword} from "./UserChangePassword/UserChangePassword";
import {UserUpdateData} from "./UserUpdateData/UserUpdateData";


interface Props{
}

interface UserType {
    id: number
    firstName: string;
    lastName: string;
    email: string;
    birthDate: string;
    points: number;
    password: string;
    country: string;
    city: string;
    address: string;
    postalAddress: string;
    roles: object;
}

export const Profile: FunctionComponent<Props>=(props: Props)=>{
    const [logoutErrorMsg, setLogoutErrorMsg] = useState('');
    const [userData, setUserData] = useState<UserType>({
        address: "",
        birthDate: "",
        city: "",
        country: "",
        email: "",
        firstName: "",
        id: 0,
        lastName: "",
        password: "",
        points: 0,
        postalAddress: "",
        roles:{}
    });
    const [component, setComponent] = useState(<></>);



    const userId = useSelector((state:RootStateUserId) => state.userId); //w storze przechowywane jest id aktualnie zalogowanego usera

    //wywylanie zapytania na edpoint /profile/{id} w api aby pobrac dane o userze
    useEffect(()=>{
       fetchDataFromServer();
    },[userId])

    function fetchDataFromServer(){
        user1.getById(userId)
        .then((response)=>{
                setUserData(response.data);
            }
        ).catch((e)=>{

        })
    }


    useEffect(()=>{
        content("userInfo", false)
    },[userData])



    const dispatch = useDispatch();
    //wysyłanie zapytania na endpoint /logout w api zeby sie wylogowac
    function logoutBtnHandler(){
        auth.logout().then(
            ()=>{
                dispatch(setIsLogged("false"));
                navigate("/");
            }
        ).catch(() => {
            setLogoutErrorMsg("Nie udało się wylogować");
        });
    }


    const navigate = useNavigate();

    function content (contentType: string, suckDataFlag: boolean){
        if(contentType==="userInfo"){
            if(suckDataFlag == true)
            {
                fetchDataFromServer();
            }
            else{
                setComponent(<UserInfo user={userData} changeContent={content}/>)
            }
            
        }
        if(contentType==="changePassword"){
            setComponent(<UserChangePassword changeContent={content}/>)
        }
        if(contentType==="editInfo"){
            setComponent(<UserUpdateData user={userData} changeContent={content}/>)
        }
    }


    return(
        <div className={profileClasses["profile-window"]}>
            <div className={profileClasses.buttons}>
                <div className={profileClasses.button} onClick={() => {navigate("/profile/orders/")}}><HistoryOutlined /><span style={{marginLeft:"30px"}}>HISTORIA ZAMÓWIEŃ</span></div>
                <div className={profileClasses.button} onClick={logoutBtnHandler}><LogoutOutlined /><span style={{marginLeft:"30px"}}>WYLOGUJ</span></div>
            </div>

            <div className={profileClasses["profile-info-window"]}>
                {component}
            </div>

            <span style={{fontSize:"35px"}}>TWOJE PUNKTY: {userData.points}</span>
        </div>
    )
}


