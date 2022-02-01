
import React, {FunctionComponent} from 'react'
import classes from "../../app.module.css";
//import classesSpefic from "../css/contact.module.css";
import { PathBar } from '../PathBar/PathBar';

interface linkList{
    name: string,
    url: string
}

interface Props{
}


export const Contact: FunctionComponent<Props>=(props: Props)=>{
    const pathList : linkList[] = [{"name": "Bastim", "url": "/"}, {"name": "Contact", "url": "/contact"} ];
    return(       
        <div className={classes.page}>
            <PathBar pathList={pathList}/>
            <div className={classes["content"]}>tel: 111 222 333</div>
            
        </div>
    )
}


