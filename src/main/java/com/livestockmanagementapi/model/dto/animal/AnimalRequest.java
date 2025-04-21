package com.livestockmanagementapi.model.dto.animal;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AnimalRequest {
    @NotBlank(message = "Tên không được để trống")
    @Size(min = 2, max = 100, message = "Tên phải từ 2 đến 100 ký tự")
    private String name;

    @NotNull(message = "Ngày nhập không được để trống")
    @PastOrPresent(message = "Ngày nhập phải là ngày trong quá khứ hoặc hiện tại")
    private LocalDate entryDate;

    @PastOrPresent(message = "Ngày xuất phải là ngày trong quá khứ hoặc hiện tại")
    private LocalDate exitDate;

    @NotBlank(message = "Trạng thái không được để trống")
    @Pattern(regexp = "^(ACTIVE|SICK|UNVACCINATED)$", message = "Trạng thái không hợp lệ")
    private String status;

    @NotNull(message = "Cân nặng không được để trống")
    @DecimalMin(value = "0.1", message = "Cân nặng phải lớn hơn 0")
    @DecimalMax(value = "1000", message = "Cân nặng không được vượt quá 1000")
    private BigDecimal weight;

    @NotNull(message = "Chuồng nuôi không được để trống")
    private Long penId;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn hoặc bằng 1")
    @Max(value = 1000, message = "Số lượng không được vượt quá 1000")
    private Integer quantity;
}