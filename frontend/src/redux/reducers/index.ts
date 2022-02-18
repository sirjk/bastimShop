import {combineReducers} from 'redux';
import {isLoggedReducer} from './loginReducers';
import {userIdReducer} from "./userReducers";
import {desiredPathReducer, categoriesPathReducer, categoryPathBeforeProductDetailsReducer} from "./pathReducers";
import {searchPhraseReducer} from "./searchReducers";
import {cartReducer} from "./cartReducers";
import {totalCostReducer} from "./cartReducers";
import {setCategoryPath} from "../actions/pathActions";


const reducers = combineReducers({
    desiredPath: desiredPathReducer,
    categoriesPath: categoriesPathReducer,
    isLogged: isLoggedReducer,
    userId: userIdReducer,
    searchPhrase: searchPhraseReducer,
    cart: cartReducer,
    totalCost: totalCostReducer,
    categoryPathBeforeProductDetails: categoryPathBeforeProductDetailsReducer
})

export default reducers