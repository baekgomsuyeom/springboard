package com.sparta.springboards.repository;

import com.sparta.springboards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//<연결할 엔티티(테이블) 클래스 이름, ID 필드 타입> --> User 에서 id 필드의 타입이 Long 이기 때문
public interface UserRepository extends JpaRepository<User, Long> {

    // 회원 중복 확인
    //Optional: null 을 반환하면 오류가 발생할 가능성이 매우 높은 경우에 '결과 없음'을 명확하게 드러내기 위해
    //--> 작성, 수정, 삭제에서 해당 Username 이 DB 에 있는지 확인하고, 없으면 예외 처리를 하기때문에 여기선 적절한 사용법으로 보임
//    Optional<com.sparta.springboards.entity.User> findByUsername(String username);
    Optional<User> findByUsername(String username);
}