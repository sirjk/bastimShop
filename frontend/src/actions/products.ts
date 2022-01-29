import client from '../services/api'

const products = {
    getAll: (categoryId: string, pageLimit:string, pageNumber:string, searchPhrase:string) =>{
        return (client.get("/products",
                {params:{
                        category: categoryId,
                        limit: pageLimit,
                        page: pageNumber,
                        search_phrase: searchPhrase
                }
                })
        )
    },
    getProductById: (id: string | undefined) =>{
        return (client.get(`/products/${id}`))
    }

}

export default products;