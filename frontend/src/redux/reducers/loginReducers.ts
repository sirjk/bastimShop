import {AnyAction} from "@reduxjs/toolkit";
import Cookies from "universal-cookie";

const cookies = new Cookies();


export const isLoggedReducer = (state = cookies.get("is_logged"), action:AnyAction) => {
    switch (action.type){
        case 'SET_IS_LOGGED':
            return action.payload
        default:
            return state
    }
}

export type RootStateIsLogged = ReturnType<typeof isLoggedReducer>

