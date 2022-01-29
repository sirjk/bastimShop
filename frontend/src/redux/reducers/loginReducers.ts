import {AnyAction} from "@reduxjs/toolkit";


export const isLoggedReducer = (state = "false", action:AnyAction) => {
    switch (action.type){
        case 'SET_IS_LOGGED':
            return action.payload
        default:
            return state
    }
}

export type RootStateIsLogged = ReturnType<typeof isLoggedReducer>

