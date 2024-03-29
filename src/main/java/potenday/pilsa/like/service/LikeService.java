package potenday.pilsa.like.service;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potenday.pilsa.global.dto.request.RequestPageDto;
import potenday.pilsa.global.exception.BadRequestException;
import potenday.pilsa.global.exception.ExceptionCode;
import potenday.pilsa.like.domain.Like;
import potenday.pilsa.like.domain.LikeType;
import potenday.pilsa.like.domain.repository.LikeRepository;
import potenday.pilsa.like.dto.response.ResponseLikeDto;
import potenday.pilsa.member.domain.Member;
import potenday.pilsa.member.domain.Status;
import potenday.pilsa.member.domain.repository.MemberRepository;
import potenday.pilsa.pilsa.domain.Pilsa;
import potenday.pilsa.pilsa.domain.repository.PilsaRepository;
import potenday.pilsa.pilsa.dto.response.ResponsePilsaDetailDto;
import potenday.pilsa.pilsa.dto.response.ResponsePilsaListDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ToString
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final PilsaRepository pilsaRepository;
    private final MemberRepository memberRepository;

    public ResponsePilsaListDto myLikeList(Long memberId, RequestPageDto request) {
        Page<Like> like = likeRepository.findByMember_IdAndPilsa_DeleteDateIsNullOrderByRegistDateDesc(memberId, request.toPageable());
        List<Pilsa> pilsaList = like.stream()
                .map(Like::getPilsa)
                .toList();

        return ResponsePilsaListDto.from(getPilsaDetailResponseDto(pilsaList, memberId), like.getTotalElements());
    }

    public ResponseLikeDto like(Long pilsaId, Long memberId) {
        Pilsa pilsa = pilsaRepository.findByPilsaIdAndDeleteDateIsNull(pilsaId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND_PILSA)
        );

        return checkLike(pilsa, memberId);
    }

    private ResponseLikeDto checkLike(Pilsa pilsa, Long memberId) {
        likeRepository.findByMember_IdAndPilsa(memberId, pilsa).ifPresentOrElse(
                this::deleteLike,
                () -> addLike(pilsa, memberId)
        );

        return new ResponseLikeDto(pilsa.getPilsaId(), likeRepository.findByMember_IdAndPilsa(memberId, pilsa).isPresent() ? LikeType.ADD : LikeType.DELETE);
    }

    private void addLike(Pilsa pilsa, Long memberId) {
        likeRepository.save(
                new Like(pilsa, getMember(memberId))
        );
    }

    private void deleteLike(Like like) {
        likeRepository.delete(like);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findByIdAndStatus(memberId, Status.ACTIVE).orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND_MEMBER)
        );
    }

    private List<ResponsePilsaDetailDto> getPilsaDetailResponseDto(List<Pilsa> pilsas, Long memberId) {
        return pilsas.stream().map(
                        pilsa -> ResponsePilsaDetailDto.from(
                                pilsa, isLikeAblePilsa(memberId, pilsa.getPilsaId())))
                .collect(Collectors.toList());
    }

    private Boolean isLikeAblePilsa(Long memberId, Long pilsaId) {
        return likeRepository.existsByMember_IdAndPilsa_PilsaId(memberId, pilsaId);
    }
}
