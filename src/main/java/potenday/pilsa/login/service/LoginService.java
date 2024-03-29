package potenday.pilsa.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import potenday.pilsa.global.exception.AuthException;
import potenday.pilsa.global.exception.ExceptionCode;
import potenday.pilsa.login.AccessTokenExtractor;
import potenday.pilsa.login.domain.KakaoProvider;
import potenday.pilsa.login.domain.TokenProvider;
import potenday.pilsa.login.dto.request.LoginRequest;
import potenday.pilsa.login.dto.response.AccessTokenResponse;
import potenday.pilsa.login.dto.response.MemberInfoResponse;
import potenday.pilsa.login.dto.response.TokenPair;
import potenday.pilsa.member.domain.Member;
import potenday.pilsa.member.domain.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final KakaoProvider kakaoProvider;
    private final AccessTokenExtractor accessTokenExtractor;

    public TokenPair login(LoginRequest request) {
        String accessToken = kakaoProvider.getAccessToken(request);
        MemberInfoResponse memberInfo = kakaoProvider.getMemberInfo(accessToken);

        Member member = memberRepository.findBySocialTypeAndMemberKey(request.getSocialType(), memberInfo.getMemberKey()).orElseGet(
                () -> createMember(memberInfo)
        );

        return tokenProvider.createTokenPair(member.getId());
    }

    public AccessTokenResponse renewAccessToken(
            String authorizationHeader, String refreshToken)  {
        String accessToken = accessTokenExtractor.extractAccessToken(authorizationHeader);

        if (!tokenProvider.isExpiredAccessAndValidRefreshToken(accessToken, refreshToken)) {
            logout(refreshToken);
            throw new AuthException(ExceptionCode.FAIL_TO_VALIDATE_TOKEN);
        }

        Long memberId = tokenProvider.getMemberIdFromExpiredJwtToken(refreshToken);

        return AccessTokenResponse.builder()
                .accessToken(tokenProvider.createAccessToken(memberId))
                .build();
    }

    public void logout(String refreshToken) {
        tokenProvider.deleteRefreshToken(refreshToken);
    }

    private Member createMember(MemberInfoResponse member) {
        return memberRepository.save(
                new Member(
                        member.getSocialType(),
                        member.getEmail(),
                        member.getMemberKey(),
                        member.getImageUrl(),
                        member.getNickName()));
    }
}
