import client from '../services/api'

const user = {

    getById: (id:number) => client.get(`/users/${id}`),
    putSelf: (id:number|null, firstName:string|null, lastName:string|null, email:string|null, birthDate:string|null, country:string|null, city:string|null, address: string|null, postalAddress: string|null, newPassword:string|null, oldPassword:string) => client.put(`/users/${id}`,{
    "firstName": firstName,
    "lastName": lastName,
    "email": email,
    "birthDate": birthDate,
    "country": country,
    "city": city,
    "address": address,
    "postalAddress": postalAddress,
    "password": newPassword
    } ,{
        headers: {
            "password": oldPassword
        }
    }),
}

export default user;