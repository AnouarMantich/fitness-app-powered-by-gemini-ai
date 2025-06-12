package org.app.activityservice.service.ActivityService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.activityservice.config.RabbitMQConfig;
import org.app.activityservice.dto.ActivityRequest;
import org.app.activityservice.dto.ActivityResponse;
import org.app.activityservice.mapper.ActivityMapper;
import org.app.activityservice.model.Activity;
import org.app.activityservice.repository.ActivityRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;

//    @Value("${spring.rabbitmq.template.exchange.name}")
//    private String exchange;
//    @Value("${spring.rabbitmq.template.routing-key}")
//    private String routingKey;


    public ActivityResponse trackActivity(ActivityRequest request) {

        boolean validateUser = userValidationService.validateUser(request.getUserId());
        if (!validateUser) {
            throw new RuntimeException("Invalid user"+request.getUserId());
        }

        Activity activity= activityMapper.mapToActivity(request);
        Activity saved = activityRepository.save(activity);

        try{
            rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, activity);
        }catch (Exception e){
            log.error("failed to publish activity to RabbitMQ service : "+e.getMessage());
        }

        return activityMapper.mapToActivityResponse(saved);
    }

    public List<ActivityResponse> getUserActivities(String userId) {


        return activityRepository.findByUserId(userId).stream()
                .map(activityMapper::mapToActivityResponse).collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String id) {
        return activityMapper.mapToActivityResponse(activityRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Activity with id "+ id +" not found")
        ));
    }
}
