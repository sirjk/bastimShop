import {AnyAction} from "@reduxjs/toolkit";


export const searchPhraseReducer = (state = "", action:AnyAction) => {
    switch (action.type){
        case 'SET_SEARCH_PHRASE':
            return action.payload
        default:
            return state
    }
}

export type RootStateSearchPhrase = ReturnType<typeof searchPhraseReducer>