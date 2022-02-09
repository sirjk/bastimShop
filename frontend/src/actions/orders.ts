import client from '../services/api'

const orders = {
    getAll: () =>{
        return (client.get("/orders")
        )
    },

}

export default orders;