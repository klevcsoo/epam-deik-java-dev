package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.screening.persistance.Screening;
import com.epam.training.ticketservice.core.screening.persistance.ScreeningId;
import com.epam.training.ticketservice.core.screening.persistance.ScreeningRepository;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {

    private static final int SCREENING_BREAK_LENGTH = 600; // seconds

    @Autowired
    private final ScreeningRepository screeningRepository;

    @Override
    public void set(Screening entity)
        throws ScreeningsOverlapException, ScreeningsBreakNotHonoredException {
        List<Screening> screeningsInTheSameRoom = screeningRepository.findAllById_Room(
            entity.getId().getRoom());

        List<Long> overlaps = screeningsInTheSameRoom.stream().map(screening -> {
            // noinspection CodeBlock2Expr
            return calcOverlapTime(screening.getId().getTime(),
                screening.getId().getMovie().getLength(), entity.getId().getTime(),
                entity.getId().getMovie().getLength());
        }).toList();

        for (Long o : overlaps) {
            if (o > 0) {
                if (o > SCREENING_BREAK_LENGTH) {
                    throw new ScreeningsOverlapException();
                } else {
                    throw new ScreeningsBreakNotHonoredException();
                }
            }
        }

        screeningRepository.save(entity);
    }

    @Override
    public void delete(ScreeningId screeningId) {
        screeningRepository.deleteById(screeningId);
    }

    @Override
    public Optional<Screening> get(ScreeningId screeningId) {
        return screeningRepository.findById(screeningId);
    }

    @Override
    public List<Screening> list() {
        return screeningRepository.findAll();
    }

    private long calcOverlapTime(LocalDateTime start1, int length1, LocalDateTime start2,
        int length2) {
        LocalDateTime end1 = start1.plusMinutes(length1);
        LocalDateTime end2 = start2.plusMinutes(length2);

        LocalDateTime latestStart = start1.isAfter(start2) ? start1 : start2;
        LocalDateTime earliestEnd = (end1.isBefore(end2) ? end1 : end2).plusMinutes(10);

        return
            Math.min(earliestEnd.toEpochSecond(ZoneOffset.UTC), end2.toEpochSecond(ZoneOffset.UTC))
                - Math.max(latestStart.toEpochSecond(ZoneOffset.UTC),
                start2.toEpochSecond(ZoneOffset.UTC));
    }
}
