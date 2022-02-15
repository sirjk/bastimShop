import React, {FunctionComponent, useEffect, useState} from 'react'
import orders from "../../../actions/orders";
import {useParams} from "react-router";
import peculiarOrderClasses from "./orderDetails.module.css"
import {Divider} from "antd";

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

    const params = useParams();

    useEffect(()=>{
        orders.getPeculiarOrder(params.id)
            .then((response)=>{
                setOrder(response.data)
            })
            .catch((e)=>{
                console.log(e)
            })
    },[])

    return(
        <div className={peculiarOrderClasses.window}>
            <div className={peculiarOrderClasses["order-id-div"]}>
                Zamówienie numer {order.trackingNumber}
            </div>
            <div className={peculiarOrderClasses["product-list-in-order"]}>
                {Object.keys(order.productMap).map((productMapKeys:string)=> {
                    return (
                        <>
                            <div key={productMapKeys} className={peculiarOrderClasses["product-tile-info"]}>
                                <span className={peculiarOrderClasses["product-info"]}>
                                    {order.productMap[productMapKeys].product.name}
                                </span>
                                <span className={peculiarOrderClasses["product-info"]}>
                                    {order.productMap[productMapKeys].quantity}x{order.productMap[productMapKeys].product.price} zł
                                </span>
                            </div>
                        </>
                    )
                 }
                )}
                <div className={peculiarOrderClasses["total-cost"]}>
                    <span><Divider/>Łącznie: {order.totalCost} PLN</span>
                </div>
            </div>
        </div>
    )
}


