package ru.gb.gbthymeleafwinter.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.gb.gbthymeleafwinter.entity.AbstractEntity;
import ru.gb.gbthymeleafwinter.entity.enums.Status;

import java.util.List;

@NoRepositoryBean
public interface AbstractDao<T extends AbstractEntity<T>,ID> extends JpaRepository<T, ID> {

    List<T> findAllByStatus(Status status, PageRequest pageRequest);

    List<T> findAllByStatus(Status status);

    List<T> findAllByStatus(Status active, Sort by);
}
