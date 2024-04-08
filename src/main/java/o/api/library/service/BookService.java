package o.api.library.service;

import jdk.jfr.Description;
import o.api.library.config.ApiResponseManager;
import o.api.library.dto.BookCheckoutDto;
import o.api.library.dto.BookConsignDto;
import o.api.library.dto.BookListRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookService {
    @Description("도서 위탁")
   ApiResponseManager consignBooks(List<BookConsignDto> bookConsignDtoList);

    @Description("도서 대여")
    ApiResponseManager checkoutBook(BookCheckoutDto bookCheckoutDto);

    @Description("조건에 맞는 도서 목록 조회")
    ApiResponseManager bookList(BookListRequestDto bookListRequestDto, Pageable pageable);
}
