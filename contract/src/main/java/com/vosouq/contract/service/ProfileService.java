package com.vosouq.contract.service;

import com.vosouq.contract.model.User;

public interface ProfileService {

    User find(Long id);
}