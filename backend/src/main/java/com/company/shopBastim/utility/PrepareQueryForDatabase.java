package com.company.shopBastim.utility;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

public class PrepareQueryForDatabase {
    public Pageable setupPegableFromParams(Map<String,String> params){
        Sort sort;
        if(params.get("sort-by") == null){
            sort = null;
        }
        else if(params.get("sort-type").equals("descending")){
            sort = Sort.by(params.get("sort-by")).descending();
        }
        else{
            sort = Sort.by(params.get("sort-by")).ascending();

        }

        Pageable pagingType;
        Integer page = 0;
        Integer limit = 20;
        if(params.get("limit")!=null)
            limit = Integer.parseInt(params.get("limit"));

        if(params.get("page")!= null)
            page = Integer.parseInt(params.get("page"));

        if(sort != null) {
            pagingType = PageRequest.of(page, limit , sort);
        }

        else{
            pagingType = PageRequest.of(page, limit);
        }

        return pagingType;
    }
}
