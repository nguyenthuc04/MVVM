// Khai báo package cho file
package com.thuc0502.mvvm.ui.viewmodel

// Import các thư viện cần thiết
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thuc0502.mvvm.data.model.User
import com.thuc0502.mvvm.data.repository.UserRepository
import kotlinx.coroutines.launch

// Khai báo class UserViewModel kế thừa từ ViewModel
class UserViewModel : ViewModel() {
    // Khởi tạo UserRepository
    private val repository = UserRepository()

    // Khai báo LiveData cho User và danh sách User
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    // Trong hàm khởi tạo, gọi hàm fetchUsers để lấy danh sách User từ Firebase
    init {
        fetchUsers()
    }

    // Hàm lấy thông tin của một User từ Firebase
    fun fetchUser(userId: String) {
        viewModelScope.launch {
            val user = repository.getUser(userId)
            _user.value = user!!
        }
    }

    // Hàm lấy danh sách tất cả User từ Firebase
    fun fetchUsers() {
        viewModelScope.launch {
            val users = repository.getUsers()
            _users.value = users
        }
    }

    // Hàm thêm một User mới vào Firebase
    fun addUser(user: User) {
        viewModelScope.launch {
            repository.addUser(user)
            fetchUsers()
        }
    }

    // Hàm cập nhật thông tin User trên Firebase
    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
            fetchUsers()
        }
    }

    // Hàm xóa User khỏi Firebase
    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            repository.deleteUser(userId)
            fetchUsers()
        }
    }
}