package ru.tde.films;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import ru.tde.films.Domain.*;
import ru.tde.films.Repositories.Dto.ActorDto;
import ru.tde.films.Repositories.Dto.CommentDto;
import ru.tde.films.Repositories.Dto.DirectorDto;

import java.util.function.Function;

public class MapperConfiguration {
    public static ModelMapper getMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        modelMapper.addConverter(getConverter(MapperConfiguration::toComment));
        modelMapper.addConverter(getConverter(MapperConfiguration::toCommentDto));
        modelMapper.addConverter(getConverter(MapperConfiguration::toActor));
        modelMapper.addConverter(getConverter(MapperConfiguration::toActorDto));
        modelMapper.addConverter(getConverter(MapperConfiguration::toDirector));
        modelMapper.addConverter(getConverter(MapperConfiguration::toDirectorDto));

        return modelMapper;
    }

    private static CommentDto toCommentDto(Comment entity) {
        return CommentDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .score(entity.getScore())
                .text(entity.getText())
                .dateWritten(entity.getDateWritten())
                .build();
    }

    private static Comment toComment(CommentDto dto) {
        return Comment.builder()
                .id(dto.getId())
                .name(dto.getName())
                .score(dto.getScore())
                .text(dto.getText())
                .dateWritten(dto.getDateWritten())
                .build();
    }

    private static ActorDto toActorDto(Actor entity) {
        return ActorDto.builder()
                .id(entity.getId())
                .gender(entity.getGender())
                .country(entity.getCountry())
                .surname(entity.getSurname())
                .name(entity.getName())
                .patronymic(entity.getPatronymic())
                .dateOfBirth(entity.getDateOfBirth())
                .build();
    }

    private static Actor toActor(ActorDto dto) {
        return Actor.builder()
                .id(dto.getId())
                .gender(dto.getGender())
                .country(dto.getCountry())
                .surname(dto.getSurname())
                .name(dto.getName())
                .patronymic(dto.getPatronymic())
                .dateOfBirth(dto.getDateOfBirth())
                .build();
    }

    private static DirectorDto toDirectorDto(Director entity) {
        return DirectorDto.builder()
                .id(entity.getId())
                .gender(entity.getGender())
                .country(entity.getCountry())
                .surname(entity.getSurname())
                .name(entity.getName())
                .patronymic(entity.getPatronymic())
                .dateOfBirth(entity.getDateOfBirth())
                .dignity(entity.getDignity())
                .build();
    }

    private static Director toDirector(DirectorDto dto) {
        return Director.builder()
                .id(dto.getId())
                .gender(dto.getGender())
                .country(dto.getCountry())
                .surname(dto.getSurname())
                .name(dto.getName())
                .patronymic(dto.getPatronymic())
                .dateOfBirth(dto.getDateOfBirth())
                .dignity(dto.getDignity())
                .build();
    }

    private static <S, D> AbstractConverter<S, D> getConverter(Function<S, D> func) {
        return new AbstractConverter<S, D>() {
            @Override
            protected D convert(S source) {
                return func.apply(source);
            }
        };
    }
}