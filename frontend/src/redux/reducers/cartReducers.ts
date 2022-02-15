import {AnyAction} from "@reduxjs/toolkit";

interface LooseObject {
    [key: string]: any
}

export const cartReducer =  (state : LooseObject = new Object, action: AnyAction) => {
    let newState : LooseObject = {...state};
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