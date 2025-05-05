package com.livestockmanagementapi.service.history;

import com.livestockmanagementapi.model.Animal;
import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.model.FeedHistory;
import com.livestockmanagementapi.model.FeedPlan;
import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.model.dto.feedhistory.FeedHistoryDTO;
import com.livestockmanagementapi.model.dto.feedhistory.FeedHistoryRequest;
import com.livestockmanagementapi.repository.AnimalRepository;
import com.livestockmanagementapi.repository.EmployeeRepository;
import com.livestockmanagementapi.repository.FeedHistoryRepository;
import com.livestockmanagementapi.repository.FeedPlanRepository;
import com.livestockmanagementapi.repository.PigPenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedHistoryServiceImpl implements FeedHistoryService {
    private final FeedHistoryRepository feedHistoryRepository;
    private final PigPenRepository pigPenRepository;
    private final AnimalRepository animalRepository;
    private final FeedPlanRepository feedPlanRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void deleteById(Long id) {
        feedHistoryRepository.deleteById(id);
    }

    @Override
    public void save(FeedHistory feedHistory) {
        feedHistoryRepository.save(feedHistory);
    }

    @Override
    public Optional<FeedHistory> findById(Long id) {
        return feedHistoryRepository.findById(id);
    }

    @Override
    public FeedHistory findById(String id) {
        try {
            Long longId = Long.parseLong(id);
            return feedHistoryRepository.findById(longId)
                    .orElseThrow(() -> new RuntimeException("Feed History not found with id: " + id));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid feed history ID format: " + id);
        }
    }

    @Override
    public List<FeedHistory> findAll() {
        return feedHistoryRepository.findAll();
    }

    @Override
    public FeedHistoryDTO createFeedHistory(FeedHistoryRequest request) {
        log.info("Creating feed history with request: {}", request);
        try {
            // Validate request
            if (request.getPigPenId() == null) {
                throw new IllegalArgumentException("PigPen ID is required");
            }
            if (request.getFeedPlanId() == null) {
                throw new IllegalArgumentException("FeedPlan ID is required");
            }
            if (request.getFeedingTime() == null) {
                throw new IllegalArgumentException("Feeding time is required");
            }
            if (request.getDailyFood() == null || request.getDailyFood() <= 0) {
                throw new IllegalArgumentException("Daily food amount must be positive");
            }
            if (request.getCreatedById() == null) {
                throw new IllegalArgumentException("Employee ID is required");
            }

            FeedHistory feedHistory = new FeedHistory();
            
            // Set PigPen
            log.debug("Finding pig pen with id: {}", request.getPigPenId());
            PigPen pigPen = pigPenRepository.findById(request.getPigPenId())
                    .orElseThrow(() -> new RuntimeException("PigPen not found with id: " + request.getPigPenId()));
            feedHistory.setPigPen(pigPen);
            
            // Set Animal if provided
            if (request.getAnimalId() != null) {
                log.debug("Finding animal with id: {}", request.getAnimalId());
                Animal animal = animalRepository.findById(request.getAnimalId())
                        .orElseThrow(() -> new RuntimeException("Animal not found with id: " + request.getAnimalId()));
                feedHistory.setAnimal(animal);
            }
            
            // Set FeedPlan
            log.debug("Finding feed plan with id: {}", request.getFeedPlanId());
            FeedPlan feedPlan = feedPlanRepository.findById(request.getFeedPlanId())
                    .orElseThrow(() -> new RuntimeException("FeedPlan not found with id: " + request.getFeedPlanId()));
            feedHistory.setFeedPlan(feedPlan);
            
            // Set Employee who created the record - handle as String ID
            log.debug("Finding employee with id: {}", request.getCreatedById());
            Employee employee = employeeRepository.findById(String.valueOf(request.getCreatedById()))
                    .orElseThrow(() -> new RuntimeException("Employee not found with id: " + request.getCreatedById()));
            feedHistory.setCreatedBy(employee);
            
            feedHistory.setFeedingTime(request.getFeedingTime());
            feedHistory.setDailyFood(request.getDailyFood());
            
            log.debug("Saving feed history: {}", feedHistory);
            FeedHistory savedHistory = feedHistoryRepository.save(feedHistory);
            log.info("Successfully saved feed history with id: {}", savedHistory.getId());
            
            return convertToDTO(savedHistory);
        } catch (Exception e) {
            log.error("Error creating feed history: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating feed history: " + e.getMessage(), e);
        }
    }

    @Override
    public List<FeedHistoryDTO> getAllFeedHistories() {
        List<FeedHistory> histories = feedHistoryRepository.findAll();
        return histories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedHistoryDTO> getFeedHistoriesByPigPenId(Long pigPenId) {
        List<FeedHistory> histories = feedHistoryRepository.findByPigPenId(pigPenId);
        return histories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private FeedHistoryDTO convertToDTO(FeedHistory history) {
        FeedHistoryDTO dto = new FeedHistoryDTO();
        dto.setId(history.getId());
        dto.setPigPenName(history.getPigPen() != null ? history.getPigPen().getName() : null);
        dto.setAnimalName(history.getAnimal() != null ? history.getAnimal().getName() : null);
        dto.setFeedType(history.getFeedPlan() != null ? history.getFeedPlan().getFeedType() : null);
        dto.setFeedingTime(history.getFeedingTime());
        dto.setDailyFood(history.getDailyFood());
        dto.setCreatedByName(history.getCreatedBy() != null ? history.getCreatedBy().getFullName() : null);
        return dto;
    }
} 