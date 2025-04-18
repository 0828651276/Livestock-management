package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name= "pig_pen")
public class PigPen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long penId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee caretaker; // Giữ lại để đảm bảo tính tương thích ngược

    @ManyToMany
    @JoinTable(
            name = "pig_pen_caretakers",
            joinColumns = @JoinColumn(name = "pen_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> caretakers = new HashSet<>();

    private String name;
    private LocalDate createdDate;
    private LocalDate closedDate;
    private int quantity;

    // Các helper methods để quản lý caretakers
    public void addCaretaker(Employee employee) {
        this.caretakers.add(employee);
    }

    public void removeCaretaker(Employee employee) {
        this.caretakers.remove(employee);
    }
}