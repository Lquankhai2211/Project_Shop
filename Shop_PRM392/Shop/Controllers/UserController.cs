using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc;
using Shop.Models;
using Shop.ViewModels;

namespace Shop.Controllers
{
    public class UserController : Controller
    {
        private readonly ApplicationDbContext _context;

        public UserController(ApplicationDbContext context)
        {
            _context = context;
        }

        public IActionResult Login()
        {
            return View();
        }

        [HttpPost]
        public IActionResult Login(LoginViewModel model)
        {
            if (!ModelState.IsValid)
            {
                return View(model);
            }

            User user = _context.Users
                .FirstOrDefault(u => u.Email == model.Username && u.Password == model.Password);

            if (user == null)
            {
                user = _context.Users
                .FirstOrDefault(u => u.Username == model.Username && u.Password == model.Password);
                if (user == null) {

                    ViewBag.Error = "Sai tài khoản hoặc mật khẩu!";
                    return View();
                }
                if (user.RoleId != 1)
                {
                    ViewBag.Error = "Bạn không có quyền đăng nhập!";
                    return View();
                }
            }

            if (user.RoleId != 1)
            {
                ViewBag.Error = "Bạn không có quyền đăng nhập!";
                return View();
            }

            HttpContext.Session.SetInt32("UserId", user.Id);
            HttpContext.Session.SetString("Username", user.Username);
            HttpContext.Session.SetInt32("RoleId", user.RoleId);

            return RedirectToAction("Index", "Home");
        }

        public IActionResult Logout()
        {
            HttpContext.Session.Clear();
            return RedirectToAction("Login");
        }
    }
    
}
