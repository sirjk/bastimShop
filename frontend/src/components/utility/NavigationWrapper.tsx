import React, {FunctionComponent, useEffect} from 'react'
import {useDispatch, useSelector} from "react-redux";
import {setDesiredPath} from "../../redux/actions/pathActions";
import {Navigate} from "react-router-dom";
import {RootStateIsLogged} from "../../redux/reducers/loginReducers";



interface Props{
    loginRequired:string
    children: any
}




export const NavigationWrapper: FunctionComponent<Props>=(props: Props)=>{

    const dispatch = useDispatch()
    const isSomebodyLogged = useSelector((state:RootStateIsLogged) => state.isLogged);
    dispatch(setDesiredPath(window.location.pathname))
    




    return(
        <>{ (isSomebodyLogged != "true" && props.loginRequired === "true")? <Navigate to={"/login"}/>:props.children }</>
    );


}


