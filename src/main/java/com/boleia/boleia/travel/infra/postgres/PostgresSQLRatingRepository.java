package com.boleia.boleia.travel.infra.postgres;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.jpa.entity.RatingModel;
import com.boleia.boleia.shared.jpa.entity.RatingModelJpa;
import com.boleia.boleia.shared.jpa.entity.UserModel;
import com.boleia.boleia.shared.jpa.entity.UserModelJpa;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.travel.domain.Rating;
import com.boleia.boleia.travel.domain.RatingRepository;
import com.boleia.boleia.travel.domain.user.EntityType;
import com.boleia.boleia.travel.domain.user.UserNotFoundError;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostgresSQLRatingRepository implements RatingRepository {
    private final RatingModelJpa jpa;
    private final UserModelJpa userJpa;
    
    @Override
    public Result<Void, DomainError> save(Rating rating) {
        try {
            this.jpa.save(this.toModel(rating));
            return Result.ok(null);
        } catch (Exception e) {
            var msg = "Erro ao avaliar o usuario";
            log.error(msg, e);
            return Result.error(new DomainError(msg));
        }
    }

    @Override
    public Result<Rating, UserNotFoundError> findByUserId(UUID id) {
        var model = this.jpa.findByUserId(id.toString());
        return model.isPresent()
            ? Result.ok(this.toRatingFactory(model.get()))
            : Result.error(new UserNotFoundError());
    }

    private RatingModel toModel(Rating rating) {
        var model = rating.getId() != null ? this.jpa.findById(rating.getId().toString()).orElse(new RatingModel()) : new RatingModel();

        var userModel = this.toUserModelFactory(rating.getUserId());

        model.setId(rating.getId().toString());
        model.setRating(rating.getRating());
        model.setUser(userModel);
        model.setEntityType(rating.getEntityType().getValue());

        return model;
    }

    private Rating toRatingFactory(RatingModel model) {
        return Rating.from(
            UUID.fromString(model.getId()),
            UUID.fromString(model.getUser().getId()),
            model.getRating(),
            EntityType.fromValue(model.getEntityType())
        );
    }

    private UserModel toUserModelFactory(UUID id){
        return this.userJpa.getReferenceById(id.toString());
    }

}
