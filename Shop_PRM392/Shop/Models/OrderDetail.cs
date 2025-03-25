using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace Shop.Models
{
    [Table("order_details")]
    public partial class OrderDetail
    {
        [Key]
        [Column("order_id", TypeName = "int(11)")]
        public int OrderId { get; set; }
        [Column("product_id", TypeName = "int(11)")]
        [Key]
        public int ProductId { get; set; }
        [Column("amount", TypeName = "int(11)")]
        public int Amount { get; set; }
        [Column("price")]
        public double Price { get; set; }
        [ForeignKey("OrderId")]
        public Order Order { get; set; }
    }
}
