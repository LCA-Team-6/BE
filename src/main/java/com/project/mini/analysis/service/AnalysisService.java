package com.project.mini.analysis.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.mini.analysis.dto.*;
import com.project.mini.analysis.entity.Analysis;
import com.project.mini.analysis.entity.AnalysisTag;
import com.project.mini.analysis.repository.AnalysisRepository;
import com.project.mini.analysis.repository.AnalysisTagRepository;
import com.project.mini.code.repository.ContentRepository;
import com.project.mini.code.repository.PersonalityRepository;
import com.project.mini.code.repository.StyleRepository;
import com.project.mini.code.repository.ToneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalysisService {
    private final ContentRepository contentRepository;
    private final PersonalityRepository personalityRepository;
    private final StyleRepository styleRepository;
    private final ToneRepository toneRepository;
    private final AnalysisRepository analysisRepository;
    private final AnalysisTagRepository analysisTagRepository;

    @Value("${openai.api.url}")
    private String API_URL;

    @Value("${openai.api.key}")
    private String API_KEY;

    public JsonNode getAnalysis (AnalysisRequestDto analysisRequestDto) {
        RestTemplate restTemplate = new RestTemplate();

        String preset = String.format("당신의 말투는 %s 이고 성격은 %s 이며 답변 스타일은 %s 인 역할을 합니다."
                , toneRepository.findNameByToneId(analysisRequestDto.getTone())
                , personalityRepository.findNameByPersonalityId(analysisRequestDto.getPersonality())
                , styleRepository.findNameByStyleId(analysisRequestDto.getStyle())
        );

        /*String prompt = String.format("""
            사용자가 작성한 일기를 기반으로 아래 다섯 가지 정보를 분석해 주세요.

            반드시 아래 JSON 포맷에 맞춰 출력해 주세요.
            감정은 고정된 10개 목록 각각 **전부**에 대해 다음 세 가지 정보를 포함해 주세요:
            - 감정 이름 (name)
            - 코드 번호 (code)
            - 점수 (score: 0~10 사이의 정수)

            [감정 코드 목록]

            (코드: 1~10 / 감정명: 기쁨, 슬픔, 분노, 불안, 놀람, 사랑, 희망, 자신감, 피곤, 감사)

            또한 사용자로부터 추천받고 싶은 콘텐츠 종류는 다음과 같습니다:
            - %s

            지정된 종류만 추천 콘텐츠로 포함해 주세요. 영상 등 다른 콘텐츠는 제외합니다.
            콘텐츠는 사용자가 원하는 종류 중 **정확히 3개**를 추천해 주세요.

            [출력 JSON 포맷 예시]

            {
            "emotions": [
            {
                "code": 2,
                "name": "슬픔",
                "score": 9
            },
            {
                "code": 4,
                "name": "불안",
                "score": 7
            }
            ],
            "summary": "오늘 하루는 전반적으로 무기력하고 우울한 분위기였습니다. 상사의 질책으로 인해 자존감이 많이 떨어졌습니다.",
            "feedback": "하루 종일 많이 힘드셨을 것 같아요. 자신을 탓하지 마시고 오늘은 충분히 쉬어 주세요. 내일은 조금 더 나은 날이 될 거예요.",
            "recommendations": [
                {
                    "type": "music",
                    "title": "이수 - 사랑이라는 이유로",
                    "description": "슬픔을 조용히 감싸주는 발라드로 위로를 줄 수 있는 곡입니다."
                },
                {
                    "type": "quote",
                    "title": "당신의 가치는 오늘의 실패나 실수로 절대 줄어들지 않아요.",
                    "description": "스스로를 위로할 수 있는 짧은 문장입니다."
                }
              ]
            }

            다음은 분석할 일기입니다:

            \"""
            %s
            \"""
            """
                , contentRepository.findNameByContentId(analysisRequestDto.getContent())
                , analysisRequestDto.getMemo());*/

        String prompt = String.format("""
            사용자가 작성한 일기를 기반으로 아래 두 가지 정보를 분석해 주세요.

            반드시 아래 JSON 포맷에 맞춰 출력해 주세요. 
            감정은 고정된 10개 목록 각각 **전부**에 대해 다음 세 가지 정보를 포함해 주세요:
            - 감정 이름 (name)
            - 코드 번호 (code)
            - 점수 (score: 0~10 사이의 정수)
            
            [감정 코드 목록]
            
            (코드: 1~10 / 감정명: 기쁨, 슬픔, 분노, 불안, 놀람, 사랑, 희망, 자신감, 피곤, 감사)
            
            또한 사용자로부터 추천받고 싶은 콘텐츠 종류는 다음과 같습니다:
            - %s
            
            summary, feedback, 추천 콘텐츠 내용을 요약하거나 줄이지 말고, \s
            analysis 필드 하나에 모두 자세하고 정중한 문장 형태로 포함해 주세요. \s
            - 각 영역을 명확히 나눠서 자연어 텍스트로 설명해 주세요 (예: "요약: ...", "피드백: ...", "추천 콘텐츠: ...")
            
            지정된 종류만 추천 콘텐츠로 포함해 주세요. 영상 등 다른 콘텐츠는 제외합니다.
            콘텐츠는 사용자가 원하는 종류 중 **정확히 3개**를 추천해 주세요.
            
            콘텐츠는 `analysis`에 자연스럽게 녹여 설명만 포함하고, JSON 배열로는 따로 나누지 마세요.

            [출력 JSON 포맷 예시]

            {
                "emotions": [
                    { "code": 2, "name": "슬픔", "score": 9 },
                    { "code": 4, "name": "불안", "score": 7 }
                ],
                "analysis": "요약: 사용자는 오늘 하루 동안 전반적으로 무기력하고 자존감이 낮아졌다는 느낌을 강하게 받았습니다. 상사의 질책으로 인해 감정적 위축을 경험했고, 퇴근 이후에도 아무것도 하기 싫은 무력감에 빠졌습니다.\\n\\n피드백: 지금 느끼는 감정은 결코 이상한 것이 아닙니다. 자신을 비난하지 마세요. 이처럼 힘든 하루를 견뎌낸 자신에게 진심으로 격려의 말을 건넬 필요가 있습니다. 당신은 잘하고 있어요.\\n\\n추천 콘텐츠: 오늘의 감정을 위로하기 위해 이수의 '사랑이라는 이유로'라는 잔잔한 발라드 곡을 추천드립니다. 또한 '당신의 가치는 오늘의 실수로 줄어들지 않아요.'라는 글귀는 낮아진 자존감을 회복하는 데 도움이 될 수 있습니다. 마지막으로 지친 하루를 마무리하며 들을 수 있는 자연 명상 영상을 통해 마음을 가라앉히고 진정시킬 수 있기를 바랍니다."
            }

            다음은 분석할 일기입니다:

            \"""
            %s
            \"""
            """
                , contentRepository.findNameByContentId(analysisRequestDto.getContent())
                , analysisRequestDto.getMemo());

        OpenAiRequest request = new OpenAiRequest();
        request.setModel("gpt-4o");
        request.setTemperature(0.7);
        request.setMax_tokens(800);
        request.setMessages(List.of(
                Map.of("role", "system", "content", preset),
                Map.of("role", "user", "content", prompt)));

        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.set("Content-Type", "application/json");
        HttpEntity<OpenAiRequest> entity = new org.springframework.http.HttpEntity<>(request, headers);

        OpenAiResponse response = restTemplate.postForObject(API_URL, entity,
                OpenAiResponse.class);

        String content = Objects.requireNonNull(response).getChoices().get(0).getMessage().getContent().replace("```json", "").replace("```", "");

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readTree(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void save(AnalysisSaveDto analysisSaveDto) {
        Analysis analysis = Analysis.builder()
                .memoId(analysisSaveDto.getMemoId())
                .presetPromptId(analysisSaveDto.getPresetPromptId())
                .analysis(analysisSaveDto.getAnalysis())
                .createdAt(LocalDateTime.now())
                .build();

        analysisRepository.save(analysis);

        List<AnalysisTag> analysisTags = analysisSaveDto.getEmotions().stream().map(emotion ->
                AnalysisTag.builder()
                .analysisId(analysis.getAnalysisId())
                .tagId(emotion.getCode())
                .score(emotion.getScore())
                .build()).toList();

        analysisTagRepository.saveAll(analysisTags);
    }
}
