package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.entities.Basket;
import com.iase24.springjunit.repository.BasketRepository;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.service.BasketService;
import com.iase24.springjunit.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;

    @Override
    public Optional<Basket> getBasketById(Long id) {

        return basketRepository.findById(id);
    }
}
