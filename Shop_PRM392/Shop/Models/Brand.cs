using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace Shop.Models
{
    [Table("brand")]
    public partial class Brand
    {
        [Key]
        [Column("id", TypeName = "int(11)")]
        public int Id { get; set; }
        [Column("brand_name")]
        [StringLength(100)]
        public string BrandName { get; set; } = null!;
        [Column("brand_image", TypeName = "text")]
        public string BrandImage { get; set; } = null!;
    }
}
