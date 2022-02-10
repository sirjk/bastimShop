import React, {FunctionComponent, useEffect} from 'react'
import profileClasses from "./UserInfo.module.css";
import user from "../../../actions/user";
import {EditOutlined, KeyOutlined} from "@ant-design/icons";

interface Props{
    user: UserType,
    changeContent: (contentType: string, suckDataFlag: boolean)=>void
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

export const UserInfo: FunctionComponent<Props>=(props: Props)=>{


    const editBtnHandleClick = ()=>{
        props.changeContent("editInfo", false);
    }
    const changePasswordBtnHandleClick = ()=>{
        props.changeContent("changePassword", false);
    }

    return(
        <>
            <div className={profileClasses["profile-info-window-buttons"]}>
                <div onClick={editBtnHandleClick} className={profileClasses["edit-btn"]}>
                    EDYTUJ DANE
                    <EditOutlined />
                </div>
                <div onClick={changePasswordBtnHandleClick} className={profileClasses["change-password-btn"]}>
                    ZMIEŃ HASŁO
                    <KeyOutlined/>
                </div>
            </div>
            <div className={profileClasses["profile-info-window-content"]}>
                <div className={profileClasses["content-left-column"]}>
                    <div className={profileClasses.data}>
                        <div className={profileClasses["user-details"]}>IMIĘ</div>
                        <div className={profileClasses["user-data"]}>{props.user.firstName}</div>
                    </div>
                    <div className={profileClasses.data}>
                        <div className={profileClasses["user-details"]}>NAZWISKO</div>
                        <div className={profileClasses["user-data"]}>{props.user.lastName}</div>
                    </div>
                    <div className={profileClasses.data}>
                        <div className={profileClasses["user-details"]}>E-MAIL</div>
                        <div className={profileClasses["user-data"]}>{props.user.email}</div>
                    </div>
                    <div className={profileClasses.data}>
                        <div className={profileClasses["user-details"]}>DATA URODZENIA</div>
                        <div className={profileClasses["user-data"]}>{props.user.birthDate}</div>
                    </div>


                </div>
                <div className={profileClasses["content-right-column"]}>

                    <div className={profileClasses.data}>
                        <div className={profileClasses["user-details"]}>KRAJ</div>
                        <div className={profileClasses["user-data"]}>{props.user.country}</div>
                    </div>
                    <div className={profileClasses.data}>
                        <div className={profileClasses["user-details"]}>MIASTO</div>
                        <div className={profileClasses["user-data"]}>{props.user.city}</div>
                    </div>
                    <div className={profileClasses.data}>
                        <div className={profileClasses["user-details"]}>ADRES</div>
                        <div className={profileClasses["user-data"]}>{props.user.address}</div>
                    </div>
                    <div className={profileClasses.data}>
                        <div className={profileClasses["user-details"]}>KOD POCZTOWY</div>
                        <div className={profileClasses["user-data"]}>{props.user.postalAddress}</div>
                    </div>
                </div>
            </div>
        </>
    )
}
