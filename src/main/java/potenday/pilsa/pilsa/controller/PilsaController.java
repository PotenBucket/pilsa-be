package potenday.pilsa.pilsa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import potenday.pilsa.login.Auth;
import potenday.pilsa.pilsa.domain.Pilsa;
import potenday.pilsa.pilsa.dto.request.RequestPilsaInfoDto;
import potenday.pilsa.pilsa.dto.response.ResponsePilsaDetailDto;
import potenday.pilsa.pilsa.dto.response.ResponsePilsaMainListDto;
import potenday.pilsa.pilsa.service.PilsaService;

import java.net.URI;

@Tag(name = "필사 Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pilsa")
public class PilsaController {

    private final PilsaService pilsaService;

    @Operation(summary = "메인 페이지 전체 필사 리스트 조회(페이징처리)", description = "")
    @GetMapping("list")
    public ResponseEntity<ResponsePilsaMainListDto> getPilsaList() {
        // Response - ResponsePilsaMainListDto
        // TODO: 서비스 기능 구현

        return ResponseEntity.ok(null);
    }


    @Operation(summary = "내가 쓴 필사 리스트 조회(페이징처리)", description = "")
    @GetMapping
    public ResponseEntity<ResponsePilsaMainListDto> getPilsaListOfMember(
            @Parameter(hidden = true) @Auth Long memberId
    ) {
        // Response - ResponsePilsaMainListDto
        // TODO: 서비스 기능 구현

        return ResponseEntity.ok(null);
    }

    @Operation(summary = "필사 상세정보 조회", description = "")
    @GetMapping("{pilsaId}")
    public ResponseEntity<ResponsePilsaDetailDto> getPilsaDetail(
            @PathVariable Long pilsaId) {
        // Response - ResponsePilsaDetailDto
        // TODO: 서비스 기능 구현

        return ResponseEntity.ok(null);
    }


    @Operation(summary = "필사 등록", description = "")
    @PostMapping
    public ResponseEntity<?> createPilsaInfo(
            @Parameter(hidden = true) @Auth Long memberId,
            @RequestBody @Valid RequestPilsaInfoDto request) {
        // Request - RequestPilsaInfoDto
        // TODO: 서비스 기능 구현

        Pilsa pilsa = pilsaService.createPilsa(memberId, request);
        return ResponseEntity.ok(pilsa);
    }


    @Operation(summary = "필사 수정", description = "")
    @PutMapping("{pilsaId}")
    public ResponseEntity<?> updatePilsaInfo(
            @PathVariable Long pilsaId
            , @RequestBody @Valid RequestPilsaInfoDto request
    ) {
        // Request - RequestPilsaInfoDto
        // TODO: 서비스 기능 구현

        URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        return ResponseEntity.created(selfLink).build();
    }


    @Operation(summary = "필사 리스트 삭제", description = "")
    @DeleteMapping("{pilsaId}")
    public ResponseEntity<?> deletePilsaInfo(@PathVariable Long pilsaId) {
        // TODO: 서비스 기능 구현

        return ResponseEntity.noContent().build();
    }
}