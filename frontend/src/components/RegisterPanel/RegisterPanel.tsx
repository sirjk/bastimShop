import React, {FunctionComponent, useState, useEffect} from 'react'
import {Link, useNavigate} from "react-router-dom";
import logoClasses from "../Navbar/navbar.module.css";
import registerClasses from "./register.module.css";
import appClasses from "../../app.module.css";
import {Button, Checkbox, DatePicker, Input, notification} from "antd";
import register from '../../actions/register'
import {setIsLogged} from "../../redux/actions/loginActions";
import moment from "moment";
import {useSelector} from "react-redux";
import {RootStateDesiredPath} from "../../redux/reducers/pathReducers";


interface Props{
}


export const RegisterPanel: FunctionComponent<Props>=(props: Props)=>{
    const [firstName, setFirstName] = useState<string>('');
    const [lastName, setLastName] = useState<string>('');
    const [email, setEmail] = useState<string>('');
    const [address, setAddress] = useState<string>('');
    const [birthDate, setBirthDate] = useState<string>('');
    const [city, setCity] = useState<string>('');
    const [country, setCountry] = useState<string>('');
    const [postalAddress, setPostalAddress] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [confirmPassword, setConfirmPassword] = useState<string>('');
    const [registrationErrorMsg, setRegistrationErrorMsg] = useState<string>( '');
    const [passwordStyle, setPasswordStyle] = useState<Object>( {});
    const [registerErrorMsg, setRegisterErrorMsg] = useState('');

    useEffect(() => {
        const timer = setTimeout(() => {
            if(password === confirmPassword || confirmPassword ==='' || password===''){
                setRegistrationErrorMsg('');
                setPasswordStyle({})
            }
            else{
                setRegistrationErrorMsg('Hasła różnią się');
                setPasswordStyle({borderColor:"red"})
            }
        }, 1000)
        return () => clearTimeout(timer)
    }, [password, confirmPassword])

    useEffect(()=>{
        if (registrationErrorMsg!=='')
            openNotification();
    },[registrationErrorMsg])



    function openNotification(){
        notification.error({
            message: `Error`,
            description: registrationErrorMsg,
            placement: "bottomRight",
        });
    };

    function passwordChangeHandler(event:any){
        let newValue = event.target.value;
        setPassword(newValue);
    }

    function confirmPasswordChangeHandler(event:any){
        let newValue = event.target.value;
        setConfirmPassword(newValue);
    }

    useEffect(()=>{
        if (registerErrorMsg!=='')
            registerErrorNotification();
    },[registerErrorMsg])



    function registerErrorNotification(){
        notification.error({
            message: `Error`,
            description: registerErrorMsg,
            placement: "bottomRight",
        });
    };

    const whereToNavigate = useSelector((state: RootStateDesiredPath)=>  state.desiredPath);
    const navigate = useNavigate();

    function registenBtnHandler(){
        register.register(firstName,lastName,email,password,address,birthDate,city,country,postalAddress).then(
            () =>{
               // let temp = cookies.get('is_logged_in')
                // console.log(cookies.isLoggedIn.value)
                //dispatch(setIsLogged(temp));
                navigate(whereToNavigate)
            }
        ).catch(() => setRegisterErrorMsg("Nie udało się zarejestrowć"))
    }


    return(
        <div className={registerClasses["register-site"]}>
            <div>
                <Link to={"/"}>
                   <span className={logoClasses.logo} style={{fontSize: "60px"}}>
                       BASTIM
                   </span>
                </Link>
            </div>
                <div className={`${registerClasses["register-window"]} ${appClasses.page} ${appClasses.content}`}>
                    <span style={{fontSize: "35px", fontWeight: "400", letterSpacing: "2px"}}>
                        Zarejestruj się
                    </span>
                    <div className={registerClasses["form-div"]}>
                        <div className={registerClasses["input-row"]}>
                            <div className={registerClasses["input-div"]}>
                                <span className={registerClasses["input-text-before"]}>IMIĘ </span>
                                <Input onChange={(e)=>setFirstName(e.target.value)} className={registerClasses.input}  id={"register-first-name"} placeholder={"Imię"}/>
                            </div>
                            <div className={registerClasses["input-div"]}>
                                <span className={registerClasses["input-text-before"]}>NAZWISKO </span>
                                <Input onChange={(e)=>setLastName(e.target.value)} className={registerClasses.input} id={"register-last-name"} placeholder={"Nazwisko"}/>
                            </div>
                            <div className={registerClasses["input-div"]}>
                                <span className={registerClasses["input-text-before"]}>E-MAIL </span>
                                <Input onChange={(e)=>setEmail(e.target.value)} className={registerClasses.input} id={"register-email"} placeholder={"E-mail"}/>
                            </div>
                            <div className={registerClasses["input-div"]}>
                                <span className={registerClasses["input-text-before"]}>HASŁO </span>
                                <Input.Password style={passwordStyle} onChange={passwordChangeHandler} className={registerClasses.input} id={"register-password"} placeholder={"Hasło"}/>
                            </div>
                            <div className={registerClasses["input-div"]}>
                                <span className={registerClasses["input-text-before"]}>POTWIERDŹ HASŁO </span>
                                <Input.Password style={passwordStyle} onChange={confirmPasswordChangeHandler} className={registerClasses.input} id={"register-confirm-password"} placeholder={"Potwierdź hasło"}/>
                            </div>
                        </div>

                        <div className={registerClasses["input-row"]}>
                            <div className={registerClasses["input-div"]}>
                                <span className={registerClasses["input-text-before"]}>DATA URODZENIA </span>
                                <DatePicker onChange={(date, dateString) => setBirthDate(dateString)} className={registerClasses.input} placeholder={"Data urodzenia"} id={"register-birth-date"} />
                            </div>
                            <div className={registerClasses["input-div"]}>
                                <span className={registerClasses["input-text-before"]}>KRAJ </span>
                                <Input onChange={(e)=>setCountry(e.target.value)} className={registerClasses.input} id={"register-country"} placeholder={"Kraj"}/>
                            </div>
                            <div className={registerClasses["input-div"]}>
                                <span className={registerClasses["input-text-before"]}>MIASTO </span>
                                <Input onChange={(e)=>setCity(e.target.value)} className={registerClasses.input} id={"register-city"} placeholder={"Miasto"}/>
                            </div>
                            <div className={registerClasses["input-div"]}>
                                <span className={registerClasses["input-text-before"]}>ADRES </span>
                                <Input onChange={(e)=>setAddress(e.target.value)} className={registerClasses.input} id={"register-address"} placeholder={"Adres"}/>
                            </div>
                            <div className={registerClasses["input-div"]}>
                                <span className={registerClasses["input-text-before"]}>KOD POCZTOWY </span>
                                <Input onChange={(e)=>setPostalAddress(e.target.value)} className={registerClasses.input} id={"register-postal-address"} placeholder={"Kod pocztowy"}/>
                            </div>
                        </div>
                    </div>

                    <div className={registerClasses["checkbox-div"]}>
                        <Checkbox style={{margin: "0px"}}>
                            Akceptuję <Link to={"/about"}>regulamin</Link> sklepu
                        </Checkbox>
                        <Checkbox style={{margin: "0px"}}>
                            Wyrażam zgodę na otrzymywanie informacji handlowych na e-mail
                        </Checkbox>
                    </div>
                    <Button onClick={registenBtnHandler} className={registerClasses["register-btn"]} type="primary">ZAREJESTRUJ</Button>
                </div>
            <span style={{marginTop: "5px"}}>
                Masz już konto? <Link to={"/login"}>Zaloguj się</Link>
            </span>

        </div>
    )
}


