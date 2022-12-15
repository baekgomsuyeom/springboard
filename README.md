# Spring Boot Team Project
> 🚩 Goal: "Spring Security를 적용한 나만의 항해 블로그 백엔드 서버 만들기"
</br>

## 📝 목차
1. [서비스 완성 요구사항](#1-서비스-완성-요구사항)
2. [ERD](#2-ERD)
3. [API 명세서](#3-API-명세서)
4. [Git-flow 전략](#4-Git-flow-전략)
5. [팀원 정보](#5-팀원-정보)

## 1. 서비스 완성 요구사항
1.  숙련주차 개인과제 LV2 프로젝트에 Spring Security 적용하기
2.  게시글 좋아요 API
    -   사용자는 선택한 게시글에 ‘좋아요’를 할 수 있습니다.
    -   사용자가 이미 ‘좋아요’한 게시글에 다시 ‘좋아요’ 요청을 하면 ‘좋아요’를 했던 기록이 취소됩니다.
    -   요청이 성공하면 Client 로 성공했다는 메시지, 상태코드 반환하기
    -   게시글/댓글에 ‘좋아요’ 개수도 함께 반환하기
3.  댓글 좋아요 API
    -   사용자는 선택한 댓글에 ‘좋아요’를 할 수 있습니다.
    -   사용자가 이미 ‘좋아요’한 댓글에 다시 ‘좋아요’ 요청을 하면 ‘좋아요’를 했던 기록이 취소됩니다.
    -   요청이 성공하면 Client 로 성공했다는 메시지, 상태코드 반환하기
    -   댓글에 ‘좋아요’ 개수도 함께 반환하기
4.  예외처리
    -   아래 예외처리를 AOP 를 활용하여 구현하기
    -   토큰이 필요한 API 요청에서 토큰을 전달하지 않았거나 정상 토큰이 아닐 때는 "토큰이 유효하지 않습니다." 라는 에러메시지와 statusCode: 400을 Client에 반환하기
	-   토큰이 있고, 유효한 토큰이지만 해당 사용자가 작성한 게시글/댓글이 아닌 경우에는 “작성자만 삭제/수정할 수 있습니다.”라는 에러메시지와 statusCode: 400을 Client에 반환하기
	-   DB에 이미 존재하는 username으로 회원가입을 요청한 경우 "중복된 username 입니다." 라는 에러메시지와 statusCode: 400을 Client에 반환하기
	-   로그인 시, 전달된 username과 password 중 맞지 않는 정보가 있다면 "회원을 찾을 수 없습니다."라는 에러메시지와 statusCode: 400을 Client에 반환하기
	-   회원가입 시 username과 password의 구성이 알맞지 않으면 에러메시지와 statusCode: 400을 Client에 반환하기

## 2. ERD
### 🤔 프로젝트 계획 단계 ERD
![enter image description here](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/15fef0a6-6c8d-49a3-a7de-1ebb7d6bedbd/%EC%8B%AC%ED%99%94_6%EC%A1%B0.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45/20221215/us-west-2/s3/aws4_request&X-Amz-Date=20221215T052126Z&X-Amz-Expires=86400&X-Amz-Signature=8d51bd6565510eabb8008312747030af5154356cbf7c2416398dc759c38533d4&X-Amz-SignedHeaders=host&response-content-disposition=filename=%22%25EC%258B%25AC%25ED%2599%2594%25206%25EC%25A1%25B0.png%22&x-id=GetObject)

### 🧐 프로젝트 구현 ERD

![ERD](https://user-images.githubusercontent.com/87196958/207775521-5484b05c-66fa-4a8e-bf75-326bab3cf256.png)

- post -> board로 변경
- 식별/비식별 관계 재정의
- 좋아요 개수 field 삭제(다대일 양방향 매핑으로 필요X)


## 3. API 명세서

![enter image description here](https://user-images.githubusercontent.com/87196958/207782113-1c349642-93ca-49ba-bc8d-a9133e306684.png)

[📬 Postman API 명세서](https://documenter.getpostman.com/view/24643348/2s8YzWT1ig)

## 4. Git-flow 전략
![enter image description here](https://velog.velcdn.com/images/ssol_916/post/63a1ec26-777d-4346-91a5-f94ae102bbc2/image.webp)
|                |내용                          |
|----------------|------------------------------|
|main(master)    |제품으로 출시될 수 있는 브랜치   |
|develop         |다음 출시 버전을 개발하는 브랜치 |
|feature         |기능을 개발하는 브랜치          |

## 5. 팀원 정보
| 이름 | 맡은 기능 |
|--|--|
| [박영준](https://github.com/baekgomsuyeom) | 게시글 API, 댓글 API, Spring Security 적용 |
| [안은솔](https://github.com/eunsol-an) | 게시글 좋아요 API, 예외처리 작업 |
| [이상현](https://github.com/Sangtriever) | 댓글 좋아요 API, 게시글 카테고리 작업 |
