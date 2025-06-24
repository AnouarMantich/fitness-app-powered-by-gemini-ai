package org.app.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.aiservice.model.Activity;
import org.app.aiservice.model.Recommendation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityAIService {

    private final GeminiService geminiService;

    public Recommendation generateRecommendation(Activity activity) {

        log.info("Fetching recommendation from AI ... ");
        String prompt=createPromptForActivity(activity);
        String response=geminiService.getAnswer(prompt);
        log.info("Response from AI : "+response);
        Recommendation recommendation = processAIResponse(response, activity);
        return recommendation;
    }

    public Recommendation processAIResponse(String response, Activity activity) {

        try {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            JsonNode textNode= rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");

            String jsonContent= textNode.asText()
                    .replaceAll("```json\\n","")
                    .replaceAll("\\n```","")
                            .trim();

            log.info("PARSED RESPONSE FROM AI : "+jsonContent);

            JsonNode jsonAnalysis= mapper.readTree(jsonContent);
            JsonNode nodeAnalysis= jsonAnalysis.path("analysis");
            StringBuilder fullAnalysis= new StringBuilder();
            addAnalysisSection(fullAnalysis,nodeAnalysis,"overall","Overall:");
            addAnalysisSection(fullAnalysis,nodeAnalysis,"pace","Pace:");
            addAnalysisSection(fullAnalysis,nodeAnalysis,"heartRate","heart rate:");
            addAnalysisSection(fullAnalysis,nodeAnalysis,"caloriesBurned","Calories:");


            List<String> improvements = extractImprovements(jsonAnalysis.path("improvements"));
            List<String> suggestions = extractSuggestions(jsonAnalysis.path("suggestions"));
            List<String> safetyMeasures = extractSafetyMeasures(jsonAnalysis.path("safety"));

            return Recommendation.builder()
                    .improvements(improvements)
                    .suggestions(suggestions)
                    .safetySuggestions(safetyMeasures)
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .activityType(String.valueOf(activity.getType()))
                    .caloriesBurned(activity.getCaloriesBurned())
                    .duration(activity.getDuration())
                    .recommendation(fullAnalysis.toString().trim())
                    .created(LocalDateTime.now())
                    .build();

        }catch (Exception e) {
            e.printStackTrace();
           return createDefaultRecommendations(activity);
        }

    }

    private Recommendation createDefaultRecommendations(Activity activity) {

        return Recommendation.builder()
                .improvements(Collections.singletonList("Failed to generate detailed improvements"))
                .suggestions(Collections.singletonList("Continue with your current routine"))
                .safetySuggestions(Arrays.asList(
                        "always warm up before workout",
                        "stay hydrated",
                        "listen to your body"
                ))
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .activityType(String.valueOf(activity.getType()))
                .recommendation("Consider consulting a professional trainer ")
                .created(LocalDateTime.now())
                .build();

    }

    private List<String> extractImprovements(JsonNode improvementsNode) {
        List<String> improvements = new ArrayList<>();
        if (improvementsNode.isArray()) {
            improvementsNode.forEach(improvement ->{
                String area=improvement.get("area").asText();
                String detail=improvement.get("recommendation").asText();
                improvements.add(String.format("%s: %s",area,detail));
            });
        }
        return improvements.isEmpty() ?
                Collections.singletonList("No specific improvements has been provided !") :
                improvements;

    }

    private List<String> extractSuggestions(JsonNode suggestionsNode) {
        List<String> suggestions = new ArrayList<>();
        if (suggestionsNode.isArray()) {
            suggestionsNode.forEach(suggestion ->{
                String workout=suggestion.get("workout").asText();
                String description=suggestion.get("description").asText();
                suggestions.add(String.format("%s: %s",workout,description));
            });
        }
        return suggestions.isEmpty() ?
                Collections.singletonList("No specific suggestions has been provided !") :
                suggestions;

    }

    private List<String> extractSafetyMeasures(JsonNode safetyNode) {
        List<String> safetyMeasures = new ArrayList<>();
        if (safetyNode.isArray()) {
            safetyNode.forEach(SafetyMeasure ->{
                safetyMeasures.add(SafetyMeasure.asText());
            });
        }
        return safetyMeasures.isEmpty() ?
                Collections.singletonList("Follow general safety measures !") :
                safetyMeasures;

    }


    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode nodeAnalysis, String key, String prefix) {
        if(!nodeAnalysis.path(key).isMissingNode()) {
            fullAnalysis.append(prefix)
                    .append(nodeAnalysis.path(key).asText())
                    .append("\n\n");
        }
    }

    private String createPromptForActivity(Activity activity) {
        return String.format(""" 
                  Analyze this fitness activity and provide detailed recommendations in the following format
                  {
                      "analysis" : {
                          "overall": "Overall analysis here",
                          "pace": "Pace analysis here",
                          "heartRate": "Heart rate analysis here",
                          "CaloriesBurned": "Calories Burned here"
                      },
                      "improvements": [
                          {
                              "area": "Area name",
                              "recommendation": "Detailed Recommendation"
                          }
                      ],
                      "suggestions" : [
                          {
                              "workout": "Workout name",
                              "description": "Detailed workout description"
                          }
                      ],
                      "safety": [
                          "Safety point 1",
                          "Safety point 2"
                      ]
                  }
                
                  Analyze this activity:
                  Activity Type: %s
                  Duration: %d minutes
                  calories Burned: %d
                  Additional Metrics: %s
        Make sure the output strictly follows the JSON format shown above with correct keys and structure.
""", activity.getType()
                        ,activity.getDuration()
                    ,activity.getCaloriesBurned()
                    ,activity.getAdditionalMetrics()
        );
    }

}
