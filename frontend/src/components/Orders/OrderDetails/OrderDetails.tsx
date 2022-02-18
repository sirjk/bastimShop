import React, {FunctionComponent, MouseEventHandler, useEffect, useRef, useState} from 'react'
import orders from "../../../actions/orders";
import user from "../../../actions/user";
import {useParams} from "react-router";
import peculiarOrderClasses from "./orderDetails.module.css"
import {Divider} from "antd";
import {Link, useNavigate} from "react-router-dom";
import textToURL from "../../utility/textToURL";
import {PathBar} from "../../PathBar/PathBar";
import classesGlobal from "../../../app.module.css";
import {useSelector} from "react-redux";
import {RootStateUserId} from "../../../redux/reducers/userReducers";
import products from "../../../actions/products";

interface Props{
}

interface OrderType {
    "id": number,
    "productMap": any,
    "userId": number,
    "totalCost": number,
    "buyDate": string,
    "shipmentDate": string,
    "shipmentState": string,
    "orderState": string
    "trackingNumber": number
}

interface UserType {
    id: number
    firstName: string;
    lastName: string;
    email: string;
    birthDate: string;
    points: number;
    password: string;
    country: string;
    city: string;
    address: string;
    postalAddress: string;
    roles: object;
}

export const OrderDetails: FunctionComponent<Props>=(props: Props)=>{
    const[order, setOrder] = useState<OrderType>({
        buyDate: "",
        id: 0,
        orderState: "",
        productMap: {},
        shipmentDate: "",
        shipmentState: "",
        totalCost: 0,
        userId: 0,
        trackingNumber: 0
    })
    const[buyer, setbuyer] = useState<UserType>({
        address: "",
        birthDate: "",
        city: "",
        country: "",
        email: "",
        firstName: "",
        id: 0,
        lastName: "",
        password: "",
        points: 0,
        postalAddress: "",
        roles: {}
    });

    interface linkList{
        name: string,
        url: string
    }

    const params = useParams();

    const isMounted = useRef(false);

    useEffect(()=>{
        orders.getPeculiarOrder(params.id)
            .then((response)=>{
                setOrder(response.data)
            })
            .catch((e)=>{
                console.log(e)
            })
    },[])

    useEffect(()=>{
        if(!isMounted.current){
            isMounted.current=true;
        }
        else{
            user.getById(order.userId)
                .then((response)=>{
                    setbuyer(response.data)
                })
                .catch((e)=>{
                    console.log(e.response.data)
                })
        }
    },[order])

    function calculateCostForProduct(quantity:string, price:string):number{
        let calculatedPrice:number;
        calculatedPrice = Number(quantity) * Number(price);
        return calculatedPrice;
    }

    const navigate = useNavigate();

    function onProductNameClick(product:any){
        let nameInUrlFormat:string = textToURL.textToURL(`${product.name}`);
        navigate(`/pr-${nameInUrlFormat}-${product.id}`);
    }

    const userId = useSelector((state:RootStateUserId) => state.userId)

    const pathList : linkList[] = [{"name": "Bastim", "url": "/"}, {"name": "Profil", "url": `/profile`}, {"name": "Zamówienia", "url": `/profile/orders`}, {"name": "Szczegóły zamówienia", "url": ``} ];

    return(
        <div className={classesGlobal.page}>
            <PathBar pathList={pathList}/>
            <div className={peculiarOrderClasses.window}>
                <div className={peculiarOrderClasses["left-column"]}>
                    <div className={peculiarOrderClasses["order-id-div"]}>
                        Zamówienie numer {order.trackingNumber||<>(error)</>}
                    </div>
                    <div className={peculiarOrderClasses["product-list-in-order"]}>
                        {Object.keys(order.productMap).map((productMapKeys:string)=> {
                                return (
                                    <>
                                        <div key={productMapKeys} className={peculiarOrderClasses["product-tile-info"]}>
                                            <span onClick={()=>onProductNameClick(order.productMap[productMapKeys].product)} className={peculiarOrderClasses["product-info"]}>
                                                 {order.productMap[productMapKeys].product.name}
                                            </span>
                                            <div className={peculiarOrderClasses["product-quantity-x-price"]}>
                                                {order.productMap[productMapKeys].quantity}x{order.productMap[productMapKeys].product.price} zł
                                                <span className={peculiarOrderClasses["product-price"]}>
                                                    {calculateCostForProduct(order.productMap[productMapKeys].quantity,order.productMap[productMapKeys].product.price)} zł
                                                </span>
                                            </div>

                                        </div>
                                    </>
                                )
                            }
                        )}
                        <div className={peculiarOrderClasses["total-cost"]}>
                            <span className={peculiarOrderClasses["total-cost-span"]}><Divider/>Łącznie: {order.totalCost} PLN</span>
                        </div>
                        <div className={peculiarOrderClasses["order-info"]}>
                            <div>
                                Status zamówienia: <span className={peculiarOrderClasses["order-info-span"]}>{order.orderState}</span>
                            </div>
                            <div>
                                Status przesyłki: <span className={peculiarOrderClasses["order-info-span"]}>{order.shipmentState}</span>
                            </div>
                            <div>
                                Data złożenia zamówienia: <span className={peculiarOrderClasses["order-info-span"]}>{order.buyDate}</span>
                            </div>
                            <div>
                                Data dostawy: <span className={peculiarOrderClasses["order-info-span"]}>{order.shipmentDate || <>-</> }</span>
                            </div>
                            <div>
                                <Link to={""}>Śledź przesyłkę</Link>
                            </div>
                        </div>
                    </div>
                </div>

                <div className={peculiarOrderClasses["right-column"]}>
                    <div className={peculiarOrderClasses["data-to-invoice-div"]}>
                        <span className={peculiarOrderClasses["data-to-invoice-title"]}>Dane do faktury</span>
                        <span className={peculiarOrderClasses["data-to-invoice"]}>{buyer.firstName} {buyer.lastName}</span>
                        <span className={peculiarOrderClasses["data-to-invoice"]}>{buyer.address}</span>
                        <span className={peculiarOrderClasses["data-to-invoice"]}>{buyer.postalAddress} {buyer.city}</span>
                        <span className={peculiarOrderClasses["data-to-invoice"]}>{buyer.email}</span>
                    </div>

                </div>

            </div>
        </div>

    )
}


