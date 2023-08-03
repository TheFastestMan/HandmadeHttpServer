package ru.rail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ConvertToObject {

    public List<Employee> convert(String json) throws JsonProcessingException {
        SalaryInfo salaryInfo;
        ObjectMapper objectMapper = new ObjectMapper();
        salaryInfo = objectMapper.readValue(json, SalaryInfo.class);
        List<Employee> employeeList = salaryInfo.getEmployees();

        return employeeList;
    }
}
