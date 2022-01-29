import React, {FunctionComponent, useEffect, useState} from 'react'
import 'antd/dist/antd.css';
import classes from "../css/categorybar.module.css";
import classesGlobal from "../css/app.module.css";
import {PhoneOutlined, DownOutlined, QuestionCircleOutlined, UsergroupAddOutlined, UserOutlined} from "@ant-design/icons";
import {Link, useNavigate} from "react-router-dom";
import { Menu, Dropdown, Button, Space} from 'antd';
import categories from '../actions/categories'
import {useDispatch, useSelector} from "react-redux";
import {RootStateCategoriesPath} from '../redux/reducers/pathReducers'

interface Props{
}

const  SubMenu  = Menu.SubMenu;

interface CategoryInObject{
    id : number,
    name : string,
    parentId : number
}


export const CategoriesBar: FunctionComponent<Props>=(props: Props)=>{

    const navigate = useNavigate();
    const [catInArray, setCatInArray] = useState<CategoryInObject[]>([]);

    const requiredCategoriesPathMap = useSelector((state: RootStateCategoriesPath)=>  state.categoriesPath);
    let categoriesPathsLoaded : boolean = false;
    if(requiredCategoriesPathMap[1] != null){
        categoriesPathsLoaded = true;
    }

    useEffect(()=>{
        categories.getAllFilteredRawParams("?parent-id=1").then((response)=>{

            let categoriesInArray: CategoryInObject[] = response.data.content;
            //let arrayOfMenuItems = categoriesInArray.map((element, index)=> <Menu.Item  key={element.id} onClick={categoriesPathsLoaded?(()=>navigate(requiredCategoriesPathMap[element.id])):(()=>navigate("/"))}>{element.name}</Menu.Item>);
            setCatInArray(categoriesInArray);
        }).catch((e)=>{
  
        })
    },[])



    return(
        <div className={classes["categories-bar"]}>
              <Dropdown overlay={<Menu>{catInArray.map((element, index)=> <Menu.Item  key={element.id} onClick={categoriesPathsLoaded?(()=>navigate(requiredCategoriesPathMap[element.id])):(()=>navigate("/"))}>{element.name}</Menu.Item>)}</Menu>}>
                <div onClick={categoriesPathsLoaded?(()=>navigate(requiredCategoriesPathMap[1])):(()=>navigate("/"))} className={[classes["ant-dropdown-link"], classesGlobal["noselect"]].join(" ")}>
                    Kategorie <DownOutlined />
                </div>
            </Dropdown>
        </div>
            );

    
}


