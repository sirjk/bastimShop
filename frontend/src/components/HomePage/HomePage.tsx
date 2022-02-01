import React, {FunctionComponent} from 'react'
import classes from "../../app.module.css"
import { CategoriesMenu } from '../CategoriesMenu/CategoriesMenu'
import SimpleImageSlider from "react-simple-image-slider";

interface Props{
}

export const HomePage: FunctionComponent<Props>=(props: Props)=>{
    const images = [
        { url: "https://boltman.pl/wp-content/uploads/2017/01/tlo.png" },
        { url: "https://ecttechnik.pl/images/product/zatrzask-ze-sruba-i-nakretka-do-sekatora-p108-bahco-nr-kat-r478p-10700-large-1.png" },
        { url: "https://sklep.przybyszewski.eu/userdata/public/gfx/1252.jpg" },
        { url: "https://satsklep.pl/userdata/public/gfx/10249/nakretka.jpg" },

    ];
    return(
        <div className={classes.page} >
            <div style={{display:"flex", flexDirection:"row", width:"100%", justifyContent:"center", alignItems:"center", marginTop:"20px"}}>
                <SimpleImageSlider
                    style={{backgroundColor:"rgb(241, 241, 241)"}}
                    width={1080}
                    height={600}
                    images={images}
                    slideDuration={0.3}
                    showBullets
                    showNavs
                />
            </div>

        </div>
    )
}


