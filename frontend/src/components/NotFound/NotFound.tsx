import React, {FunctionComponent} from 'react'
import classesGlobal from "../../app.module.css";

interface Props{
}


export const NotFound: FunctionComponent<Props>=(props: Props)=>{


    return(
        <div className={classesGlobal.page}>
            <div className={classesGlobal["content"]}>
                <span style={{fontSize: "115px"}}>404: Not Found</span>
            </div>
        </div>);
}