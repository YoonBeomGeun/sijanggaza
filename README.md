<img src="https://github.com/YoonBeomGeun/sijanggaza/assets/145637270/7d2a9977-2433-4f38-b43e-f333a41ce0f0" alt="시장" width="50" heigth="50">

### 🧨준비, 시장

## 📖 프로젝트 요약
기술이 발전함에 따라 많은 전통시장은 현대적 시설과 인프라가 부족한 상황이다. 이는 편의성과 접근성 측면에서 비교적 낮은 인지도와 부족한 정보로 인해 매출 부진으로 이어져 고객 감소 및 지역 경제 활성화의 문제가 되고 있다.

하지만, 전통 시장은 많은 사람들에게 중요한 쇼핑 장소이자 지역 경제의 중심이다. 이러한 전통시장의 활성화를 돕고 지역 경제를 강화하고자 ‘준비, 시장’ 커뮤니티 서비스를 개발하게 되었다.

해당 서비스를 이용하는 전통시장 관련 사업자들은 ‘상품존’ 게시글을 통해 상품 할인 등의 판촉 행사 및 다양한 지역 홍보를 진행할 수 있고, 일반 이용자들은 저렴한 가격의 상품을 구입할 수 있는 기회를 얻을 수 있을 뿐만 아니라 ‘소식 및 정보’ 게시글을 통해 시장 정보를 확보하여 접근성을 높일 수 있다.


## ✨ 주요 기능

- **회원가입 및 로그인**
    - Spring Security를 통한 회원가입 및 로그인(해시 함수 사용)
    - 회원 유형에 따라 상이한 권한 부여
    - 조건 별 오류 메시지 출력
- **게시글 검색 및 페이징**
    - 키워드를 포함한 게시글 검색
    - 작성 일시를 기준으로 페이징(API 최적화 진행)
- **댓글 및 추천**
    - 인증된 회원에 한해 댓글 및 추천 기능 제공
    - 댓글 추가, 수정, 삭제 시 앵커 기능 활용
- **상품 게시글 등록**
- 회원 유형이 사업자인 경우, 상품 등록 게시글 작성 가능
- JavaScript를 이용한 입력란 추가 및 삭제
- **상품 주문 및 취소**
    - 주문 기능 추가
    - 조건 별 오류 메시지 출력
    - 금액 별 구매 제한
    - 재고 증감 로직 구현(동시성 고려)

## 아키텍처

<img src="https://github.com/YoonBeomGeun/sijanggaza/assets/145637270/2021097f-c034-4c5b-afc9-055434c03bb7" alt="아키텍처" width="800" heigth="800">

## ERD

<img src="https://github.com/YoonBeomGeun/sijanggaza/assets/145637270/598adaae-f624-4860-b5ff-a67c28905341" alt="ERD" width="800" heigth="800">

## 상세 설명



