package com.example.studentapp.model.dto;

public class StudentStatsDto {
    private final long totalStudents;
    private final double avgGpa;
    private final long excellentStudents;

    public StudentStatsDto(long totalStudents, double avgGpa, long excellentStudents) {
        this.totalStudents = totalStudents;
        this.avgGpa = avgGpa;
        this.excellentStudents = excellentStudents;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public double getAvgGpa() {
        return avgGpa;
    }

    public long getExcellentStudents() {
        return excellentStudents;
    }
}