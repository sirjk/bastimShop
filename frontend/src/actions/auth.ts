import client from '../services/api'

const auth = {

    login: (email: string, password: string) => {
        const params = new URLSearchParams();
        params.append('username', email)
        params.append('password', password)
        return (client.post("/login",
            params,
        {  headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }})
    )},

    logout: ()=>{
        return(client.post("/logout"))
    },

    refreshToken:()=>{
        return(client.post("/refresh_token"))
    }
}

export default auth;



