import {AnyAction} from "@reduxjs/toolkit";
import Cookies from "universal-cookie";

const cookies = new Cookies();
let isLoggedCookie = cookies.get("is_logged")
if(isLoggedCookie == undefined){
    isLoggedCookie = "false";
}


export const isLoggedReducer = (state = isLoggedCookie, action:AnyAction) => {
    switch (action.type){
        case 'SET_IS_LOGGED':
            return action.payload
        default:
            return state
    }
}

export type RootStateIsLogged = ReturnType<typeof isLoggedReducer>

