// Khai báo package cho file
package com.thuc0502.mvvm.ui.adapter

// Import các thư viện cần thiết
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thuc0502.mvvm.data.model.User
import com.thuc0502.mvvm.databinding.ItemUserBinding

// Khai báo class UserAdapter kế thừa từ RecyclerView.Adapter
class UserAdapter(
    // Khai báo hai hàm callback để xử lý sự kiện click vào nút chỉnh sửa và xóa
    private val onEditClickListener: (User) -> Unit ,
    private val onDeleteClickListener: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // Khai báo danh sách User
    private var userList: List<User> = listOf()

    // Hàm cập nhật danh sách User
    fun setUsers(users: List<User>) {
        userList = users
        notifyDataSetChanged()
    }

    // Hàm tạo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup ,viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false)
        return UserViewHolder(binding)
    }

    // Hàm gán dữ liệu cho ViewHolder
    override fun onBindViewHolder(holder: UserViewHolder ,position: Int) {
        holder.bind(userList[position])
    }

    // Hàm trả về số lượng item
    override fun getItemCount(): Int {
        return userList.size
    }

    // Khai báo class UserViewHolder
    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Hàm gán dữ liệu cho item
        fun bind(user: User) {
            binding.user = user
            binding.executePendingBindings()
            binding.btnEditUser.setOnClickListener {
                onEditClickListener.invoke(user)
            }
            binding.btnDeleteUser.setOnClickListener {
                showDeleteConfirmationDialog(user)
            }
        }

        // Hàm hiển thị dialog xác nhận xóa
        private fun showDeleteConfirmationDialog(user: User) {
            val builder = AlertDialog.Builder(binding.root.context)
            builder.apply {
                setTitle("Xóa người dùng")
                setMessage("Bạn có chắc chắn muốn xóa người dùng ${user.name}?")
                setPositiveButton("Đồng ý") { dialog ,_ ->
                    onDeleteClickListener.invoke(user)
                    dialog.dismiss()
                }
                setNegativeButton("Hủy") { dialog ,_ ->
                    dialog.dismiss()
                }
            }
            builder.create().show()
        }
    }
}