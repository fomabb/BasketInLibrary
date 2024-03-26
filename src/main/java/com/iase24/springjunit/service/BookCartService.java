package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.BooCartDataDTO;

import java.util.List;

public interface BookCartService {
    List<BooCartDataDTO> findAllByCartId(Long cartId);
}
