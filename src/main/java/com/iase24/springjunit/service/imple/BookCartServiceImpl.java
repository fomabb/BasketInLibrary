package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookCartDataDTO;
import com.iase24.springjunit.repository.BookCartRepository;
import com.iase24.springjunit.service.BookCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookCartServiceImpl implements BookCartService {

    private final BookCartRepository bookCartRepository;

    @Override
    public List<BookCartDataDTO> findAllByCartId(Long cartId) {
        return bookCartRepository.findAllByCart_Id(cartId).stream()
                .map(bookCart -> new BookCartDataDTO(bookCart.getId(), bookCart.getCreationTime(), bookCart.getBook()))
                .collect(Collectors.toList());
    }
}
