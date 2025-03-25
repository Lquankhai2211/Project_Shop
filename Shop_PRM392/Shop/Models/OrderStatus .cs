using System.ComponentModel.DataAnnotations;

namespace Shop.Models
{
    public enum OrderStatus
    {
        [Display(Name = "Chờ xử lý")] Pending,
        [Display(Name = "Đang chuẩn bị hàng")] Processing,
        [Display(Name = "Đã giao hàng")] Shipped,
        [Display(Name = "Hoàn thành")] Completed,
        [Display(Name = "Đã hủy")] Canceled
    }

}
