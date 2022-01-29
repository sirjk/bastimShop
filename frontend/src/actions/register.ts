import client from '../services/api'

const register = {

    register: (firstName: string, lastName: string, email:string, password:string, address:string, birthDate:string, city:string, country:string, postalAddress:string) => {
        return (client.post("/register",{
            firstName:firstName,
            lastName: lastName,
            email: email,
            password: password,
            address: address,
            birthDate: birthDate,
            city: city,
            country: country,
            postalAddress: postalAddress,
            },{ headers: {
                'Content-Type': 'application/json'
            }}
        ))},
}

export default register;