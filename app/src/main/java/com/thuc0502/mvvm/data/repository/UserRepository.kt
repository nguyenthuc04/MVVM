package com.thuc0502.mvvm.data.repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.thuc0502.mvvm.data.model.User
import kotlinx.coroutines.tasks.await

// Khai báo class UserRepository
class UserRepository {
    // Khởi tạo đối tượng database để truy cập vào Firebase
    private val database = FirebaseDatabase.getInstance("https://mvvm-9f47e-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users")

    // Hàm thêm một User mới vào Firebase
    suspend fun addUser(user: User) {
        // Lấy danh sách User hiện tại
        val users = getUsers()
        // Tính toán ID cho User mới
        val newUserId = if (users.isEmpty()) 1 else users.maxOf { it.id } + 1
        // Gán ID mới cho User
        user.id = newUserId
        // Thêm User vào Firebase
        database.child(user.id.toString()).setValue(user).await()
    }

    // Hàm cập nhật thông tin User trên Firebase
    suspend fun updateUser(user: User) {
        database.child(user.id.toString()).setValue(user)
    }

    // Hàm xóa User khỏi Firebase
    suspend fun deleteUser(userId: Int) {
        database.child(userId.toString()).removeValue()
    }

    // Hàm lấy thông tin của một User từ Firebase
    suspend fun getUser(userId: String): User? {
        return try {
            // Lấy dữ liệu từ Firebase
            val snapshot = database.child(userId).get().await()
            // Chuyển dữ liệu về dạng User
            snapshot.getValue(User::class.java)
        } catch (e: Exception) {
            // Trả về null nếu có lỗi
            null
        }
    }

    // Hàm lấy danh sách tất cả User từ Firebase
    suspend fun getUsers(): List<User> {
        return try {
            // Lấy dữ liệu từ Firebase
            val snapshot = database.get().await()
            // Chuyển dữ liệu về danh sách User
            snapshot.children.mapNotNull { it.getValue(User::class.java) }
        } catch (e: Exception) {
            // Log lỗi và trả về danh sách rỗng nếu có lỗi
            Log.e("UserRepository", "Error fetching users: ${e.message}")
            emptyList()
        }
    }
}