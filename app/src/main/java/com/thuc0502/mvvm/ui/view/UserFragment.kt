// Khai báo package cho file
package com.thuc0502.mvvm.ui.view

// Import các thư viện cần thiết
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.thuc0502.mvvm.data.model.User
import com.thuc0502.mvvm.databinding.FragmentUserBinding
import com.thuc0502.mvvm.ui.adapter.UserAdapter
import com.thuc0502.mvvm.ui.viewmodel.UserViewModel

// Khai báo class UserFragment kế thừa từ Fragment
class UserFragment : Fragment() {

    // Khởi tạo ViewModel và Binding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: FragmentUserBinding
    private lateinit var userAdapter: UserAdapter

    // Hàm tạo view cho Fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout cho Fragment từ file fragment_user.xml
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Hàm được gọi sau khi view đã được tạo
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Khởi tạo UserAdapter với các hàm callback
        userAdapter = UserAdapter(
            onEditClickListener = { user ->
                showEditDialog(user)
            },
            onDeleteClickListener = { user ->
                deleteUser(user)
            }
        )

        // Cài đặt RecyclerView
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }

        // Quan sát dữ liệu từ ViewModel
        userViewModel.users.observe(viewLifecycleOwner) { users ->
            users?.let {
                userAdapter.setUsers(it)
            }
        }

        // Đặt sự kiện click cho nút thêm User
        binding.btnAddUser.setOnClickListener {
            showAddUserDialog()
        }

        // Lấy danh sách User từ Firebase
        userViewModel.fetchUsers()
    }

    // Hàm hiển thị dialog chỉnh sửa User
    private fun showEditDialog(user: User) {
        val editDialog = EditUserDialogFragment(user) { updatedUser ->
            userViewModel.updateUser(updatedUser)
        }
        editDialog.show(parentFragmentManager, "EditUserDialog")
    }

    // Hàm xóa User
    private fun deleteUser(user: User) {
        userViewModel.deleteUser(user.id)
    }

    // Hàm hiển thị dialog thêm User
    private fun showAddUserDialog() {
        val addUserDialog = EditUserDialogFragment(User(0, "", "")) { newUser ->
            userViewModel.addUser(newUser)
        }
        addUserDialog.show(parentFragmentManager, "AddUserDialog")
    }
}