import React, {FunctionComponent} from 'react'
import classesGlobal from "../css/app.module.css"
import classes from "../css/categoryFilterColumnBar.module.css"
import { CategoriesMenu } from './CategoriesMenu'

interface Props{
    id: number
}


export const CategoryFilterColumnBar: FunctionComponent<Props>=(props: Props)=>{

    return(
        <div className={classes['category-and-filter-column-bar']}>
            <CategoriesMenu id={props.id}/>
        </div>
    )
}


