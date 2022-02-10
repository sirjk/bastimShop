import client from '../services/api'

const user = {

    getById: (id:number) => client.get(`/users/${id}`),
    putSelf: (id:number, firstName:string, lastName:string, email:string, birthDate:string, country:string, city:string, address: string, postalAddress: string, newPassword:string|null, oldPassword:string) => client.put(`/users/${id}`,{
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