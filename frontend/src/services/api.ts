import axios, {AxiosError, AxiosRequestConfig, AxiosResponse} from 'axios'
import auth from "../actions/auth";
import {setIsLogged} from "../redux/actions/loginActions";
import store from '../redux/store';

const instance = axios.create({
    baseURL: 'http://localhost:8080/api/v1',
    withCredentials: true
})


instance.interceptors.response.use(function (response) {
    //console.log("interceptors ok")
    //console.log(response)
    return response;
}, function (error) {
    //console.log(error.response)
    //console.log(error.request)
    //console.log(error.response.data.message)
    let heresy: any = error;
     if(error.response.status === 401 /*&& error.response.data.error_message ==="Refresh token expired"*/){
        //dispatch(setIsLogged("false"));
         store.dispatch(setIsLogged("false"))
         //console.log("ASDDDDDDDDDDDDDDDDDD")
        // return Promise.reject(error);
     }
    /*if (error.response.status === 401){
        //console.log("interceptors error")
       // let axiosResponse :AxiosResponse = error.response;
       // let flag:boolean = false;
        let babel: Promise<any> ;
        auth.refreshToken()
            .then(()=>{
                   // console.log("interceptors refreshToken")
                    console.log("error.request.url",error.request.url)
                    console.log("error.request.data", error.request.data)
                    console.log("error.request.config", error.request.config)
                    axios.post(error.request.url, error.request.data, error.request.config).then(r  => {
                        console.log("interceptors refreshToken response")
                        axiosResponse = r;
                        flag = true;
                    })
                   // flag=true;
                    error.response.headers.set("retry","true");

                    return Promise.reject(error);
                }
            )
            .catch(()=> {
               // if(e.response.data.error_message ==="Refresh token expired"){
                 //   //dispatch(setIsLogged("false"));
                 //   return Promise.reject(error);
              //  }
            })
       // if(flag){
        //    error.response.headers.set("retry","true");
        //}
    }*/
    //console.log("yyyet")
    return Promise.reject(error);
});

export default instance;