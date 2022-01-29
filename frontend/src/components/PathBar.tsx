import React, {FunctionComponent} from 'react'
import classes from "../css/pathbar.module.css";
import {Link} from 'react-router-dom';

interface Props{
    pathList: linkList[];
}

interface linkList{
    name: string,
    url: string
}

export const PathBar: FunctionComponent<Props>=(props: Props)=>{
    const pathList: linkList[]= props.pathList;

    const path = pathList.map( (element) => 
    <div key={element.url} className={classes["path-bar-one-element-div"]}>
        <Link to={element.url} className={classes["path-bar-one-element-link"]}>
            <div className={classes["subtitle-in-the-link"]}>{element.name}</div>
        </Link> 
        {element.url!==pathList[pathList.length -1].url ? (<span className={classes["path-separator"]}>/</span>) : null}
    </div> );

    return(
        <div className={classes["path-bar-wrapper"]}>
            {path}
        </div>
    )
}

