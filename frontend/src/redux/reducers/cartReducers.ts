import {AnyAction} from "@reduxjs/toolkit";

interface LooseObject {
    [key: string]: number
}

export const cartReducer =  (state : LooseObject = {}, action: AnyAction) => {
    let newState : LooseObject = {...state};
    console.log(newState)
    switch(action.type){
        case 'ADD_TO_CART':
            newState[action.payload.product.id] =  action.payload;
            return newState;

        case 'DELETE_FROM_CART':
            delete newState[action.payload];
            return newState;


        case 'CHANGE_QUANTITY_IN_CART':
            newState[action.payload.productId] = action.payload.newQuantity;
            return newState;


        default:
            return state;
    }
}

export type RootStateCart = ReturnType<typeof cartReducer>

export const totalCostReducer =  (state : number = 0.0, action: AnyAction) => {
  
    switch(action.type){
        case 'SET_TOTAL_COST':
            return action.payload;
        default:
            return state;
    }
}

export type RootStateTotalCost = ReturnType<typeof totalCostReducer>