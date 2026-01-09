package com.library.repository;

import com.library.dto.DashboardData;
import com.library.entity.Order;
import com.library.entity.OrderEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderEntryRepository extends CrudRepository<OrderEntry, Long>
{
    List<OrderEntry> findAllByOrder(Order order);

    @Query(value = "SELECT new com.library.dto.DashboardData(p.displayName, SUM(entries.price)) " +
        "FROM OrderEntry as entries left join ProductEntity as p on p.id = entries.product left join Order as o on entries.order=o.id " +
        "where entries.createdAt>:dateStart and entries.createdAt<:dateEnd and (o.orderStatus='OK' or o.orderStatus is null ) " +
        "Group By entries.product ")
    List<DashboardData> fetchDashboardDataLeftJoin(@Param("dateStart") Date dateStart, @Param("dateEnd") Date dateEnd);
}
