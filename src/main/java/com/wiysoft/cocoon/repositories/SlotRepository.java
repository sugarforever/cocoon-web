package com.wiysoft.cocoon.repositories;

import com.wiysoft.cocoon.core.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by weiliyang on 7/24/15.
 */
@Transactional
@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {

    @Query("select count(id) from Slot s where s.code = ?1 and s.date >= ?2 and  s.date < ?3")
    long countByCodeAndDatesBetween(String code, Date startDate, Date endDate);

    @Query("select max(s.date) from Slot s where s.code = ?1")
    Date findMaxDateByCode(String code);

    @Query("select DATE_FORMAT(s.date, '%Y-%m-%d'), SUM(s.netValue) from Slot s where s.code = ?1 group by DATE_FORMAT(s.date, '%Y-%m-%d')")
    Object[] findAllDateAndNetValueByCode(String code);
}
