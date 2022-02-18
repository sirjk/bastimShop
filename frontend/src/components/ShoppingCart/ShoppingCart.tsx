import React, {FunctionComponent, useEffect} from 'react'
import {PathBar} from '../PathBar/PathBar';
import classesGlobal from "../../app.module.css";
import classes from "./shoppingCart.module.css";
import {useDispatch, useSelector} from "react-redux";
import {RootStateCart} from "../../redux/reducers/cartReducers";
import { CartEntry } from './CartEntry/CartEntry';
import {addToCart, deleteFromCart, changeQuantityInCart} from "../../redux/actions/cartActions";

interface Props{
}

interface linkList{
    name: string,
    url: string
}

export const ShoppingCart: FunctionComponent<Props>=(props: Props)=>{

    let products : Object = useSelector((state: RootStateCart)=>  state.cart);
    let cartQuantityName : string = "Koszyk("+ Object.keys(products).length +")";

    const pathList : linkList[] = [{"name": "Bastim", "url": "/"}, {"name": cartQuantityName, "url": "/cart"} ];
    let dispatch = useDispatch();

    useEffect(()=>{
        //useDispatch(addToCart( {"3":2} ))
    }, [])

    return(
        <div className={classesGlobal.page}>
            <PathBar pathList={pathList}/>
            <div className={classes["cart-flex-box"]}>
                <div className={classes["entry-table-box"]}>
                    <div className={classes["entry-table-box-header"]}>
                        <div>Twój koszyk?</div><div>blaaa</div>
                    </div>
                    <div className={classes["entry-table"]}>
                        <CartEntry productId="3" productPriceForOneItem={299.99} productPreviousPriceForOneItem={350.20} productQuantity={10}/>
                    </div>
                </div>
                <div className={classes["summary-box"]}>

                </div>
            </div>
        </div>
    )
}


