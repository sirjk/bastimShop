import React, {FunctionComponent, useEffect, useState} from 'react'
import classesGlobal from "../../../app.module.css";
import classes from "./cartEntry.module.css";
import {Button, Divider, Input, InputNumber, notification} from "antd";
import {CheckCircleOutlined, ExclamationCircleOutlined, MinusOutlined, PlusCircleOutlined, CloseOutlined} from "@ant-design/icons";
import { useDispatch, useSelector } from 'react-redux';
import {RootStateCart} from "../../../redux/reducers/cartReducers";
import {addToCart, deleteFromCart, changeQuantityInCart} from "../../../redux/actions/cartActions";

interface Props{
    productId: string,
    productPriceForOneItem: number,
    productPreviousPriceForOneItem: number | null,
    productQuantity: number
}

interface LooseObject {
    [key: string]: number
}

interface incrementDecrementButtonsState{
    incrementDisabled: boolean,
    decrementDisabled: boolean
}



export const CartEntry: FunctionComponent<Props>=(props: Props)=>{
    
    const [incrementDecrementButtonDisabled, setIncrementDecrementButtonDisabled] = useState<incrementDecrementButtonsState>({incrementDisabled: false, decrementDisabled: false});

    let cart: any = useSelector((state: RootStateCart) =>state.cart);
    const dispatch = useDispatch();

    useEffect(()=>{
        let newState : incrementDecrementButtonsState = {incrementDisabled:incrementDecrementButtonDisabled.incrementDisabled, decrementDisabled: incrementDecrementButtonDisabled.decrementDisabled};


        if(cart[props.productId]<2){
            newState.decrementDisabled = true;
        }
        else{
            newState.decrementDisabled = false;
        }

        if(cart[props.productId]>=props.productQuantity){
            newState.incrementDisabled = true;
        }
        else{
            newState.incrementDisabled = false;
        }

        setIncrementDecrementButtonDisabled(newState);

    }, [cart[props.productId]])


    function handleDecreaseClick(){
        if(cart[props.productId]>1)
            dispatch(changeQuantityInCart(props.productId, cart[props.productId] - 1))
    }

    function handleIncreaseClick(){
        if(cart[props.productId]<props.productQuantity)
            dispatch(changeQuantityInCart(props.productId, cart[props.productId] + 1))
        else{
            displayNotEnoughQuantity()

        }

    }

    function displayNotEnoughQuantity(){
        notification.error({
            message: `Error`,
            description: "Nie wystarczająca ilość produktu na magazynie",
            placement: "bottomRight",
        });
    }

    function handleRemoveFormCartButton(){

    }

    console.log("aaaaaaaaaa")

    return(
        <div className={classes["cart-entry-flex-box"]}>
            <div className={classes["cart-entry-left-side"]}>
                <label className={classes["checkbox-container"]}>
                    <input type="checkbox"/>
                    <span className={classes["checkbox-checkmark"]}></span>
                </label>
                <img className={classes["cart-entry-img"]} src="https://laptopshop.pl/pol_pl_Sruba-mocujaca-wentylator-czarna-M4x10-srubka-2215_2.png"/>
                <div className={classes["cart-entry-info"]}>
                    <div className={classes["cart-entry-info-title"]}>
                        Non Nobis Domine!
                    </div>
                    
                </div>
            </div>
            <div className={classes["cart-entry-right-side"]}>
                <MinusOutlined onClick={handleDecreaseClick} className={`${classes["increase-decrease-btn"]} ${classesGlobal["noselect"]} ${incrementDecrementButtonDisabled.decrementDisabled && classes["increase-decrease-btn-disabled"]}`} />
                <input defaultValue={1} value={cart[props.productId]} onChange={value => 
                    {
                        
                        let caculatedValue = Math.floor(Number(value));
                        
    
                        if(caculatedValue>props.productQuantity){
                            //console.log(value);
                            //value = props.productQuantity;
                            displayNotEnoughQuantity()
                            dispatch(changeQuantityInCart(props.productId, props.productQuantity))

                        }
                        else if(caculatedValue<1){
                            dispatch(changeQuantityInCart(props.productId, 1))
                        }
                        else{
                            dispatch(changeQuantityInCart(props.productId, caculatedValue ))
                        }
                        
                        
                        }} className={classes["quantity-input"]}/>
                <PlusCircleOutlined onClick={handleIncreaseClick} className={`${classes["increase-decrease-btn"]} ${classesGlobal["noselect"]} ${incrementDecrementButtonDisabled.incrementDisabled && classes["increase-decrease-btn-disabled"]}`}/>
                <div className={classes["cart-entry-price-box"]}>
                    {props.productPreviousPriceForOneItem != null ? <div className={classes["price-before-rabat"]}>
                    {(props.productPreviousPriceForOneItem * cart[props.productId]).toFixed(2)} zł
                    </div>: <></>}
                    <div className={classes["price"]}>
                        {(props.productPriceForOneItem * cart[props.productId]).toFixed(2)} zł
                    </div>
                    {cart[props.productId]>1 ? <div className={classes["price-per-one-item"]}>
                        za sztukę {props.productPriceForOneItem} zł
                    </div>: <></>}
                </div>
                <CloseOutlined onClick={handleRemoveFormCartButton} className={`${classes["remove-from-cart-button"]} ${classesGlobal["noselect"]}`}/>
            </div>
        </div>
    )
}


