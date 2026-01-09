package com.library.controller;

import com.library.dto.DashboardData;
import com.library.dto.OrderSumDTO;
import com.library.repository.OrderEntryRepository;
import com.library.repository.OrderRepository;
import com.library.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class DashboardController
{
    @Autowired
    private OrderEntryRepository orderEntryRepository;

    @Autowired
    private OrderRepository orderRepository;

    Date dateToString(String d) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(d);
    }
    @GetMapping({"/dashboard/{date}", "/dashboard"})
    public String getDashboard(@PathVariable(name = "date", required = false) String date, Model model) throws ParseException {

        Date start;
        Date end;
        Date tmp;
        if(date != null)
        {
            tmp = dateToString(date);
        }
        else
        {
            tmp = new Date();
        }

        start = DateUtil.getStartOfDate(tmp);
        end = DateUtil.getEndOfDate(tmp);
        List<DashboardData> dashboardData = orderEntryRepository.fetchDashboardDataLeftJoin(start, end);
        for (DashboardData dashboardDatum : dashboardData) {
            System.out.println(dashboardDatum.getDisplayName() +" "+ dashboardDatum.getTotalPrice());
        }

        OrderSumDTO dto = orderRepository.fetchOrderSum(start, end);
        OrderSumDTO cancelled = orderRepository.fetchCancelledSum(start, end);
        model.addAttribute("sum", dto.getSum());
        model.addAttribute("cancelledTotal", cancelled.getSum());
        model.addAttribute("datas", dashboardData);

        return "dashboard";
    }



}
