package org.quanly.dto;

public class CategoryDTO {
    private int id;
    private String name;

    public CategoryDTO(int id, String name) { this.id = id; this.name = name; }
    public int getId() { return id; }
    public String getName() { return name; }

    // Rất quan trọng cho JComboBox
    @Override
    public String toString() { return name; }
}
