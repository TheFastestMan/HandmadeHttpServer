package ru.rail;

import java.util.List;

public class SalaryInfo {
    private String info;
    private List<Employee> employees;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "SalaryInfo{" +
                "info='" + info + '\'' +
                ", employees=" + employees +
                '}';
    }
}
