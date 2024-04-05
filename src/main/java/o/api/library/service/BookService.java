package o.api.library.service;

import jdk.jfr.Description;
import o.api.library.config.ApiResponseManager;
import o.api.library.dto.BookCheckoutDto;
import o.api.library.dto.BookConsignDto;
import o.api.library.dto.BookListDto;

public interface BookService {
    @Description("도서 위탁")
    ApiResponseManager consignBook(BookConsignDto bookConsignDto);

    @Description("도서 대여")
    ApiResponseManager checkoutBook(BookCheckoutDto bookCheckoutDto);

    @Description("조건에 맞는 도서 목록 조회")
    ApiResponseManager bookList(BookListDto bookListDto);

    @Description("도서 반납 스케줄러")
    ApiResponseManager returnBook();
}
