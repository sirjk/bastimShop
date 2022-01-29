import client from '../services/api'

const user = {

    getById: (id:number) => client.get(`/users/${id}`),
}

export default user;