import React, {FunctionComponent} from 'react'
import {PathBar} from '../PathBar/PathBar';
import classesGlobal from "../../app.module.css";
import classes from "./shoppingCart.module.css";
import {useSelector} from "react-redux";
import {RootStateCart} from "../../redux/reducers/cartReducers";

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

    return(
        <div className={classesGlobal.page}>
            <PathBar pathList={pathList}/>
            <div style={{display:"flex"}}>
                <div className={classes["entry-table-box"]}>
                    <div className={classes["entry-table"]}>
                        
                    </div>
                </div>
                <div className={classes["summary-box"]}>

                </div>
            </div>
        </div>
    )
}


