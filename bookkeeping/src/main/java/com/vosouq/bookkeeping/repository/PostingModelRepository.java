package com.vosouq.bookkeeping.repository;

import com.vosouq.bookkeeping.model.posting.PostingModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostingModelRepository extends CrudRepository<PostingModel, Integer> {

    PostingModel findByCode(Integer code);
}
