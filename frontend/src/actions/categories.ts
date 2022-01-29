import { idText } from 'typescript';
import client from '../services/api'

const categories = {
    getAllFilteredRawParams: (parameters: string) =>{
        let endpointAndParameters = "/categories" + parameters;
        return(client.get(endpointAndParameters))
    },
    getPeculiarCategory: (id: string) => {
        let endpointWithId = "/categories/" + id;
        return(client.get(endpointWithId))
    }

}

export default categories;