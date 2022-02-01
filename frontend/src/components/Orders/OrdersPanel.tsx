import React, {FunctionComponent} from 'react'
import orderClasses from "./order.module.css";
import Search from "antd/es/input/Search";
import {Divider, Select} from "antd";
import classes from "../Navbar/navbar.module.css";
import {Link} from "react-router-dom";
import {useParams} from "react-router-dom";

interface Props{
}

const { Option } = Select;

export const OrdersPanel: FunctionComponent<Props>=(props: Props)=>{

    interface OrderType {
        "id": number,
        "productMap": {
            "10": {
                "id": number,
                "product": {
                    "id": number,
                    "name": string,
                    "categoryId": number,
                    "price": number,
                    "quantityInStock": number,
                    "totalQuantitySold": number,
                    "manufacturer": string,
                    "description": string,
                    "specification": string
                },
                "quantity": number
            }
        },
        "userId": number,
        "totalCost": number,
        "buyDate": string,
        "shipmentDate": string,
        "shipmentState": string,
        "orderState": string
    }

    const orders: OrderType[] = [{
        "id":1,
        "productMap": {
            "10": {
                "id": 1,
                "product": {
                    "id": 10,
                    "name": "Śrubka rx76 paczka 100",
                    "categoryId": 1,
                    "price": 1.99,
                    "quantityInStock": 993,
                    "totalQuantitySold": 16321,
                    "manufacturer": "RPA",
                    "description": "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!",
                    "specification": "{ \"Produkt\": {\"Producent\": \"RPA\"}"
                },
                "quantity": 200
            }
        },
        "userId": 1,
        "totalCost": 200.0,
        "buyDate": "1999-01-01",
        "shipmentDate": "2021-01-01",
        "shipmentState": "complete",
        "orderState": "complete"
    },
    {
        "id":2,
        "productMap": {
            "10": {
                "id": 1,
                "product": {
                    "id": 10,
                    "name": "Nakrętka rx71 paczka 220",
                    "categoryId": 1,
                    "price": 3.99,
                    "quantityInStock": 993,
                    "totalQuantitySold": 16321,
                    "manufacturer": "RPA",
                    "description": "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!",
                    "specification": "{ \"Produkt\": {\"Producent\": \"RPA\"}"
                },
                "quantity": 21
            }},
        "userId": 1,
        "totalCost": 200.0,
        "buyDate": "1999-01-01",
        "shipmentDate": "2021-01-01",
        "shipmentState": "completed",
        "orderState": "completed"
    },
        {
            "id":3,
            "productMap": {
                "10": {
                    "id": 1,
                    "product": {
                        "id": 10,
                        "name": "Jakieś coś rx71 paczka 220",
                        "categoryId": 1,
                        "price": 3.99,
                        "quantityInStock": 993,
                        "totalQuantitySold": 16321,
                        "manufacturer": "RPA",
                        "description": "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!",
                        "specification": "{ \"Produkt\": {\"Producent\": \"RPA\"}"
                    },
                    "quantity": 21
                }},
            "userId": 1,
            "totalCost": 100.0,
            "buyDate": "2000-01-01",
            "shipmentDate": "2021-01-01",
            "shipmentState": "completed",
            "orderState": "canceled"
        },
        {
            "id":4,
            "productMap": {
                "10": {
                    "id": 1,
                    "product": {
                        "id": 10,
                        "name": "Jakieś coś rx71 paczka 220",
                        "categoryId": 1,
                        "price": 3.99,
                        "quantityInStock": 993,
                        "totalQuantitySold": 16321,
                        "manufacturer": "RPA",
                        "description": "Super wspaniała Śrubka firmy RPA odmieni Twoje życie na lepsze!",
                        "specification": "{ \"Produkt\": {\"Producent\": \"RPA\"}"
                    },
                    "quantity": 21
                }},
            "userId": 1,
            "totalCost": 100.0,
            "buyDate": "2000-01-01",
            "shipmentDate": "2021-01-01",
            "shipmentState": "completed",
            "orderState": "canceled"
        },
    ]


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

            {orders.map((data): any =>{
                return(
                    <div className={orderClasses["order-div"]}>
                        <div className={orderClasses["order-row-div"]}>
                            <span>DATA KUPNA: {data.buyDate}</span>
                            <span>STATUS ZAMÓWIENIA: {data.orderState}</span>
                        </div>
                        <Divider />
                        <div className={orderClasses["order-row-div"]}>
                            <span>{data.productMap["10"].product.name}</span>
                            <span>{data.productMap["10"].quantity} x {data.productMap["10"].product.price} PLN</span>
                            <span>CAŁKOWITY KOSZT: {data.totalCost} PLN</span>
                        </div>
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


