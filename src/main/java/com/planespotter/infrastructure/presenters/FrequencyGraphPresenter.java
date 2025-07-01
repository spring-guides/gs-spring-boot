package com.planespotter.infrastructure.presenters;

import com.planespotter.infrastructure.presenters.dto.FrequencyDataPointDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FrequencyGraphPresenter {

    public List<FrequencyDataPointDto> toDtoList(Map<String, Long> frequencyMap) {
        return frequencyMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new FrequencyDataPointDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
