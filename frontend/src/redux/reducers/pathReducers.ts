import {AnyAction} from "@reduxjs/toolkit";

export const categoriesPathReducer =  (state : Object = new Object, action: AnyAction) => {
    switch(action.type){
        case 'SET_CATEGORIES_PATH_MAP':
           // console.log(action.payload);
            return action.payload
        default:
            return state
    }
}

export type RootStateCategoriesPath = ReturnType<typeof categoriesPathReducer>

export const desiredPathReducer =  (state = "/", action: AnyAction) => {
    switch(action.type){
        case 'SET_DESIRED_PATH':
            return action.payload
        default:
            return state
    }
}

export type RootStateDesiredPath = ReturnType<typeof desiredPathReducer>