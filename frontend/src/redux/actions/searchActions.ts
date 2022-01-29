export const searchPhrase = (searchPhrase: string) => {
    return {
        type: "SET_SEARCH_PHRASE",
        payload: searchPhrase
    }
}
