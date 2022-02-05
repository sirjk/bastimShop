import React, {FunctionComponent, useEffect, useState} from 'react'
import changePasswordClasses from "./UserChangePassword.module.css";
import profileClasses from "../profile.module.css";
import {Button, Input, notification} from "antd";
import registerClasses from "../../RegisterPanel/register.module.css";

interface Props{
    changeContent: (contentType: string)=>void
}

export const UserChangePassword: FunctionComponent<Props>=(props: Props)=>{
    const [oldPassword, setOldPassword] = useState<string>('');
    const [newPassword, setNewPassword] = useState<string>('');
    const [confirmNewPassword, setConfirmNewPassword] = useState<string>('');
    const [errorMsg, setErrorMsg] = useState<string>('');
    const [passwordStyle, setPasswordStyle] = useState<Object>( {});
    const [buttonDisabled, setButtonDisabled] = useState<boolean>( true);


    useEffect(() => {
        const timer = setTimeout(() => {
            if(newPassword === confirmNewPassword || confirmNewPassword ==='' || newPassword===''){
                setErrorMsg('');
                setPasswordStyle({})
            }
            else{
                setErrorMsg('Hasła różnią się');
                setPasswordStyle({borderColor:"red"})
            }
            if(newPassword === confirmNewPassword && confirmNewPassword !=='' && newPassword!=='' && newPassword.length<8){
                setErrorMsg('Hasła muszą mieć co najmniej 8 znaków');
                setPasswordStyle({borderColor:"red"})
            }

        }, 1000)
        return () => clearTimeout(timer)
    }, [newPassword, confirmNewPassword])

    useEffect(()=>{
        if (errorMsg!=='')
            openNotificationForPasswordChange();
    },[errorMsg])



    useEffect(()=>{
        if(oldPassword!=='' && newPassword === confirmNewPassword && newPassword!=='' && newPassword.length>=8){
            setButtonDisabled(false)
        }
        else{
            setButtonDisabled(true)
        }
    },[newPassword, confirmNewPassword, oldPassword])



    function openNotificationForPasswordChange(){
        notification.error({
            message: `Error`,
            description: errorMsg,
            placement: "bottomRight",
        });
    };



    const handleCancelBtnClick = ()=>{
        props.changeContent("userInfo")
    }

    return(
        <>
            <div className={changePasswordClasses.buttons}>
                <div onClick={handleCancelBtnClick} className={changePasswordClasses["cancel-btn"]}>
                    ANULUJ
                </div>
            </div>
            <div className={changePasswordClasses.content}>
                <div className={changePasswordClasses["new-password-div"]}>
                    <div className={changePasswordClasses["left-column"]}>
                        <span className={changePasswordClasses["text-before"]}>NOWE HASŁO<span style={{color:"#e80000", fontSize:"20px"}}>*</span>  </span>
                        <Input.Password style={passwordStyle} onChange={(e)=>setNewPassword(e.target.value)}
                                        className={changePasswordClasses.input}
                                        placeholder={"Nowe Hasło"}
                        />
                    </div>
                    <div className={changePasswordClasses["right-column"]}>
                        <span className={changePasswordClasses["text-before"]}>POTWIERDŹ HASŁO<span style={{color:"#e80000", fontSize:"20px"}}>*</span>  </span>
                        <Input.Password style={passwordStyle} onChange={(e)=>setConfirmNewPassword(e.target.value)}
                                        className={changePasswordClasses.input}
                                        placeholder={"Potwierdź Hasło"}
                        />
                    </div>
                </div>
                <div className={changePasswordClasses["old-password-div"]}>
                    <span className={changePasswordClasses["text-before"]}>STARE HASŁO<span style={{color:"#e80000", fontSize:"20px"}}>*</span> </span>
                    <Input.Password className={changePasswordClasses.input}
                                    onChange={(e)=>setOldPassword(e.target.value)}
                                    placeholder={"Stare Hasło"}
                    />
                </div>
                <Button disabled={buttonDisabled} className={changePasswordClasses["confirm-btn"]} type={"primary"}>ZATWIERDŹ ZMIANY</Button>


            </div>
        </>
    )
}
