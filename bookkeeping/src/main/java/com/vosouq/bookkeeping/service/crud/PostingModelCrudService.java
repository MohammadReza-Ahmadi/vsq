package com.vosouq.bookkeeping.service.crud;

import com.vosouq.bookkeeping.model.posting.PostingModel;

public interface PostingModelCrudService {

    PostingModel findByCode(Integer code);
}
