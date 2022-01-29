import {AnyAction} from "@reduxjs/toolkit";


export const userIdReducer = (state = null, action:AnyAction) => {
    switch (action.type){
        case 'SET_USER_ID':
            return action.payload
        default:
            return state
    }
}

export type RootStateUserId = ReturnType<typeof userIdReducer>