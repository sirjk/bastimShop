import React, {FunctionComponent, useState} from 'react'
import logoClasses from "../css/navbar.module.css";
import loginClasses from "../css/login.module.css";
import appClasses from "../css/app.module.css";
import {Link, useNavigate} from "react-router-dom";
import {Button, Input} from 'antd';
import auth from '../actions/auth'
import {useDispatch, useSelector} from "react-redux";
import {setIsLogged} from "../redux/actions/loginActions"
import {setUserId} from "../redux/actions/userActions"
import Cookies from 'universal-cookie';
import {RootStateDesiredPath} from '../redux/reducers/pathReducers'


interface Props{
}

export const LoginPage: FunctionComponent<Props>=(props: Props)=>{
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [errorMsg, setErrorMsg] = useState<string>('');

    const navigate = useNavigate();
    const whereToNavigate = useSelector((state: RootStateDesiredPath)=>  state.desiredPath);
    
    const dispatch = useDispatch();
    const cookies = new Cookies();

    function handleLoginClick(e:any){
       // e.preventDefault();

        auth.login(email, password).then(
            () =>{
               dispatch(setIsLogged("true"));
               let userId = cookies.get('user_id')
                dispatch(setUserId(userId))
               navigate(whereToNavigate);
            }
        ).catch(() => setErrorMsg("Nie udało się zalogować"))
    }

    function handleKeyDown(e: any){
        // e.preventDefault();
 
         if(e.key ==='Enter'){
            handleLoginClick(e);
         }
     }


    return(
        <div className={loginClasses["login-site"]}>
            <div>
                <Link to={"/"}>
                   <span className={logoClasses.logo} style={{fontSize: "60px"}}>
                       BASTIM
                   </span>
                </Link>
            </div>
            <div className={loginClasses["login-register-parent"]}>
                <div className={`${loginClasses["login-window"]} ${appClasses.page} ${appClasses.content}`}>
                    <span style={{fontSize: "35px", fontWeight: "400", letterSpacing: "2px"}}>
                        Zaloguj się
                    </span>
                    <Input
                        onChange={(e)=>setEmail(e.target.value)}
                        className={loginClasses["login-input"]}
                        placeholder={"E-mail"}
                        onKeyDown={handleKeyDown}
                        />
                    <Input.Password
                        onChange={(e)=>setPassword(e.target.value)}
                        className={loginClasses["password-input"]}
                        placeholder="Hasło" 
                        onKeyDown={handleKeyDown}/>
                    <Link to={""}>             {/*trzeba bedzie dodać opcje przypomnienia hasła*/}
                        <span>Nie pamiętasz hasła?</span>
                    </Link>
                    <Button onClick={handleLoginClick} className={loginClasses["login-btn"]} type="primary">ZALOGUJ</Button>
                    <div style={{color: "red"}}>{errorMsg}</div>
                </div>
                <div className={`${loginClasses["register-window"]} ${appClasses.page} ${appClasses.content}`}>
                    <span style={{fontSize: "30px", fontWeight: "400", letterSpacing: "2px"}}>
                        Nie posiadasz konta?
                    </span>

                        <Button className={loginClasses["login-btn"]}>
                            <Link to={"/register"}>
                                ZAŁÓŻ KONTO
                            </Link>
                        </Button>

                </div>
            </div>
        </div>

    )
}


