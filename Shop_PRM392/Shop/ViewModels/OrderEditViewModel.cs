using Shop.Models;

namespace Shop.ViewModels
{
    public class OrderEditViewModel
    {
        public Order Order { get; set; } = null!;
        public List<Product> Products { get; set; } = new();
        public User? User { get; set; }
    }
}
