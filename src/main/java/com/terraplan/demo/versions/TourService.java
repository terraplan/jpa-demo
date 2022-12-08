package com.terraplan.demo.versions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Transactional
public class TourService {

    private final TourRepository tourRepository;

    public Tour createNewTour() {
        Tour tour = new Tour();
        tour.setName("New Tour - " + LocalDateTime.now());
        return tourRepository.save(tour);
    }

    public Tour updateStudents(Tour tour) {
        return tourRepository.save(tour);
    }

    public Tour loadTour(Long id) {
        return tourRepository.findOneWithStudentsById(id).orElseThrow();
    }
}
