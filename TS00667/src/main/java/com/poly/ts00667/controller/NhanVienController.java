package com.poly.ts00667.controller;

import com.poly.ts00667.dao.NhanVienDAO;
import com.poly.ts00667.entity.NhanVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NhanVienController {

    @Autowired
    NhanVienDAO dao;

    // 1. Trang chủ (Load danh sách + Form trống)
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("item", new NhanVien()); // Form trống
        model.addAttribute("items", dao.findAll()); // Danh sách bảng
        return "index";
    }

    // 2. Chức năng Edit (Đổ dữ liệu lên form)
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") String id) {
        NhanVien nv = dao.findById(id);
        model.addAttribute("item", nv);
        model.addAttribute("items", dao.findAll());
        return "index";
    }

    // 3. Chức năng Thêm (Create)
    @PostMapping("/create")
    public String create(NhanVien nv) {
        dao.save(nv);
        return "redirect:/";
    }

    // 4. Chức năng Cập nhật (Update)
    @PostMapping("/update")
    public String update(NhanVien nv) {
        dao.save(nv); // Firebase tự đè dữ liệu cũ nếu trùng mã
        return "redirect:/";
    }

    // 5. Chức năng Xóa (Delete)
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        dao.delete(id);
        return "redirect:/";
    }
}