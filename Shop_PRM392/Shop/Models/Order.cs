using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Shop.Models
{
    

    [Table("orders")]
    public partial class Order
    {
        [Key]
        [Column("id", TypeName = "int(11)")]
        public int Id { get; set; }

        [Column("user_id", TypeName = "int(11)")]
        public int UserId { get; set; }

        [Column("address", TypeName = "text")]
        [Display(Name = "Địa chỉ")]
        public string Address { get; set; } = null!;

        [Column("amount", TypeName = "int(11)")]
        [Display(Name = "Số lượng sản phẩm")]
        public int Amount { get; set; }

        [Column("total_price")]
        [StringLength(255)]
        [Display(Name = "Tổng tiền")]
        public string TotalPrice { get; set; } = null!;

        [Column("phone")]
        [StringLength(20)]
        [Display(Name = "Số điện thoại")]
        public string Phone { get; set; } = null!;

        [Column("status")]
        [Display(Name = "Trạng thái")]
        public string Status { get; set; }
        public List<OrderDetail> OrderDetails { get; set; } = new();
        [NotMapped] // Không lưu vào DB
        public string Email { get; set; } = "Không có thông tin";
    }
}
