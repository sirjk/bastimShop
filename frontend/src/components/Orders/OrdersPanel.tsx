import React, {CSSProperties, FunctionComponent, useEffect, useState} from 'react'
import orderClasses from "./order.module.css";
import Search from "antd/es/input/Search";
import {Divider, Select} from "antd";
import {Link} from "react-router-dom";
import orders from "../../actions/orders";

interface Props{
}


const { Option } = Select;

function colorStatus(status:string):CSSProperties{
    if(status === "accepted" || status ==="done"){
        return {color:"#32CD32"}
    }
    else if(status ==="cancelled"){
        return {color:"red"}
    }
    else{
        return {color:"orange"}
    }
}

export const OrdersPanel: FunctionComponent<Props>=(props: Props)=>{
    const[orderList, setOrderList] = useState<OrderType[]>([]);



    useEffect(()=>{
        orders.getAll()
            .then((response)=>{
                setOrderList(response.data.content)
                }
            )
            .catch(e=>{

            })
    },[])


    interface OrderType {
        "id": number,
        "productMap": any,
        "userId": number,
        "totalCost": number,
        "buyDate": string,
        "shipmentDate": string,
        "shipmentState": string,
        "orderState": string
    }




    return(
        <div className={orderClasses["order-window"]}>
            <div className={orderClasses["search-filter-sort-div"]}>
                <Search
                    className={orderClasses["search"]}
                    size={"large"}
                    placeholder="Szukaj w zamówieniach"
                    allowClear
                />
                <>
                    <span>SORTUJ:</span>
                    <Select className={orderClasses["sort"]} defaultValue="newest" >
                        <Option value="newest">Od najnowszych</Option>
                        <Option value="oldest">Od najstarszych</Option>
                    </Select>
                </>
            </div>

            {orderList.map((data): any =>{
                return(
                    <div className={orderClasses["order-div"]}  key={data.id}>
                        <div className={orderClasses["order-row-div"]}>
                            <span>DATA KUPNA: {data.buyDate}</span>
                            <span>STATUS ZAMÓWIENIA: <span style={colorStatus(data.orderState)}>{data.orderState}</span></span>
                        </div>
                        <Divider />
                        {Object.keys(data.productMap).map((productMapKeys:string)=>{
                            return(
                                <div className={orderClasses["order-row-div"]}  key={productMapKeys}>
                                    <span>{data.productMap[productMapKeys].product.name}</span>
                                    <span>{data.productMap[productMapKeys].quantity} x {data.productMap[productMapKeys].product.price} PLN</span>
                                    <span>CAŁKOWITY KOSZT: {data.totalCost} PLN</span>
                                </div>
                                )
                            }
                        )}




                        <span className={orderClasses["order-details"]}>
                            <Link to={"/profile/orders/1"}> {/*tutaj /profile/orders/id -> id bedzie pobierane z api -> useParams()*/}
                                Szczegóły zamówienia
                            </Link>
                        </span>
                    </div>
                );
            })}

        </div>
    )
}


