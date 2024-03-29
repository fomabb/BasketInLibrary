package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.BookCartDataDTO;

import java.util.List;
import java.util.Optional;

public interface BookCartService {
    List<BookCartDataDTO> findAllByCartId(Long cartId);
}
