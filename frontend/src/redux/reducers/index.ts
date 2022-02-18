import {combineReducers} from 'redux';
import {isLoggedReducer} from './loginReducers';
import {userIdReducer} from "./userReducers";
import {desiredPathReducer, categoriesPathReducer} from "./pathReducers";
import {searchPhraseReducer} from "./searchReducers";
import {cartReducer} from "./cartReducers";
import {totalCostReducer} from "./cartReducers";


const reducers = combineReducers({
    desiredPath: desiredPathReducer,
    categoriesPath: categoriesPathReducer,
    isLogged: isLoggedReducer,
    userId: userIdReducer,
    searchPhrase: searchPhraseReducer,
    cart: cartReducer,
    totalCost: totalCostReducer

})

export default reducers