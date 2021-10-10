package com.vosouq.contract.utills;

import com.vosouq.contract.exception.BadPaginationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtil {

    public static Pageable buildPageable(Integer pageNumber,
                                         Integer pageSize,
                                         Boolean asc,
                                         SortBy sortBy) {
        if (pageNumber <= 0 || pageSize > 200)
            throw new BadPaginationException();

        Sort sort = sortBy.equals(SortBy.PRICE) ? Sort.by("price") : Sort.by("createDate");
        sort = asc ? sort.ascending() : sort.descending();
        return PageRequest.of(pageNumber - 1, pageSize, sort);
    }


}