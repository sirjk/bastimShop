import React, {useEffect, useState} from 'react';
import {Navbar} from "./components/Navbar/Navbar";
import classes from "./app.module.css";
import {Route, Routes, useNavigate} from "react-router-dom";
import {HomePage} from "./components/HomePage/HomePage"
import {LoginPage} from "./components/LoginPanel/LoginPanel";
import {RegisterPanel} from "./components/RegisterPanel/RegisterPanel";
import {ShoppingCart} from "./components/ShoppingCart/ShoppingCart";
import {Profile} from "./components/Profile/Profile";
import {UpdatePanel} from "./components/UpdatePanel/UpdatePanel";
import {OrderDetails} from "./components/Orders/OrderDetails/OrderDetails";
import {OrdersPanel} from "./components/Orders/OrdersPanel";
import {Contact} from "./components/Contact/Contact";
import {About} from "./components/About/About";
import {CategoriesBar} from "./components/CategoriesBar/CategoriesBar";
import {ControlMainPage} from "./components/Admin/ControlMainPage";
import {useDispatch} from "react-redux";
import {NavigationWrapper} from "./components/wrapers/NavigationWrapper";
import {setCategoriesPathMap} from "./redux/actions/pathActions"
import {EntireListOfProductsPage} from "./components/wrapers/EntireListOfProdutsPage"
import categories from './actions/categories';
import {ProductsDetails} from "./components/Products/ProductDetails/ProductDetails";
import {NotFound} from "./components/NotFound/NotFound";

interface CategoryInObject{
    id : number,
    name : string,
    parentId : number
}

function App() {

    const dispatch = useDispatch();

    const navigate = useNavigate();

    const [categoryRoutes, setCategoryRoutes] = useState(<></>);


    useEffect(()=>{

        categories.getAllFilteredRawParams("?limit=1000").then((response)=>{
            let mapOfCategoriesPaths : Map<number, string> = new Map;

            let categoriesInArray: CategoryInObject[] = response.data.content;
            //console.log(categoriesInArray)
            let arrayOfCategoryRoutes = categoriesInArray.map((element, index)=> {

                let properPath: string = "/" + element.name.replaceAll(" ", "-");
                let temp: CategoryInObject = element;
                for(let i: number = 0; i < 15; i ++ ){  //15 is hardcoded max lenght of path in one line
                    let foundParentFlag: boolean = false;
                    for(let j: number = 0; j < categoriesInArray.length; j ++ ){
                        if(categoriesInArray[j].id === temp.parentId){
                            foundParentFlag = true;
                            temp = categoriesInArray[j];
                            break;
                        }
                    }
                    if(!foundParentFlag){
                        break;
                    }
                    properPath = "/" + temp.name.replaceAll(" ", "-") + properPath;
                    properPath = properPath.toLowerCase();

                }

                //console.log(properPath)
             mapOfCategoriesPaths.set(element.id, properPath);
             return <Route path={properPath} key={element.id} element={<NavigationWrapper loginRequired="false"><EntireListOfProductsPage id={element.id}/></NavigationWrapper>}/> 
            });

            //console.log(arrayOfCategoryRoutes)

           // console.log(mapOfCategoriesPaths)

            let coerceMapToObject = Object.fromEntries(mapOfCategoriesPaths);
            dispatch(setCategoriesPathMap(coerceMapToObject));
            setCategoryRoutes (<>{arrayOfCategoryRoutes}</>);
        }).catch((e)=>{
  
        })
    },[])



    let DefaultRoutes = () => {
        return (
            <div className={classes.app}>
                <Navbar/>
                <CategoriesBar />
                <Routes>
                    <Route path="/" element={<NavigationWrapper loginRequired="false"><HomePage/></NavigationWrapper>}/>
                    <Route path="/cart" element={<NavigationWrapper loginRequired="false"><ShoppingCart/></NavigationWrapper>}/>
                    <Route path="/profile" element={<NavigationWrapper loginRequired="true"> <Profile/> </NavigationWrapper>}/>
                    <Route path="/profile/update" element={<NavigationWrapper loginRequired="true"><UpdatePanel/></NavigationWrapper>}/>
                    <Route path="/profile/orders" element={<NavigationWrapper loginRequired="true"><OrdersPanel/></NavigationWrapper>}/>
                    <Route path="/profile/orders/:id" element={<NavigationWrapper loginRequired="true"><OrderDetails/></NavigationWrapper>}/>
                    <Route path="/control" element={<NavigationWrapper loginRequired="true"><ControlMainPage/></NavigationWrapper>} />
                    <Route path="/contact" element={<NavigationWrapper loginRequired="false"><Contact/></NavigationWrapper>}/>
                    <Route path="/about" element={<NavigationWrapper loginRequired="false"><About/></NavigationWrapper>}/>
                    <Route path="/pr-:name-:id" element={<NavigationWrapper loginRequired="false"><ProductsDetails/></NavigationWrapper>}/>
                    {categoryRoutes}

                     <Route path="/*" element={<NavigationWrapper loginRequired="false"><NotFound/></NavigationWrapper>}/>
                </Routes>
            </div>
        );
    };

    return (
        <Routes>
            <Route path="/login" element={<LoginPage/>}/>
            <Route path="/register" element={<RegisterPanel/>}/>
            <Route path="*" element={DefaultRoutes()} />
        </Routes>
    );

}

export default App;
