import React, {ChangeEvent, FunctionComponent, useEffect, useState} from 'react'
import {Dropdown, Input, Menu, notification, Select} from 'antd'
import 'antd/dist/antd.css';
import classes from "./navbar.module.css";
import {
    PhoneOutlined,
    QuestionCircleOutlined, ShoppingCartOutlined,
    UsergroupAddOutlined,
    UserOutlined
} from "@ant-design/icons";
import {Link, useNavigate} from "react-router-dom";
import classesGlobal from "../../app.module.css";
import {useDispatch, useSelector} from "react-redux";
import {RootStateIsLogged} from "../../redux/reducers/loginReducers"
import {setIsLogged} from "../../redux/actions/loginActions"
import auth from '../../actions/auth'
import categories from '../../actions/categories'
import {searchPhrase} from "../../redux/actions/searchActions";
import {RootStateSearchPhrase} from "../../redux/reducers/searchReducers";
import {EntireListOfProductsPage} from "../wrapers/EntireListOfProdutsPage";
import {Navigate} from "react-router-dom";
import {RootStateDesiredPath} from "../../redux/reducers/pathReducers";

interface Props{
}
const { Option } = Select;
const { Search } = Input;

interface CategoryInObject{
    id : number,
    name : string,
    parentId : number
}


export const Navbar: FunctionComponent<Props>=(props: Props)=>{
const isSomebodyLogged = useSelector((state:RootStateIsLogged) => state.isLogged);
const [logoutErrorMsg, setLogoutErrorMsg] = useState('');
const [searchPhrase_, setSearchPhrase_] = useState('');


    const navigate = useNavigate();
    const dispatch = useDispatch();


    // const [selectBefore, setSelectBefore] = useState(
    // <Select className={classes.category} defaultValue="Wszystko" >
    //     <Option value="category1">Kategoria 1</Option>
    //     <Option value="category2">Kategoria 2</Option>
    //     <Option value="category3">Kategoria 3</Option>
    //     <Option value="Wszystko">Wszystko</Option>
    // </Select>);


    useEffect(()=>{

        categories.getAllFilteredRawParams("?parent-id=1").then((response)=>{

            let categoriesInArray: CategoryInObject[] = response.data.content;
            let arrayOfMenuItems = categoriesInArray.map((element, index)=> <Option  key={element.id} value={element.name}>{element.name}</Option>);
            // setSelectBefore (<Select className={classes.category} defaultValue="Wszystko" ><Option key={1} value="Wszystko">Wszystko</Option>{arrayOfMenuItems}</Select>);
        }).catch((e)=>{
  
        })
    },[])




    function openNotification(){
        notification.error({
            message: `Error`,
            description: logoutErrorMsg,
            placement: "bottomRight",
        });
    };

    useEffect(()=>{
        if (logoutErrorMsg!=='')
            openNotification();

    },[logoutErrorMsg])


    function handleSearch(value:any, event :any){
        dispatch(searchPhrase(value))
        setSearchPhrase_(value);
    }

    function handleOnchangeSearch(event:any){
        if (event.target.value==='') {
            dispatch(searchPhrase(event.target.value))
            setSearchPhrase_(event.target.value)
        }

    }

    const path = useSelector((state:RootStateDesiredPath) => state.desiredPath);



    const menu = (
        <Menu >

            <Menu.Item  key="1" onClick={() => {navigate("/profile/")}}>Profil</Menu.Item>
            <Menu.Item  key="2" onClick={() => {navigate("/profile/orders/")}}>Zamówienia</Menu.Item>
            <Menu.Item key="3" onClick={() => {
                   /* console.log("pressed")
                    let retry: boolean = true;
                    while(retry){
                        retry = false;*/
                        auth.logout().then(
                            
                            (response1)=>{
                               // console.log(response1.headers)
                                dispatch(setIsLogged("false"));
                                navigate("/");
                            }
                        ).catch((e) => {
                           /* console.log(e.response.headers)
                            if(e.response.headers('retry') === 'true'){
                                console.log("header found")
                                retry = true;
                            }*/
                            setLogoutErrorMsg("Nie udało się wylogować");
                        });
                    //}
                }}>Wyloguj
            </Menu.Item>

        </Menu>
    );

    return(
        <div className={classes.navbar}>

            <Link to={"/"}>
                <span onClick={()=>setSearchPhrase_('')} className={classes.logo}>BASTIM</span>
            </Link>
            <div className={classes["logo-search-div"]}>
                <Search
                    className={classes.search}
                    size={"large"}
                    placeholder="Szukaj w ofercie"
                    allowClear
                    onSearch={handleSearch}
                    onChange={handleOnchangeSearch}
                    // addonBefore={selectBefore}
                />
            </div>
            {isSomebodyLogged=="true" ?
                (<>
                    <Dropdown overlay={menu}>
                        <div>
                            <Link onClick={()=>setSearchPhrase_('')} to={"/profile"}>
                                <UserOutlined  className={classes.icons}/>
                                <span className={classes["icons-text"]}>Moje Konto</span>
                            </Link>
                        </div>
                    </Dropdown>
                   
                </>):
                (<>
                    <div>
                        <Link to={"/login"}>
                            <UserOutlined className={classes.icons}/>
                            <span className={classes["icons-text"]}>Zaloguj</span>
                        </Link>
                    </div>
                    <div>
                        <Link to={"/register"}>
                            <UsergroupAddOutlined className={classes.icons}/>
                            <span className={classes["icons-text"]}>Rejestracja</span>
                        </Link>
                    </div>
                </>)}

            {/*<div>*/}
            {/*    /!*<Link to={"/contact"}>*!/*/}
            {/*    /!*    <PhoneOutlined className={classes.icons}/>*!/*/}
            {/*    /!*    <span className={classes["icons-text"]}>Kontakt</span>*!/*/}
            {/*    /!*</Link>*!/*/}
            {/*</div>*/}
            <div>
                <Link onClick={()=>setSearchPhrase_('')} to={"/about"}>
                    <QuestionCircleOutlined className={classes.icons}/>
                    <span className={classes["icons-text"]}>Info</span>
                </Link>

            </div>
            <div>
                        <Link onClick={()=>setSearchPhrase_('')} to={"/cart"}>
                            <ShoppingCartOutlined  className={classes.icons}/>
                            <span className={classes["icons-text"]}>Koszyk</span>
                        </Link>
             </div>

            {searchPhrase_!='' && (path ==='/' || path ==='/about' || path ==='/cart' || path ==='/profile')?<Navigate to={"/wszystko"}/>:<></>}
        </div>
    )
}


