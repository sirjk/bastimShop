import React, {FunctionComponent, useEffect, useState} from 'react'
import productClasses from "../css/product.module.css"

import {Button, Divider} from "antd";
import {ProductsDetails} from "./ProductDetails";
import {useNavigate} from "react-router-dom";
import textToURL from "./utility/textToURL";


interface Props{
    product: ProductType
}

interface ProductType{

    "id": number,
    "name": string,
    "categoryId": number,
    "price": number,
    "quantityInStock":number,
    "totalQuantitySold": number,
    "manufacturer": string,
    "description": string,
    "specification": string

}

export const ProductTile: FunctionComponent<Props>=(props: Props)=>{
    //const [productsList, setProductsList] = useState<ProductType[]>([]);

    const navigate = useNavigate();



    return(
        <>
            <div className={productClasses["product-tile"]} onClick={()=>{
                let url:string = textToURL.textToURL(`/pr-${props.product.name}`);
                navigate(url+`-${props.product.id}`);

            }}>
                <div style={{height: "100%", width:"350px", minWidth:"160px"}}>
                    <img style={{height: "100%"}} src={"https://laptopshop.pl/pol_pl_Sruba-mocujaca-wentylator-czarna-M4x10-srubka-2215_2.png"}/>
                </div>
                <div className={productClasses["product-info-div-left"]}>
                    <div>
                        {props.product.name}
                    </div>
                    <div className={productClasses["total-sold-div"]}>
                        {props.product.totalQuantitySold <5? <>
                            {props.product.totalQuantitySold ===1?<>
                                Kupiła {props.product.totalQuantitySold} osoba</>
                                :<>Kupiły {props.product.totalQuantitySold} osoby</>
                            }
                        </>:<>Kupiło {props.product.totalQuantitySold} osób</>}
                    </div>
                    <div className={productClasses["description-div"]}>
                        {props.product.description}
                    </div>
                </div>
                <div className={productClasses["product-info-div-right"]}>
                    <div className={productClasses["price-div"]}>
                        {props.product.price} zł
                    </div>
                    <Button className={productClasses["add-to-cart-btn"]} type="primary">Dodaj do koszyka</Button>
                </div>

            </div>
        </>
    )
}


