// Khai báo package cho file
package com.thuc0502.mvvm.ui.view

// Import các thư viện cần thiết
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.thuc0502.mvvm.R
import com.thuc0502.mvvm.data.model.User

// Khai báo class EditUserDialogFragment kế thừa từ DialogFragment
class EditUserDialogFragment(
    // Khai báo hai biến đầu vào: một User cần chỉnh sửa và một hàm callback khi User được cập nhật
    private val user: User ,
    private val onUserUpdated: (User) -> Unit
) : DialogFragment() {

    // Hàm tạo view cho DialogFragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout cho DialogFragment từ file dialog_edit_user.xml
        return inflater.inflate(R.layout.dialog_edit_user, container, false)
    }

    // Hàm được gọi sau khi view đã được tạo
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Lấy tham chiếu đến các EditText từ view
        val editTextUserName = view.findViewById<EditText>(R.id.editTextUserName)
        val editTextUserEmail = view.findViewById<EditText>(R.id.editTextUserEmail)

        // Đặt text cho các EditText bằng thông tin của User
        editTextUserName.setText(user.name)
        editTextUserEmail.setText(user.email)

        // Đặt sự kiện click cho nút Save
        view.findViewById<View>(R.id.buttonSave).setOnClickListener {
            // Tạo một User mới với thông tin từ các EditText
            val updatedUser = user.copy(
                name = editTextUserName.text.toString(),
                email = editTextUserEmail.text.toString()
            )
            // Gọi hàm callback với User mới
            onUserUpdated(updatedUser)
            // Đóng DialogFragment
            dismiss()
        }
    }
}