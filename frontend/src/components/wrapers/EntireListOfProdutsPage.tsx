import React, {FunctionComponent} from 'react'
import 'antd/dist/antd.css';
import classesGlobal from "../../app.module.css";
import {useLocation, useNavigate} from "react-router-dom";
import {Menu} from 'antd';
import {CategoryFilterColumnBar} from './CategoryFilterColumnBar';
import {ProductsList} from '../Products/ProductsList';
import {PathBar} from '../PathBar/PathBar';

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
        if(i< allEndpoints.length-1){
            url = url + allEndpoints[i]
            pathList.push({"name": allEndpoints[i], "url": url})
            url = url + "/"
        }
        else{
            pathList.push({"name": allEndpoints[i], "url": ''})
        }

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


