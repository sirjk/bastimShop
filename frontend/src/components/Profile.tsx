import React, {FunctionComponent, useEffect, useState} from 'react'
import profileClasses from '../css/profile.module.css'
import appClasses from '../css/app.module.css'
import {EditOutlined, EyeOutlined, HistoryOutlined, KeyOutlined, LogoutOutlined} from "@ant-design/icons";
import {Button, DatePicker, Divider, Input, Menu, notification} from "antd";
import {useNavigate} from "react-router-dom";
import registerClasses from "../css/register.module.css";
import moment from 'moment';
import {useDispatch, useSelector} from "react-redux";
import {useCookies} from "react-cookie";
import {setIsLogged} from "../redux/actions/loginActions"
import {isLoggedReducer, RootStateIsLogged} from "../redux/reducers/loginReducers";
import auth from "../actions/auth";
import user1 from '../actions/user'
import {RootStateUserId} from "../redux/reducers/userReducers";


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
}

export const Profile: FunctionComponent<Props>=(props: Props)=>{
    const [passwordText, setPasswordText] = useState<string>("•••••••••");
    const [editBtn, setEditBtn] = useState<string>("EDYTUJ DANE");
    const [changePasswordBtn, setChangePasswordBtn] = useState<string>("ZMIEŃ HASŁO");
    const [password, setPassword] = useState<string>('');
    const [newPassword, setNewPassword] = useState<string>('');
    const [confirmNewPassword, setConfirmNewPassword] = useState<string>('');
    const [registrationErrorMsg, setRegistrationErrorMsg] = useState<string>( '');
    const [passwordStyle, setPasswordStyle] = useState<Object>( {});
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
        postalAddress: ""
    });



    useEffect(() => {
        const timer = setTimeout(() => {
            if(newPassword === confirmNewPassword || confirmNewPassword ==='' || newPassword===''){
                setRegistrationErrorMsg('');
                setPasswordStyle({})
            }
            else{
                setRegistrationErrorMsg('Hasła różnią się');
                setPasswordStyle({borderColor:"red"})
            }
        }, 1000)
        return () => clearTimeout(timer)
    }, [newPassword, confirmNewPassword])

    useEffect(()=>{
        if (registrationErrorMsg!=='')
            openNotificationForPasswordChange();
    },[registrationErrorMsg])



    function openNotificationForPasswordChange(){
        notification.error({
            message: `Error`,
            description: registrationErrorMsg,
            placement: "bottomRight",
        });
    };



    let user: UserType;

    const userId = useSelector((state:RootStateUserId) => state.userId);

    useEffect(()=>{
        user1.getById(userId).then((response)=>{
            user = response.data;
            setUserData(user);
            }

        ).catch((e)=>{

        })
    },[])





    function handleEditClick(editType:string){
        if(editType === "editData"){
            if(editBtn==="EDYTUJ DANE"){
                setEditBtn("ZATWIERDŹ");
            }
            else{
                //PUT METHOD HERE
                setEditBtn("EDYTUJ DANE");
            }
        }
        else if(editType === "editPassword"){
            if(changePasswordBtn==="ZMIEŃ HASŁO"){
                setChangePasswordBtn("ZATWIERDŹ");
            }
            else{
                //PUT METHOD HERE
                setChangePasswordBtn("ZMIEŃ HASŁO");
            }
        }

    }


    const dispatch = useDispatch();

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

    return(
        <div className={profileClasses["profile-window"]}>
            <div className={profileClasses.buttons}>
                <div className={profileClasses.button} onClick={() => {navigate("/profile/orders/")}}><HistoryOutlined /><span style={{marginLeft:"30px"}}>HISTORIA ZAMÓWIEŃ</span></div>
                <div className={profileClasses.button} onClick={logoutBtnHandler}><LogoutOutlined /><span style={{marginLeft:"30px"}}>WYLOGUJ</span></div>
            </div>

            <div className={`${profileClasses["profile-info-window"]} ${appClasses.page} ${appClasses.content}`}>
                <div style={{display:"flex", justifyContent:"space-between", width:"100%"}}>
                    {editBtn==="EDYTUJ DANE"?<></>:<span onClick={()=>handleEditClick("editData")} className={profileClasses["cancel-text"]}>ANULUJ</span>}
                    {changePasswordBtn==="ZMIEŃ HASŁO"?<></>:<span onClick={()=>handleEditClick("editPassword")} className={profileClasses["cancel-text"]}>ANULUJ</span>}
                    <div className={profileClasses["edit-div"]}>
                        {changePasswordBtn==="ZATWIERDŹ"?<></>:
                            <div className={profileClasses["confirm-btn-div"]}>
                                {editBtn==="EDYTUJ DANE"?<></>:<Input.Password style={passwordStyle} onChange={(e)=>setPassword(e.target.value)}
                                                                               className={registerClasses.input} id={"register-password"}
                                                                               placeholder={"Podaj Hasło"}
                                />}
                                <div onClick={()=>handleEditClick("editData")} className={profileClasses["edit-text"]}>{editBtn}{editBtn==="ZATWIERDŹ"?<></>:<EditOutlined />}</div>
                            </div>}
                        {editBtn==="ZATWIERDŹ"?<></>:
                            <div className={profileClasses["confirm-btn-div"]}>
                                {changePasswordBtn==="ZMIEŃ HASŁO"?<></>:<Input.Password style={passwordStyle} onChange={(e)=>setPassword(e.target.value)}
                                                                                         className={registerClasses.input} id={"register-password"}
                                                                                         placeholder={"Stare Hasło"}
                                />}
                                <div onClick={()=>handleEditClick("editPassword")} className={profileClasses["edit-text"]}>{changePasswordBtn}{changePasswordBtn==="ZATWIERDŹ"?<></>:<KeyOutlined />}</div>
                            </div>
                            }
                    </div>
                </div>
                <div className={profileClasses["profile-info-window-content"]}>
                    <div className={registerClasses["form-div"]}>
                        <div className={registerClasses["input-row"]}>
                            <div className={profileClasses["data-div-row"]}>
                                {editBtn === "EDYTUJ DANE" && changePasswordBtn === "ZMIEŃ HASŁO" || editBtn === "ZATWIERDŹ"  ?
                                    <>
                                        <span className={profileClasses["text-before"]}>IMIĘ </span>
                                        {editBtn==="EDYTUJ DANE"?
                                            <span className={profileClasses["text-data"]}>{userData.firstName}</span>:
                                            <Input className={registerClasses.input}  id={"register-first-name"} defaultValue={userData.firstName}/>
                                        }
                                    </>:<></>
                                }
                            </div>
                            <div className={profileClasses["data-div-row"]}>
                                {editBtn === "EDYTUJ DANE" && changePasswordBtn === "ZMIEŃ HASŁO" || editBtn === "ZATWIERDŹ"  ?
                                    <>
                                        <span className={profileClasses["text-before"]}>NAZWISKO </span>
                                        {editBtn === "EDYTUJ DANE" ?
                                            <span className={profileClasses["text-data"]}>{userData.lastName}</span> :
                                            <Input className={registerClasses.input} id={"register-last-name"}
                                                   defaultValue={userData.lastName}/>
                                        }
                                    </>:<></>
                                }
                            </div>
                            <div className={profileClasses["data-div-row"]}>
                                {editBtn === "EDYTUJ DANE" && changePasswordBtn === "ZMIEŃ HASŁO" || editBtn === "ZATWIERDŹ"  ?
                                    <>
                                        <span className={profileClasses["text-before"]}>E-MAIL </span>
                                        {editBtn === "EDYTUJ DANE" ?
                                            <div className={profileClasses["text-data"]}>{userData.email}</div> :
                                            <Input className={registerClasses.input} id={"register-email"} defaultValue={userData.email}/>
                                        }
                                    </>:<></>
                                }
                            </div>
                            <div className={profileClasses["data-div-row"]}>
                                {editBtn === "EDYTUJ DANE" && changePasswordBtn === "ZMIEŃ HASŁO" || editBtn === "ZATWIERDŹ"  ?
                                    <>
                                        <span className={profileClasses["text-before"]}>DATA URODZENIA </span>
                                        {editBtn === "EDYTUJ DANE" ?
                                            <span className={profileClasses["text-data"]}>{userData.birthDate}</span> :
                                            <DatePicker className={registerClasses.input} defaultValue={moment(userData.birthDate, 'YYYY-MM-DD')} placeholder={"Data urodzenia"}
                                                        id={"register-birth-date"}/>
                                        }
                                    </>:<></>
                                }
                            </div>
                            {/*{editBtn === "EDYTUJ DANE" ?<></>: <Divider/>}*/}
                            <div className={profileClasses["data-div-row"]}>
                                {changePasswordBtn === "ZMIEŃ HASŁO" ?
                                    <></> :
                                    <>
                                        <span className={profileClasses["text-before"]}>NOWE HASŁO </span>
                                        <Input.Password style={passwordStyle} onChange={(e)=>setNewPassword(e.target.value)}
                                                        className={registerClasses.input} id={"register-password"}
                                                        placeholder={"Nowe Hasło"}
                                                        />
                                    </>
                                }
                            </div>

                        </div>

                        <div className={registerClasses["input-row"]}>
                            <div className={profileClasses["data-div-row"]}>
                                {editBtn === "EDYTUJ DANE" && changePasswordBtn === "ZMIEŃ HASŁO" || editBtn === "ZATWIERDŹ"  ?
                                    <>
                                        <span className={profileClasses["text-before"]}>KRAJ </span>
                                        {editBtn === "EDYTUJ DANE" ?
                                            <span className={profileClasses["text-data"]}>{userData.country}</span> :
                                            <Input className={registerClasses.input} id={"register-country"}
                                                   defaultValue={userData.country}/>
                                        }
                                    </>:<></>
                                }
                            </div>
                            <div className={profileClasses["data-div-row"]}>
                                {editBtn === "EDYTUJ DANE" && changePasswordBtn === "ZMIEŃ HASŁO" || editBtn === "ZATWIERDŹ"  ?
                                    <>
                                        <span className={profileClasses["text-before"]}>MIASTO </span>
                                        {editBtn === "EDYTUJ DANE" ?
                                            <span className={profileClasses["text-data"]}>{userData.city}</span> :
                                            <Input className={registerClasses.input} id={"register-city"} defaultValue={userData.city}/>
                                        }
                                    </>:<></>
                                }
                            </div>
                            <div className={profileClasses["data-div-row"]}>
                                {editBtn === "EDYTUJ DANE" && changePasswordBtn === "ZMIEŃ HASŁO" || editBtn === "ZATWIERDŹ"  ?
                                    <>
                                        <span className={profileClasses["text-before"]}>ADRES </span>
                                        {editBtn === "EDYTUJ DANE" ?
                                            <span className={profileClasses["text-data"]}>{userData.address}</span> :
                                            <Input className={registerClasses.input} id={"register-address"} defaultValue={userData.address}/>
                                        }
                                    </>:<></>
                                }
                            </div>
                            <div className={profileClasses["data-div-row"]}>
                                {editBtn === "EDYTUJ DANE" && changePasswordBtn === "ZMIEŃ HASŁO" || editBtn === "ZATWIERDŹ"  ?
                                    <>
                                        <span className={profileClasses["text-before"]}>KOD POCZTOWY </span>
                                        {editBtn === "EDYTUJ DANE" ?
                                            <span className={profileClasses["text-data"]}>{userData.postalAddress}</span> :
                                            <Input className={registerClasses.input} id={"register-postal-address"}
                                                   defaultValue={userData.postalAddress}/>
                                        }
                                    </>:<></>
                                }
                            </div>
                            {/*{editBtn === "EDYTUJ DANE" ?<></>: <Divider/>}*/}
                            <div className={profileClasses["data-div-row"]}>
                                {changePasswordBtn === "ZMIEŃ HASŁO" ?
                                    <></> :
                                    <>
                                        <span className={profileClasses["text-before"]}>POTWIERDŹ HASŁO </span>
                                        <Input.Password onChange={(e)=>setConfirmNewPassword(e.target.value)}
                                                        className={registerClasses.input} id={"register-confirm-password"}
                                                        placeholder={"Potwierdź hasło"}
                                                        style={passwordStyle}/>
                                    </>
                                }
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <span style={{fontSize:"35px"}}>TWOJE PUNKTY: {userData.points}</span>
        </div>
    )
}


