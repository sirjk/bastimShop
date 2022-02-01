import React, {FunctionComponent, useEffect, useState} from 'react'
import products from "../../actions/products";
import {ProductTile} from "./ProductTile";
import productClasses from "./product.module.css";
import appClasses from "../../app.module.css";
import {Divider, Input, Pagination} from "antd";
import {useSelector} from "react-redux";
import {RootStateDesiredPath} from "../../redux/reducers/pathReducers";
import {RootStateSearchPhrase} from "../../redux/reducers/searchReducers";


interface Props{
    id: number
}

interface ProductType{

    "id": number,
    "name": string,
    "categoryId": number,
    "price": number,
    "quantityInStock":number,
    "totalQuantitySold": number,
    "manufacturer": string,
    "description": string,
    "specification": string

}

export const ProductsList: FunctionComponent<Props>=(props: Props)=>{
    const [productsList, setProductsList] = useState<ProductType[]>([]);
    const [pageNumber, setPageNumber] = useState<number>(1);
    const [pageLimit, setPageLimit] = useState<number>(5);
    const [totalElements, setTotalElements] = useState<number>(0);
    const [searchPhrase, setSearchPhrase] = useState<string>("");


    const searchPhrase_ = useSelector((state: RootStateSearchPhrase)=>  state.searchPhrase);


    useEffect(()=>{
        products.getAll(props.id.toString(), pageLimit.toString(), (pageNumber-1).toString(), searchPhrase)
            .then((response)=>{
                setProductsList(response.data.content)
                setTotalElements(response.data.totalElements)
                }
            )
            .catch(e=>{

            })
    },[pageNumber, pageLimit, props,searchPhrase])


    useEffect(()=>{
        setSearchPhrase(searchPhrase_)
    }, [searchPhrase_])

    function onShowSizeChange(current: number, pageLimit: number) {
        setPageLimit(pageLimit)
    }

    function onPageChange(current: number, pageLimit: number){
        setPageNumber(current)
    }



    return(
        <div className={`${productClasses["products-window"]} ${appClasses.content}`}>
            {productsList.map(value => <ProductTile key={value.id} product={value}/>)}
            {productsList.length===0?<div>Brak wyszukiwań</div>:
                <Pagination
                showQuickJumper={true}
                onShowSizeChange={onShowSizeChange}
                defaultCurrent={1}
                total={totalElements}
                pageSizeOptions={[5,10,15,30,50]}
                defaultPageSize={5}
                onChange={onPageChange}
                responsive={true}
            />}


            <Divider/>
        </div>

    )
}


