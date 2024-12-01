    function confirmDelete() {
        return confirm("Do you want to delete this accessory?");
    }



function clearForm() {
    const form = document.querySelector('form');

    // Đặt lại các giá trị trong form
    form.reset();

    // Xóa giá trị của các trường input
    document.querySelectorAll('input, select, textarea').forEach(input => {
        input.value = ''; // Xóa giá trị hiện tại
        input.removeAttribute('value'); // Xóa thuộc tính value (nếu có)
    });

    // Xóa thông báo lỗi nếu có
    document.querySelectorAll('.text-danger').forEach(error => {
        error.textContent = ''; // Xóa nội dung lỗi
    });

    // Xóa trạng thái lỗi CSS nếu có
    document.querySelectorAll('.is-invalid').forEach(field => {
        field.classList.remove('is-invalid');
    });

    // Gửi yêu cầu GET để tải lại dữ liệu ban đầu từ server
    //window.location.href = '/total';
}






