import {combineReducers} from 'redux';
import {isLoggedReducer} from './loginReducers';
import {userIdReducer} from "./userReducers";
import {desiredPathReducer, categoriesPathReducer} from "./pathReducers";
import {searchPhraseReducer} from "./searchReducers";


const reducers = combineReducers({
    desiredPath: desiredPathReducer,
    categoriesPath: categoriesPathReducer,
    isLogged: isLoggedReducer,
    userId: userIdReducer,
    searchPhrase: searchPhraseReducer

})

export default reducers