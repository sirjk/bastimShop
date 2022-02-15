import {AnyAction} from "@reduxjs/toolkit";
import Cookies from "universal-cookie";

const cookies = new Cookies();
let isLoggedCookie = cookies.get("is_logged")
if(isLoggedCookie == undefined){
    isLoggedCookie = "false";
}


export const isLoggedReducer = (state : string[] = isLoggedCookie, action:AnyAction) => { //No clue why the array works here, but ordinary string does not
    switch (action.type){
        case 'SET_IS_LOGGED':
            return action.payload
        default:
            return state
    }
}

export type RootStateIsLogged = ReturnType<typeof isLoggedReducer>

