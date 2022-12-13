package com.sparta.springboards.service;

import com.sparta.springboards.dto.LoginRequestDto;
import com.sparta.springboards.dto.SignupRequestDto;
import com.sparta.springboards.entity.User;
import com.sparta.springboards.entity.UserRoleEnum;
import com.sparta.springboards.exception.CustomException;
import com.sparta.springboards.jwt.JwtUtil;
import com.sparta.springboards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

import static com.sparta.springboards.exception.ErrorCode.*;

@Service

//다른 계층에서 파라미터를 검증하기 위해서
@Validated          //추가

//final 또는 @NotNull 이 붙은 필드의 생성자를 자동 생성. 주로 의존성 주입(Dependency Injection) 편의성을 위해서 사용
@RequiredArgsConstructor

public class UserService {

//회원가입 구현

    //의존성 주입(DI)
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;  //의존성 주입(DI) --> jwtUtil.class 에서 @Component 로 빈을 등록했기때문에 가능
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    //SignupRequestDto 에서 가져온 username, password 를 확인
    public void signup(@Valid SignupRequestDto signupRequestDto) {      //@Valid 추가 주의!
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());   //저장하기 전에 password 를 Encoder 한다

        // 회원 중복 확인
        //userRepository 에서 username 으로 실제 유저가 있는지 없는지 확인
        Optional<User> found = userRepository.findByUsername(username);  //userRepository 에 구현하기
        //중복된 유저가 존재한다면,
        if (found.isPresent()) {
            //해당 메시지 보내기
            throw new CustomException(DUPLICATED_USERNAME);
        }

        // 사용자 ROLE(권한) 확인
        UserRoleEnum role = UserRoleEnum.USER;
        //SignupRequestDto 에서 admin 이 true 라면(admin 으로 로그인을 시도하려고 하는구나),
        if (signupRequestDto.isAdmin()) {
            //들어온 Token 값과 위의 검증을 위한 Token 값(위쪽에 AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC)이 일치하는지 확인
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                //일치하지 않으면(매개변수가 의도치 않는 상황 유발시), 해당 메시지를 보낸다
                throw new CustomException(INVALID_AUTH_TOKEN);
            }
            //일치하면, user 를 admin 타입으로 바꾼다
            role = UserRoleEnum.ADMIN;
        }

        //가져온 username, password, role(UserRoleEnum)를 넣어서 저장(save)
        User user = new User(username, password, role);
        userRepository.save(user);
    }

    //로그인 구현
    @Transactional(readOnly = true)
    //로그인이 되면, LoginRequestDto 로 username, password 가 넘어온다
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        //username 을 통해 확인해서, 있다면 User 객체에 담긴다
        User user = userRepository.findByUsername(username).orElseThrow(
                //없다면(매개변수가 의도치 않는 상황 유발시), 해당 메시지 보내기
                () -> new CustomException(NOT_FOUND_USER)
        );
        // 비밀번호 확인
        //User 객체에 들어있던 Password 와 가지고 온 Password(String password = loginRequestDto.getPassword() 에 있는) 가 일치하는지 확인
        //일치하지 않는다면, 해당 메시지 보내기
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw  new CustomException(NOT_MATCH_INFORMATION);
        }

        //response 에 addHeader() 를 사용해서, Header 쪽에 값을 넣는데
        //AUTHORIZATION_HEADER: KEY 값
        //createToken(user.getUsername(), user.getRole()): Username(이름), Role(권한)을 넣어서 토큰을 만든다
        //위의 User 객체에서 유저 정보를 가져왔기 떄문에 사용 가능한 것
        //jwtUtil 를 사용하기 위해, 위에서 의존성 주입을 해줘야 함
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }
}
