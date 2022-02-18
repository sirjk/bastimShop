export const setDesiredPath = (newEndpoint: string) => {
    return {
        type: "SET_DESIRED_PATH",
        payload: newEndpoint
    }
}

export const setCategoriesPathMap = (newPathMap: Object) =>{
    return {
        type: "SET_CATEGORIES_PATH_MAP",
        payload: newPathMap
    }
}


export const setCategoryPath = (path: string) =>{
    return {
        type: "SET_CATEGORY_PATH",
        payload: path
    }
}