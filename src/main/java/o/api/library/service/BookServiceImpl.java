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
    public ApiResponseManager consignBooks(List<BookConsignDto> bookConsignDtoList) {
        // 위탁한 도서 리스트를 담는 객체 선언
        List<Book> books = new ArrayList<>();
        // 받아온 DTO에 있는 도서 정보들을 Entity로 변환하여 Book 테이블에 저장
        bookConsignDtoList.parallelStream()
                .map(bookConsignDto -> {
                    Book book = bookRepository.save(bookConsignDto.consign());
                    books.add(book);
                    return new AbstractMap.SimpleEntry<>(book, bookConsignDto.historyByConsign(book));
                })
                .forEach(entry -> bookHistoryRepository.save(entry.getValue()));
        return ApiResponseManager.success(books);
    }

    @Override
    @Transactional
    @Description("도서 대여")
    public ApiResponseManager checkoutBooks(List<BookCheckoutDto> bookCheckoutDtoList) {
        List<Book> checkoutBookList = new ArrayList<>();    // 대여 성공한 도서 리스트
        List<Book> checkoutFailedBookList = new ArrayList<>();  // 대여 실패한 도서 리스트
        List<BookHistory> bookHistoryList = new ArrayList<>();  // 대여 성공한 도서 리스트를 BookHistory에 담기 위한 리스트
        Map<String, Object> checkoutResult = new HashMap<>(); // 도서 대여 결과를 담는 객체
        // 해당 도서가 존재하는지 체크하여 리스트로 가져오기
        List<Book> bookList = bookRepository.findByIdIn(bookCheckoutDtoList.stream().map(BookCheckoutDto::getId).toList());
        // 해당 도서의 status가 true이고 checkoutId가 null이면 Book 테이블에 BookCheckoutDto의 checkoutId로 Update하고 checkoutCount를 + 1하고 status를 false로 바꾼다.
        for (BookCheckoutDto bookCheckoutDto : bookCheckoutDtoList) {
            Optional<Book> optionalBook = bookList.stream()
                    .filter(book -> book.getId().equals(bookCheckoutDto.getId()))
                    .findFirst();
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                // 대여할 수 있는 도서인 경우 updateBooks에 Book을 추가
                if (book.isStatus() && book.getCheckoutId() == null) {
                    book.setCheckOutAndIncreaseCount(bookCheckoutDto.getCheckoutId());
                    checkoutBookList.add(book);
                    BookHistory bookHistory = bookCheckoutDto.historyByCheckout(book, bookCheckoutDto.getCheckoutId());
                    bookHistoryList.add(bookHistory);
                } else {
                    // 대여할 수 없는 경우 대여 실패 리스트(checkoutFailedBooks) 에 추가
                    checkoutFailedBookList.add(book);
                }
            } else {
                // 없는 도서인 경우 대여 실패 리스트(checkoutFailedBooks) 에 추가
                checkoutFailedBookList.add(optionalBook.get());
            }
        }
        // 대여할 수 있는 도서들은 Book 테이블에 UPDATE, BookHistory에 INSERT
        bookRepository.saveAll(checkoutBookList);
        bookHistoryRepository.saveAll(bookHistoryList);
        // 도서 대여 결과를 담아서 리턴
        checkoutResult.put("checkoutBookList", checkoutBookList);
        checkoutResult.put("checkoutFailedBookList", checkoutFailedBookList);
        checkoutResult.put("bookHistoryList", bookHistoryList);
        return ApiResponseManager.success(checkoutResult);
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
}
