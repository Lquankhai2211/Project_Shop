using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Shop.Models;
using Shop.ViewModels;
using System.Linq;
using System.Threading.Tasks;

namespace Shop.Controllers
{
    public class OrderController : Controller
    {
        private readonly ApplicationDbContext _context;

        public OrderController(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<IActionResult> Index()
        {
            var orders = await _context.Orders
                .ToListAsync();

            var users = await _context.Users.ToListAsync();

            foreach (var order in orders)
            {
                var user = users.FirstOrDefault(u => u.Id == order.UserId);
                order.Email = user?.Email ?? "Không có thông tin"; 
            }

            return View(orders);
        }


        public async Task<IActionResult> Edit(int id)
        {
            var order = await _context.Orders
                .FirstOrDefaultAsync(o => o.Id == id);

            if (order == null)
            {
                return NotFound();
            }

            var user = await _context.Users
                .FirstOrDefaultAsync(u => u.Id == order.UserId);

            // Lấy danh sách sản phẩm từ OrderDetails
            var orderDetails = await _context.OrderDetails
        .Where(od => od.OrderId == id)
        .ToListAsync();
            var productIds = orderDetails.Select(od => od.ProductId).ToList();

            var products = await _context.Products
                .Where(p => productIds.Contains(p.Id))
                .ToListAsync();

            // Tạo ViewModel chứa cả Order, Products và User
            var viewModel = new OrderEditViewModel
            {
                Order = order,
                Products = products,
                User = user
            };

            return View(viewModel);
        }

        [HttpPost]
        public async Task<IActionResult> Edit(int id, [Bind("Status")] Order orderUpdate)
        {
            var order = await _context.Orders.FindAsync(id);
            if (order == null)
            {
                return NotFound();
            }

            order.Status = orderUpdate.Status;

            _context.Update(order);
            await _context.SaveChangesAsync();

            return RedirectToAction(nameof(Index));
        }

    }
}
