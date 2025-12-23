package com.poly.ts00667.dao;

import com.google.firebase.database.*;
import com.poly.ts00667.entity.NhanVien;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Repository
public class NhanVienDAO {

    // Khai báo kết nối đến nhánh "nhanvien"
    private DatabaseReference getRef() {
        return FirebaseDatabase.getInstance().getReference("nhanvien");
    }

    // 1. Lấy tất cả (Load bảng)
    public List<NhanVien> findAll() {
        CompletableFuture<List<NhanVien>> future = new CompletableFuture<>();
        List<NhanVien> list = new ArrayList<>();

        getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot node : snapshot.getChildren()) {
                        NhanVien nv = node.getValue(NhanVien.class);
                        list.add(nv);
                    }
                }
                future.complete(list);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });

        try {
            return future.get(); // Chờ lấy dữ liệu về xong mới return
        } catch (InterruptedException | ExecutionException e) {
            return new ArrayList<>();
        }
    }

    // 2. Tìm theo Mã (Dùng cho chức năng Edit)
    public NhanVien findById(String id) {
        CompletableFuture<NhanVien> future = new CompletableFuture<>();

        getRef().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    future.complete(snapshot.getValue(NhanVien.class));
                } else {
                    future.complete(null);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                future.complete(null);
            }
        });

        try {
            return future.get();
        } catch (Exception e) {
            return null;
        }
    }

    // 3. Thêm mới & Cập nhật (Firebase dùng chung hàm setValueAsync)
    public void save(NhanVien nv) {
        getRef().child(nv.getMaNV()).setValueAsync(nv);
    }

    // 4. Xóa
    public void delete(String id) {
        getRef().child(id).removeValueAsync();
    }
}