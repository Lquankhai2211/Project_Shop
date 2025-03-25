using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace Shop.Models
{
    [Table("product")]
    public partial class Product
    {
        [Key]
        [Column("id", TypeName = "int(11)")]
        public int Id { get; set; }
        [Column("product_name")]
        [StringLength(100)]
        public string ProductName { get; set; } = null!;
        [Column("product_image", TypeName = "text")]
        public string ProductImage { get; set; } = null!;
        [Column("product_description")]
        [StringLength(350)]
        public string ProductDescription { get; set; } = null!;
        [Column("product_price")]
        public double ProductPrice { get; set; }
        [Column("brand_id", TypeName = "int(11)")]
        public int BrandId { get; set; }
    }
}
