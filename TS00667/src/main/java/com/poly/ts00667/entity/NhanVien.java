package com.poly.ts00667.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanVien {
    private String maNV;
    private String hoTen;
    private String diaChi;
    private Boolean gioiTinh; // true: Nam, false: Nữ
}