package ru.gb.gbthymeleafwinter.entity;

import lombok.Getter;
import lombok.Setter;
import ru.gb.gbthymeleafwinter.entity.enums.Status;

import java.time.LocalDate;

@Setter
@Getter
public abstract class AbstractEntity<T> {
    protected Long id;

    protected Status status;

    protected int version;
    protected String createdBy;
    protected LocalDate createdDate;
    protected String lastModifiedBy;
    protected LocalDate lastModifiedDate;

    public abstract T createBuilder();
}
