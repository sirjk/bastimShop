import {AnyAction} from "@reduxjs/toolkit";
import Cookies from "universal-cookie";

const cookies = new Cookies();
let userId = cookies.get("user_id")
if(userId == undefined)
    userId = null;

export const userIdReducer = (state = userId, action:AnyAction) => {
    switch (action.type){
        case 'SET_USER_ID':
            return action.payload
        default:
            return state
    }
}

export type RootStateUserId = ReturnType<typeof userIdReducer>