# o-library

# 우도 API

<aside>
❗ **Notion 팁:** 이 템플릿을 활용해 Notion에서 API 레퍼런스를 작성하고 호스팅해 보세요.
엔드포인트 데이터베이스에는 추가 문서를 위한 엔드포인트 템플릿도 포함되어 있습니다.

</aside>

# 기본 정보

## 요구사항

우도 백엔드 시스템 개발은 고객이 도서를 대여하고 위탁하기 위해 사용하는 우도 서비스의 백엔드 애플리케이션을 개발하는 중요한 직무입니다. 따라서 아래와 같은 직무 역량을 갖추어야 한다고 생각합니다. (본 과제 결과물에 대한 판단 기준도 동일합니다)

- 서비스 기획 내용에 대한 **정확한 이해**
- 기능 요구사항에 대한 **효율적이고 적시적인 구현**
- **버그를 최소화**하는 높은 코드 품질
- 유연한 변경 사항 대응을 위한 **재사용 및 확장 가능한** 애플리케이션 구조 설계
- 협업을 위한 **코드 및 문서 가독성**

이를 위해 본 과제에서는 우도 서비스의 도서 대여 및 위탁 기능에 필요한 API를 개발하려고 합니다.

- 회원 가입
- 도서 위탁
- 도서 대여
- **도서 목록**
- 도서 반납

---

### **규칙**

모든 API 요청을 전송하는 기본 URL은 `localhost:1234/udo`입니다.

우도API는 페이지와 데이터베이스 리소스에 대한 `GET`, `POST`, `PUT`, `DELETE` 요청을 통해 대부분 작업을 수행하는 등 가능한 한 RESTful 규칙을 따릅니다. 이번 요구사항에서는 기능에 따라 RESTful 규칙을 위배할 수 있습니다. 요청과 응답 본문은 JSON으로 인코딩됩니다.

**테이블 규칙**

- 속성 이름은 `snake_case` 를 사용합니다.

**JSON 규칙**

- 속성 이름은 `Camel Case` 를 사용합니다.
- 시간 값(날짜와 일시)은 [ISO 8601](https://ko.wikipedia.org/wiki/ISO_8601) 문자열로 인코딩됩니다. 일시는 시간 값(`2020-08-12T02:12:33.231Z`)을 포함하며, 날짜는 날짜(`2020-08-12`)만 포함합니다.
- 우도 API는 빈 문자열을 지원하지 않습니다. 예를 들어, `url` [속성값 개체](https://developers.notion.com/reference/property-value-object)와 같은 속성의 문자열 값을 설정 해제하려면 `""` 대신 명시적인 `null`을 사용하세요.

---

### API 목록

| Method | URL | 기능 |
| --- | --- | --- |
| POST | /member | 회원 가입 |
| POST | /book/consignment | 도서 위탁 |
| POST | /book/checkOut | 도서 대여 |
| GET | /book/list | 도서 목록 조회 |
| POST | /book/return | 도서 반납 |

---

### 테이블 설계

[Member](%E1%84%8B%E1%85%AE%E1%84%83%E1%85%A9%20API%20b67d922c5a5e4fdbae9107f67b6a73f8/Member%20ea3c5c0199d64281957627c2c839182e.csv)

[Book](%E1%84%8B%E1%85%AE%E1%84%83%E1%85%A9%20API%20b67d922c5a5e4fdbae9107f67b6a73f8/Book%20d4a99aba8d8c40a7a719d9be79e1f89f.csv)

---

### Feature Specification

**1. 회원 가입**

가입한 회원이 자신이 소장한 도서를 우도에 위탁하는 기능입니다. 위탁된 도서는 다른 회원이 대여할 수 있습니다.

- Request

| 항목 | 설명 | 속성명 | 타입 | nullable | validation | default |
| --- | --- | --- | --- | --- | --- | --- |
| 이름 | 회원 이름 | name | string(50) | X | 숫자를 제외한 문자열만 가능 |  |
| 이메일 | 회원 이메일 | email | string(50) | X | 이메일 형식으로만 가능 |  |
| 휴대폰 번호 | 회원 휴대폰 번호 | phone | string(11) | X | 0~9까지의 숫자로만 11자리까지 |  |
| 비밀번호 | 로그인을 위한 비밀번호
- 최소 6자 이상 10자 이하
- 영문 소문자, 대문자, 숫자 중 최소 두 가지 이상 조합 필요 | password | string(10) | X | 1. 영문 대소문자, 숫자 중 최소 2가지 이상의 조합
2. 최소 6자 이상, 최대 10자 이하 |  |
- Response

| 항목 | 설명 |
| --- | --- |
| status | 응답 코드 |
| message | 응답 메시지 |
| data | 가입 정보 |

**2. 도서 위탁**

가입한 회원이 자신이 소장한 도서를 우도에 위탁하는 기능입니다. 위탁된 도서는 다른 회원이 대여할 수 있습니다.

- Request

| 항목 | 설명 | 속성명 | 타입 | nullable | validation | default |
| --- | --- | --- | --- | --- | --- | --- |
| 도서명 | 도서 제목 | title | string(50) | X | 숫자를 제외한 문자열만 가능 |  |
| ISBN | 도서에 부여되는 https://namu.wiki/w/ISBN | isbn | string(50) | X | 이메일 형식으로만 가능 |  |
| 대여 가격 | 대여자가 해당 도서를 대여할 때 지불하는 가격 | price | string(11) | X | 0~9까지의 숫자로만 11자리까지 |  |
| 위탁자 ID | 위탁자의 회원 ID | consignerId | BIGINT | X | 1 이상의 양수 |  |
| 상태 | 대여 가능 여부 (0: 불가, 1: 가능) | status | number | X | 0, 1만 가능 | 0 |
- Response

| 항목 | 설명 |
| --- | --- |
| status | 응답 코드 |
| message | 응답 메시지 |
| data | 도서 위탁 정보 |

**3. 도서 대여**

가입한 회원이 위탁된 도서를 대여하는 기능입니다. 회원이 앱의 대여 가능 도서 목록에서 자신이 대여하고 싶은 도서를 탐색합니다.

- 도서 목록은 pagination을 제공합니다. 1페이지 당 20개의 도서를 불러옵니다.
- 대여 많은 순, 낮은 가격순, 최근 등록일순으로 도서 목록 정렬이 가능합니다. 대여 많은 순은 가장 대여가 많이 된 도서를 먼저 보여줍니다.
- 도서 목록의 각 도서 아이템은 아래 정보를 표시합니다.
    - 도서명
    - ISBN
    - 대여 가격
    - 위탁자 이름
    - **대여 가능 여부 (가능, 불가능)**
- 회원이 대여를 원하는 책을 선택하고 대여하기 버튼을 클릭하면 대여가 완료됩니다. 대여 중인 도서는 다른 회원이 대여할 수 없습니다.
- Request

| 항목 | 설명 | 속성명 |
| --- | --- | --- |
| 도서명 | 도서 제목 | title |
| ISBN | 도서에 부여되는 https://namu.wiki/w/ISBN | isbn |
| 대여 가격 | 대여자가 해당 도서를 대여할 때 지불하는 가격 | price |
| 위탁자 이름 | 위탁자의 이름 | consigner |
| 상태 | 대여 가능 여부 | status |
- Response

| 항목 | 설명 |
| --- | --- |
| status | 응답 코드 |
| message | 응답 메시지 |
| data | 대여한 도서 목록 |

**4. 도서 목록**

가입한 회원이 위탁된 도서 목록을 조회하는 기능입니다. 회원이 앱의 대여 가능 도서 목록에서 자신이 대여하고 싶은 도서 목록을 탐색합니다.

- 도서 목록은 pagination을 제공합니다. 1페이지 당 20개의 도서를 불러옵니다.
- 대여 많은 순, 낮은 가격순, 최근 등록일순으로 도서 목록 정렬이 가능합니다. 대여 많은 순은 가장 대여가 많이 된 도서를 먼저 보여줍니다.
- 도서 목록의 각 도서 아이템은 아래 정보를 표시합니다.
    - 도서명
    - ISBN
    - 대여 가격
    - 위탁자 이름
    - **대여 가능 여부 (가능, 불가능)**
- **목록 조회에 필요한 파라미터 추가**
    - **검색어(제목)**
    - **대여 가격(최소, 최대)**
- Request

| 항목 | 설명 | 속성명 |
| --- | --- | --- |
| 정렬 방법 | 대여 많은 순, 낮은 가격 순, 최근 등록일 순 등 | sortingMethod |
| 검색어 | 제목으로 검색할 수 있도록 추가 | isbn |
| 대여 가격 | 대여자가 해당 도서를 대여할 때 지불하는 가격 | price |
| 위탁자 이름 | 위탁자의 이름 | consigner |
| 상태 | 대여 가능 여부 | status |
- Response

| 항목 | 설명 |
| --- | --- |
| status | 응답 코드 |
| message | 응답 메시지 |
| data | 조건에 맞는 도서 목록 |

**5. 도서 반납**

도서 반납은 도서가 대여된 시점으로부터 최소 10초 ~ 최대 20초가 경과하면 자동으로 반납 처리합니다. 반납된 도서는 다른 회원이 대여할 수 있습니다. 해당 기능은 스케줄러로 구현합니다.

- 실행 주기
    - 10초에서 20초 사이의 랜덤한 시간을 주기로 도서 반납 처리 메서드를 실행합니다.
      @Scheduled(fixedDelayString = "#{ T(java.lang.Math).random() * (20000 - 10000) + 10000 }")

---

## 상태 코드

HTTP 응답 코드는 일반적인 성공과 오류 클래스를 나타내는 데 사용됩니다.

### 성공 코드

| HTTP 상태 | 설명 |
| --- | --- |
| 200 | 성공적으로 처리된 요청 |

### 오류 코드

오류 응답 본문의 `“status"`와 `"message"` 속성에서 오류에 대한 더 구체적인 세부 정보를 확인할 수 있습니다.

| HTTP 상태명 | status | message |
| --- | --- | --- |
| 400 | 400 | Bad request |
| 500 | 500 | Internal server error |