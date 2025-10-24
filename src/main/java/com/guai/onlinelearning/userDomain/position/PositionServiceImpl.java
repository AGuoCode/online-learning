package com.guai.onlinelearning.userDomain.position;

import com.guai.onlinelearning.common.PaginationResponse;
import com.guai.onlinelearning.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements IPositionService {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    @Override
    public Integer create(PositionRequest positionRequest) {
        Position position = positionMapper.toPosition(positionRequest);
        return positionRepository.save(position).getId();
    }

    @Override
    public Integer update(Integer id, PositionRequest positionRequest) {
        if (!existsById(id)) {
            throw new EntityNotFoundException(Position.class, "ID", id);
        }

        Position position = positionMapper.mergeForUpdatePosition(id, positionRequest);
        return positionRepository.save(position).getId();
    }

    @Override
    public PositionResponse findById(Integer id) {
        return positionRepository.findById(id)
                .map(positionMapper::toPositionResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException(Position.class, "ID", id)
                );
    }

    @Override
    public void delete(Integer id) {
        if (!existsById(id)) {
            throw new EntityNotFoundException(Position.class, "ID", id);
        }
        positionRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return positionRepository.existsById(id);
    }

    @Override
    public PaginationResponse<PositionResponse> findAll(Pageable pageable) {
        Page<Position> positionPage = positionRepository.findAll(pageable);
        List<PositionResponse> resList = positionPage.stream()
                .map(positionMapper::toPositionResponse)
                .toList();
        return new PaginationResponse<>(
                positionPage.getNumber(),
                positionPage.getSize(),
                positionPage.getTotalPages(),
                positionPage.getTotalElements(),
                resList,
                positionPage.isFirst(),
                positionPage.isLast(),
                positionPage.isEmpty()
        );
    }

    @Override
    public Optional<PositionResponse> getPositionByName(String name) {
        return positionRepository.findByName(name).map(positionMapper::toPositionResponse);
    }
}
