package com.interview.roja.util;

import java.util.HashMap;
import java.util.Map;

public class EmployeeRole {
    private static final Map<Short, String> employeeRoleMap = new HashMap<>();

    static {
        employeeRoleMap.put((short) 1, "DOCTOR");
        employeeRoleMap.put((short) 2, "NURSE");
        employeeRoleMap.put((short) 3, "JANITOR");
    }

    public static Map<Short, String> getEmployeeRoleMap() {
        return employeeRoleMap;
    }
}
