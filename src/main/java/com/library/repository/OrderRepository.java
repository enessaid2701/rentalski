package com.library.repository;

import com.library.dto.DashboardData;
import com.library.dto.OrderSumDTO;
import com.library.entity.CustomerEntity;
import com.library.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository  extends CrudRepository<Order, Long>
{
    List<Order> findAllByCustomer(CustomerEntity customer);

    @Query(value = "SELECT new com.library.dto.OrderSumDTO(SUM(o.totalPrice)) " +
        "FROM Order as o "+
        "where o.createdAt>:dateStart and o.createdAt<:dateEnd and (o.orderStatus='OK' or o.orderStatus is null ) ")
    OrderSumDTO fetchOrderSum(@Param("dateStart") Date dateStart, @Param("dateEnd") Date dateEnd);


    @Query(value = "SELECT new com.library.dto.OrderSumDTO(SUM(o.totalPrice)) " +
        "FROM Order as o "+
        "where o.createdAt>:dateStart and o.createdAt<:dateEnd and o.orderStatus='CANCELLED' ")
    OrderSumDTO fetchCancelledSum(@Param("dateStart") Date dateStart, @Param("dateEnd") Date dateEnd);
}
