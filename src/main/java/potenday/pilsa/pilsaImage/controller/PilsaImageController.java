package potenday.pilsa.pilsaImage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import potenday.pilsa.pilsaContents.service.PilsaContentService;
import potenday.pilsa.pilsaImage.service.PilsaImageService;

@Tag(name = "필사 이미지 Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pilsa/image")
public class PilsaImageController {

    private final PilsaImageService pilsaImageService;


    @Operation(summary = "내가 등록한 필사 이미지 리스트 조회", description = "")
    @GetMapping
    public ResponseEntity<?> getPilsaImageList() {
        // TODO: 회원 정보 받아오기

        return ResponseEntity.ok(null);
    }

}