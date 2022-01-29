import React, {FunctionComponent, useEffect, useState} from 'react'
import 'antd/dist/antd.css';
import classes from "../css/categorybar.module.css";
import classesGlobal from "../css/app.module.css";
import {PhoneOutlined, DownOutlined, QuestionCircleOutlined, UsergroupAddOutlined, UserOutlined} from "@ant-design/icons";
import {Link, useNavigate, useLocation} from "react-router-dom";
import { Menu, Dropdown, Button, Space} from 'antd';
import categories from '../actions/categories'
import { CategoryFilterColumnBar } from './CategoryFilterColumnBar';
import { ProductsList } from './ProductsList';
import { PathBar } from './PathBar';

interface Props{
    id: number
}

const  SubMenu  = Menu.SubMenu;

interface CategoryInObject{
    id : number,
    name : string,
    parentId : number
}

interface linkList{
    name: string,
    url: string
}


export const EntireListOfProductsPage: FunctionComponent<Props>=(props: Props)=>{

    const location = useLocation();
    
    const navigate = useNavigate();
    const pathList : linkList[] = [{"name": "Bastim", "url": "/"} ];
    let allEndpoints: string[] = location.pathname.split("/");
    allEndpoints.shift();
    let url: string = "/";
    for(let i: number = 0; i< allEndpoints.length; i++){
        url = url + allEndpoints[i] + "/"
        pathList.push({"name": allEndpoints[i], "url": url})
    }

    return(
        <div className={classesGlobal.page}>
        <PathBar pathList={pathList}/>
        <div style={{display:"flex"}}>
            
            <CategoryFilterColumnBar id={props.id}/>
            <ProductsList id={props.id}/>
        </div>
        </div>)
    
}


