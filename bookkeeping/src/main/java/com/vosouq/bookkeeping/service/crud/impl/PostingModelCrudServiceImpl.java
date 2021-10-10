package com.vosouq.bookkeeping.service.crud.impl;

import com.vosouq.bookkeeping.model.posting.PostingModel;
import com.vosouq.bookkeeping.repository.PostingModelRepository;
import com.vosouq.bookkeeping.service.crud.PostingModelCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostingModelCrudServiceImpl implements PostingModelCrudService {

    private final PostingModelRepository postingModelRepository;

    public PostingModelCrudServiceImpl(PostingModelRepository postingModelRepository) {
        this.postingModelRepository = postingModelRepository;
    }


    @Override
    public PostingModel findByCode(Integer code) {
        return postingModelRepository.findByCode(code);
    }
}
