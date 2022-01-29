import React, {FunctionComponent, useEffect, useState, useRef} from 'react'
import {Link, useNavigate} from "react-router-dom";
import { Menu } from 'antd';
import categories from '../actions/categories'
import {useDispatch, useSelector} from "react-redux";
import {RootStateCategoriesPath} from '../redux/reducers/pathReducers'
import classes from "../css/categoriesMenu.module.css";
import classesGlobal from "../css/app.module.css";
import { JsxElement } from 'typescript';

//import { useIsFocused } from '@react-navigation/native'

const { SubMenu } = Menu;

interface Props{
    id: number
}


interface CategoryInObject{
    id : number,
    name : string,
    parentId : number
}

type CategriesInArray = CategoryInObject[];

export const CategoriesMenu: FunctionComponent<Props>=(props: Props)=>{
    const navigate = useNavigate();
    const didMountRef = useRef(false);

    let handleClick = (e: any) => {
        console.log('click ', e);
        };

    //console.log(props)
    
    
    const [allCategories, setAllCategories] = useState<CategriesInArray>([])


    const [menu, setMenu] = useState(<Menu
        onClick={handleClick}
        style={{ width: 256 }}
        defaultSelectedKeys={['1']}
        defaultOpenKeys={['sub1']}
        mode="inline"
      >
    
        

    </Menu>);

    //const isFocused = useIsFocused()


    const [reRender, setReRender] = useState(0);
    

    useEffect(()=>{

        categories.getPeculiarCategory(""+props.id).then((r)=>{

          //  let allCategoriesInArray: CategoryInObject[] = r.data.content;
           // console.log(allCategoriesInArray);
            let initialCategory: CategoryInObject = r.data;
            //console.log(initialCategory);
            let allCategoriesInArray: CategoryInObject[] =[initialCategory] ;

            //console.log(allCategoriesInArray);
           // let entireArrayOfIds= [props.id];
            let param: string = "?limit=2000&parent-id=" + props.id
           // console.log(param);
            categories.getAllFilteredRawParams(param).then((response)=>{

                let categoriesInArray: CategoryInObject[] = response.data.content;
                let arrayOfNewIds = categoriesInArray.map((element, index)=> element.id);
               // entireArrayOfIds = entireArrayOfIds.concat(...arrayOfNewIds);

                allCategoriesInArray = allCategoriesInArray.concat(...categoriesInArray);
                //console.log(entireArrayOfIds);
               // console.log(allCategoriesInArray);
                if(arrayOfNewIds.length !== 0){
                    param = "?limit=2000&parent-id=" + arrayOfNewIds.join(",");
                    //console.log(param);
                    categories.getAllFilteredRawParams(param).then((response1)=>{
                        categoriesInArray = response1.data.content;
                        arrayOfNewIds = categoriesInArray.map((element, index)=> element.id);
                        //entireArrayOfIds = entireArrayOfIds.concat(...arrayOfNewIds);
                        allCategoriesInArray = allCategoriesInArray.concat(...categoriesInArray);
                        //console.log(allCategoriesInArray);
                        if(arrayOfNewIds.length !== 0){
                            param = "?limit=2000&parent-id=" + arrayOfNewIds.join(",");
                            //console.log(param);
                            categories.getAllFilteredRawParams(param).then((response2)=>{
                                categoriesInArray = response2.data.content;
                                arrayOfNewIds = categoriesInArray.map((element, index)=> element.id);
                               // entireArrayOfIds = entireArrayOfIds.concat(...arrayOfNewIds);
                                allCategoriesInArray = allCategoriesInArray.concat(...categoriesInArray);
                                //console.log(entireArrayOfIds);
                               // console.log(allCategoriesInArray);
                                setAllCategories(allCategoriesInArray);
                                
                
                            }).catch((e)=>{
                                console.log("Wrong");
                            })
                        }
                        else{
                            setAllCategories(allCategoriesInArray);
                        }


                    }).catch((e)=>{
                        console.log("Wrong");
                    })
                }
                else{
                    setAllCategories(allCategoriesInArray);
                }


        
            }).catch((e)=>{
                console.log("Wrong");
            })

        }).catch((e)=>{
            console.log("Wrong");
        })


    },[props])


    const requiredCategoriesPathMap = useSelector((state: RootStateCategoriesPath)=>  state.categoriesPath);
    let categoriesPathsLoaded : boolean = false;
    if(requiredCategoriesPathMap[1] != null){
        categoriesPathsLoaded = true;
    }

    useEffect(()=>{
        if(didMountRef.current === false)
                didMountRef.current = true;
        else{
            //console.log("IN!")
            let toBeSavedInMenu = (<></>);
            let initialCategory: CategoryInObject = allCategories[0];
            let initialCategoryHasChildreen : boolean = false;

            let catInGenerationT1Generation = [];

            for( let i : number = 0; i<allCategories.length; i++){

                let catInGenerationT2HasChildreen : boolean = false;

               

                if(allCategories[i].parentId === initialCategory.id){
                    initialCategoryHasChildreen = true

                    let catInGenerationT2Generation = [];
                    
                    for( let j : number = 0; j<allCategories.length; j++){
                        let catInGenerationT3HasChildreen : boolean = false;

                        if(allCategories[j].parentId === allCategories[i].id){
                            catInGenerationT2HasChildreen = true

                            let catInGenerationT3Generation = [];

                            for( let k : number = 0; k<allCategories.length; k++){
                                
        
                                if(allCategories[k].parentId === allCategories[j].id){
                                    catInGenerationT3HasChildreen = true
                                    if(categoriesPathsLoaded)
                                        catInGenerationT3Generation.push(<Menu.Item key={allCategories[k].id} onClick={()=>navigate(requiredCategoriesPathMap[allCategories[k].id])}>{allCategories[k].name}</Menu.Item>)
                                     else
                                        catInGenerationT3Generation.push(<Menu.Item key={allCategories[k].id} onClick={()=>navigate("/")}>{allCategories[k].name}</Menu.Item>)
                                }

                            }
                            
                            if(catInGenerationT3Generation.length === 0){
                                catInGenerationT2Generation.push(<Menu.Item key={allCategories[j].id} onClick={categoriesPathsLoaded?(()=>navigate(requiredCategoriesPathMap[allCategories[j].id])):(()=>navigate("/"))}>{allCategories[j].name}</Menu.Item>)
                            }
                            else{
                                catInGenerationT2Generation.push(<SubMenu key={allCategories[j].id} onTitleClick={categoriesPathsLoaded?(()=>navigate(requiredCategoriesPathMap[allCategories[j].id])):(()=>navigate("/"))} title={allCategories[j].name}>{catInGenerationT3Generation}</SubMenu>  )
                            }
                        }
                    }

                    if(catInGenerationT2Generation.length === 0){
                        catInGenerationT1Generation.push(<Menu.Item style={{marginLeft:"15px"}} key={allCategories[i].id} onClick={categoriesPathsLoaded?(()=>navigate(requiredCategoriesPathMap[allCategories[i].id])):(()=>navigate("/"))}>{allCategories[i].name}</Menu.Item>)
                    }
                    else{
                        catInGenerationT1Generation.push(<SubMenu style={{marginLeft:"15px"}} key={allCategories[i].id} onTitleClick={categoriesPathsLoaded?(()=>navigate(requiredCategoriesPathMap[allCategories[i].id])):(()=>navigate("/"))} title={allCategories[i].name}>{catInGenerationT2Generation}</SubMenu>  )
                    }

                }


            }
            //console.log("HERE!")
            if(catInGenerationT1Generation.length === 0){
                //console.log("HEREIN!")
                setMenu(<Menu
                    key={initialCategory.id} 
                   // onClick={categoriesPathsLoaded?(()=>navigate(requiredCategoriesPathMap[initialCategory.id])):(()=>navigate("/"))}
                    title={initialCategory.name}

                    
                    defaultSelectedKeys={['1']}
                    defaultOpenKeys={['sub1']}
                    mode="vertical"
                  ><Menu.ItemGroup title={initialCategory.name}/></Menu>)
            }
            else{
                setMenu(<Menu
                    key={initialCategory.id} 
                   // onClick={categoriesPathsLoaded?(()=>navigate(requiredCategoriesPathMap[initialCategory.id])):(()=>navigate("/"))}
                    title={initialCategory.name}

                    
                    defaultSelectedKeys={['1']}
                    defaultOpenKeys={['sub1']}
                    mode="vertical"
                  ><Menu.ItemGroup title={initialCategory.name.toUpperCase()}/>{catInGenerationT1Generation}</Menu>  )
            }
        }
  
        }, [allCategories])


    return(
        <>{menu}</>
    )
}


