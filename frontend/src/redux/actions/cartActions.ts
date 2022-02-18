export const addToCart = (cartEntry: Object) => {
    return {
        type: "ADD_TO_CART",
        payload: cartEntry
    }
}

export const deleteFromCart = (productId: string) =>{
    return {
        type: "DELETE_FROM_CART",
        payload: productId
    }
}

export const changeQuantityInCart = (productId: string, newQuantity: number) =>{
    return {
        type: "CHANGE_QUANTITY_IN_CART",
        payload: {
            "productId": productId,
            "newQuantity": newQuantity
        }
    }
}

export const setTotalCost= (newTotalCost: number) =>{
    return {
        type: "setTotalCost",
        payload: newTotalCost
    }
}