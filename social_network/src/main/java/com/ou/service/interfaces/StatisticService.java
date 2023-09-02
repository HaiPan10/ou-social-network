package com.ou.service.interfaces;

import java.util.List;
import java.util.Map;

public interface StatisticService {
    List<Object[]> StatisticsNumberOfUsers(Map<String, String> params) throws Exception;
}
