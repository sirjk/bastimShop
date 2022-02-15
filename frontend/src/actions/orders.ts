import client from '../services/api'

const orders = {
    getAll: () =>{
        return (client.get("/orders")
        )
    },
    getPeculiarOrder: (id: string | undefined)=>{
        return(client.get(`/orders/${id}`))
    }

}

export default orders;