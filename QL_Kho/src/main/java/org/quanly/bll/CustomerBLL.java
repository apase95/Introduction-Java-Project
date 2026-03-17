package org.quanly.bll;

import org.quanly.dal.CustomerDAL;
import org.quanly.dto.CustomerDTO;
import java.util.List;

public class CustomerBLL {
    private final CustomerDAL dal = new CustomerDAL();

    public List<CustomerDTO> getAllCustomers() {
        return dal.getAll();
    }

    public String addCustomer(CustomerDTO c) {
        if (c.getName() == null || c.getName().trim().isEmpty()) {
            return "Tên khách hàng không được để trống!";
        }
        return dal.insert(c) ? "Thêm khách hàng thành công!" : "Lỗi khi thêm vào cơ sở dữ liệu!";
    }

    public String updateCustomer(CustomerDTO c) {
        if (c.getId() <= 0) {
            return "ID khách hàng không hợp lệ!";
        }
        if (c.getName() == null || c.getName().trim().isEmpty()) {
            return "Tên khách hàng không được để trống!";
        }
        return dal.update(c) ? "Cập nhật khách hàng thành công!" : "Lỗi khi cập nhật khách hàng!";
    }

    public String deleteCustomer(int id) {
        if (id <= 0) {
            return "ID khách hàng không hợp lệ!";
        }
        return dal.delete(id) ? "Xóa khách hàng thành công!" : "Lỗi khi xóa khách hàng!";
    }
}
