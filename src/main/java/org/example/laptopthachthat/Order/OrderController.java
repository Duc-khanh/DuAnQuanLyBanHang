package org.example.laptopthachthat.Order;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class OrderController {

    // Các thành phần FXML
    @FXML
    private ListView<String> orderItemsList; // Danh sách các mục trong đơn hàng
    @FXML
    private Label totalCostLabel; // Nhãn hiển thị tổng chi phí
    @FXML
    private TextField nameField; // Trường nhập tên khách hàng
    @FXML
    private TextField addressField; // Trường nhập địa chỉ giao hàng
    @FXML
    private TextField phoneField; // Trường nhập số điện thoại

    // Dữ liệu giả cho đơn hàng (thay thế bằng dữ liệu thực tế)
    private double totalCost = 0.00;

    // Phương thức khởi tạo gọi khi FXML được tải
    @FXML
    public void initialize() {
        // Điền các mục vào danh sách đơn hàng (thay thế bằng dữ liệu động)
        orderItemsList.getItems().addAll("Laptop A - 1 đơn vị - $500", "Laptop B - 2 đơn vị - $300");

        // Cập nhật tổng chi phí (thay thế bằng tính toán thực tế)
        totalCost = 1100.00;
        totalCostLabel.setText(String.format("%.2f", totalCost));
    }

    // Phương thức xử lý khi xác nhận đơn hàng
    @FXML
    private void handleConfirmOrder() {
        // Lấy thông tin khách hàng từ các trường nhập liệu
        String customerName = nameField.getText();
        String customerAddress = addressField.getText();
        String customerPhone = phoneField.getText();

        // Thêm logic xác nhận đơn hàng, ví dụ lưu vào cơ sở dữ liệu, in biên lai, v.v.
        System.out.println("Đơn hàng đã được xác nhận:");
        System.out.println("Tên khách hàng: " + customerName);
        System.out.println("Địa chỉ giao hàng: " + customerAddress);
        System.out.println("Số điện thoại: " + customerPhone);
        System.out.println("Tổng chi phí: $" + totalCost);

        // Bạn có thể đặt lại các trường nhập liệu hoặc hiển thị thông báo xác nhận ở đây
    }

    // Phương thức xử lý khi hủy đơn hàng
    @FXML
    private void handleCancelOrder() {
        // Xóa chi tiết đơn hàng và thông tin khách hàng
        orderItemsList.getItems().clear();
        totalCostLabel.setText("0.00");
        nameField.clear();
        addressField.clear();
        phoneField.clear();

        // Bạn cũng có thể hiển thị thông báo hoặc thực hiện các hành động khác khi hủy đơn hàng
        System.out.println("Đơn hàng đã bị hủy.");
    }
}
