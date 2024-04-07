package o.api.library.service;

import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import o.api.library.config.ApiResponseManager;
import o.api.library.dto.BookCheckoutDto;
import o.api.library.dto.BookConsignDto;
import o.api.library.dto.BookListRequestDto;
import o.api.library.dto.BookListResponseDto;
import o.api.library.entity.Book;
import o.api.library.entity.BookHistory;
import o.api.library.entity.Member;
import o.api.library.repository.BookHistoryRepository;
import o.api.library.repository.BookRepository;
import o.api.library.repository.MemberRepository;
import o.api.library.specification.BookSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private BookHistoryRepository bookHistoryRepository;
    private MemberRepository memberRepository;

    @Override
    @Transactional
    @Description("도서 위탁")
    public ApiResponseManager consignBook(BookConsignDto bookConsignDto) {
        // Book 테이블에 INSERT
        Book book = bookRepository.save(bookConsignDto.consign());
        // BookHistory 테이블에 INSERT
        bookHistoryRepository.save(bookConsignDto.historyByConsign(book));
        return ApiResponseManager.success(book);
    }

    @Override
    @Transactional
    @Description("도서 대여")
    public ApiResponseManager checkoutBook(BookCheckoutDto bookCheckoutDto) {
        // 해당 도서가 존재하는지 확인하고 존재하면 checkOutId를 checkOut에 Update하고, checkOutCount를 + 1 한다.
        Optional<Book> optionalBook = bookRepository.findById(bookCheckoutDto.getId());
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            Optional<Member> optionalMember = memberRepository.findById(bookCheckoutDto.getCheckoutId());
            if (optionalMember.isPresent()) {
                Member checkoutMember = optionalMember.get();
                book.setCheckOutAndIncreaseCount(checkoutMember.getId());
                bookRepository.save(book);
                // BookHistory에 해당 내용을 INSERT
                BookHistory bookHistory = bookCheckoutDto.historyByCheckout(book, checkoutMember.getId());
                bookHistoryRepository.save(bookHistory);
                return ApiResponseManager.success(book);
            } else {
                return ApiResponseManager.error(HttpStatus.NOT_FOUND, "대여자를 찾을 수 없습니다");
            }
        } else {
            return ApiResponseManager.error(HttpStatus.NOT_FOUND, "해당 도서를 찾을 수 없습니다");
        }
    }

    @Override
    @Description("조건에 맞는 도서 목록 조회")
    public ApiResponseManager bookList(BookListRequestDto bookListRequestDto, Pageable pageable) {
        // 정렬 방법에 따라 도서 목록 조회(specification 사용)
        Sort sortingMethod = Sort.by(bookListRequestDto.getBookSortingMethod().name().toLowerCase());
        Page<Book> bookPage = bookRepository.findAll(
                BookSpecification.withSortingMethod(
                        bookListRequestDto.getBookSortingMethod(),
                        bookListRequestDto.getMinimumPrice(),
                        bookListRequestDto.getMaximumPrice()
                ),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortingMethod)
        );
        // 엔터티 목록을 DTO로 변환하여 반환
        List<BookListResponseDto> responseDtoList = bookPage.getContent().stream()
                .map(book -> {
                    Optional<Member> memberOptional = memberRepository.findById(book.getConsignerId());
                    return memberOptional.map(member ->
                            BookListResponseDto.builder()
                                    .title(book.getTitle())
                                    .isbn(book.getIsbn())
                                    .price(book.getPrice())
                                    .consignerName(member.getName())
                                    .build()
                    ).orElse(null); // 엔티티와 관련된 정보가 없을 경우 null 반환
                })
                .filter(Objects::nonNull) // null이 아닌 DTO만 필터링
                .collect(Collectors.toList());
        // 응답에 전체 페이지 수를 추가하여 반환
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("bookList", responseDtoList);
        responseData.put("totalPage", bookPage.getTotalPages());

        return ApiResponseManager.success(responseData);
    }

    @Override
    @Description("도서 반납(10초에서 20초 사이 랜덤 주기 실행")
    @Scheduled(fixedDelayString = "#{ T(java.lang.Math).random() * (20000 - 10000) + 10000 }")
    public void returnBook() {
        log.info("**** 도서 반납 스케줄러 실행");
    }
}
