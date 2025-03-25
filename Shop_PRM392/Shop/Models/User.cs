using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

namespace Shop.Models
{
    [Table("user")]
    public partial class User
    {
        [Key]
        [Column("id", TypeName = "int(11)")]
        public int Id { get; set; }
        [Column("email")]
        [StringLength(250)]
        public string Email { get; set; } = null!;
        [Column("password")]
        [StringLength(250)]
        public string Password { get; set; } = null!;
        [Column("username")]
        [StringLength(150)]
        public string Username { get; set; } = null!;
        [Column("phone")]
        [StringLength(20)]
        public string Phone { get; set; } = null!;
        [Column("role_id", TypeName = "int(11)")]
        public int RoleId { get; set; }
    }
}
