import React, {FunctionComponent, useEffect, useRef, useState} from 'react'
import {useParams} from "react-router";
import products from "../../../actions/products";
import productDetailsClass from "./productDetails.module.css"
import appClasses from "../../../app.module.css";
import textToURL from "../../utility/textToURL";
import {useLocation, useNavigate} from "react-router-dom";
import {Button, Divider, Input, InputNumber} from "antd";
import {CheckCircleOutlined, ExclamationCircleOutlined, MinusOutlined, PlusCircleOutlined} from "@ant-design/icons";
import {PathBar} from "../../PathBar/PathBar";

interface Props{
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


export const ProductsDetails: FunctionComponent<Props>=(props: Props)=>{
    const [product, setProduct] = useState<ProductType>({
        categoryId: 0,
        description: "",
        id: 0,
        manufacturer: "",
        name: "",
        price: 0,
        quantityInStock: 0,
        specification: "",
        totalQuantitySold: 0
    });
    const [flag, setFlag] = useState<boolean>(true);
    const [price, setPrice] = useState<number>(0);
    const [quantityToBuy, setQuantityToBuy] = useState<number>(1);

    const params = useParams();
    const firstIteration = useRef(true);


    useEffect(()=>{
        products.getProductById(params.id)
            .then((response)=> {
                setProduct(response.data)

            }
            )
            .catch(e=>{
                setFlag(false);
            })


    },[])

    const navigate = useNavigate();



    useEffect(()=>{
        if(firstIteration.current){
            firstIteration.current=false;
        }
        else{
            let nameInUrlFormat:string = textToURL.textToURL(`${product.name}`);
            if(params.name?.localeCompare(nameInUrlFormat) == 0){
            }else{
                navigate(`/pr-${nameInUrlFormat}-${product.id}`);
            }
        }
        setPrice(product.price)

    },[product])



    function handleIncreaseClick(){
        setQuantityToBuy(quantityToBuy=>quantityToBuy+1)
    }

    function handleDecreaseClick(){
        if(quantityToBuy>1){
            setQuantityToBuy(quantityToBuy=>quantityToBuy-1)
        }
    }




    return(
        <>
        {
            flag?
            <div className={`${productDetailsClass["product-details-window"]} ${appClasses.content}`}>
                <div className={productDetailsClass["product-details"]}>
                    <div className={productDetailsClass["product-img-div"]}>
                        <img
                            src={"https://laptopshop.pl/pol_pl_Sruba-mocujaca-wentylator-czarna-M4x10-srubka-2215_2.png"}/>
                    </div>
                    <div className={productDetailsClass["product-description-div"]}>
                        <span className={productDetailsClass["product-name"]}>{product.name}</span>
                        <Divider/>
                        <div className={productDetailsClass["product-description-div-inner"]}>
                            <div className={productDetailsClass["product-description-div-inner-left"]}>
                                <span className={productDetailsClass["product-description-div-inner-span"]}>
                                    Opis produktu: <br/>
                                    <span className={productDetailsClass["product-description-div-inner-span-data"]}>
                                        {product.description}
                                    </span>
                                </span>
                                <span className={productDetailsClass["product-description-div-inner-span"]}>
                                     Producent: <br/>
                                    <span className={productDetailsClass["product-description-div-inner-span-data"]}>
                                        {product.manufacturer}
                                    </span>
                                </span>
                            </div>

                            <div className={productDetailsClass["product-description-div-inner-right"]}>
                                <div className={productDetailsClass.price}>
                                    {price} zł
                                </div>
                                <div style={{display:"flex", flexDirection:"row", alignItems:"center"}}>
                                    <span style={{fontSize:"20px"}}>Ilość:</span>
                                    <MinusOutlined onClick={handleDecreaseClick} className={`${productDetailsClass["increase-decrease-btn"]} ${appClasses["noselect"]}`}/>
                                    <InputNumber controls={false} min={1} defaultValue={1} value={quantityToBuy} onChange={value => setQuantityToBuy(value)} className={productDetailsClass["quantity-input"]}/>
                                    <PlusCircleOutlined onClick={handleIncreaseClick} className={`${productDetailsClass["increase-decrease-btn"]} ${appClasses["noselect"]}`}/>
                                    {
                                        product.quantityInStock > 0 ?
                                            <Button className={productDetailsClass["add-to-cart-btn"]} type="primary">Dodaj
                                                do koszyka</Button> :
                                            <Button disabled={true} className={productDetailsClass["add-to-cart-btn"]} type="primary">Dodaj
                                                do koszyka</Button>
                                    }
                                </div>
                                <span className={productDetailsClass["product-description-div-inner-span"]}>
                                    {
                                        product.quantityInStock>0?
                                            <><CheckCircleOutlined  style={{color: "green", marginRight: "15px"}}/><span className={appClasses.noselect}>Produkt dostępny</span></>:
                                            <><ExclamationCircleOutlined style={{color: "red", marginRight: "15px"}}/><span className={appClasses.noselect} >Produkt nie dostępny</span></>
                                    }
                                </span>
                            </div>
                        </div>
                    </div>
                </div>

                <div className={productDetailsClass["product-specification"]}>

                </div>

            </div> : <div className={`${appClasses.content}`} style={{fontSize:"80px"}}>Nie znaleziono produktu</div>
                }
        </>
    )
}


