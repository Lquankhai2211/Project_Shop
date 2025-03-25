using System.Diagnostics;
using Microsoft.AspNetCore.Mvc;

namespace Shop.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;

        public HomeController(ILogger<HomeController> logger)
        {
            _logger = logger;
        }

        public IActionResult Index()
        {
            if (HttpContext.Session.GetInt32("RoleId") != 1)
            {
                return RedirectToAction("Login", "User");
            }

            return View();
        }

        public IActionResult Privacy()
        {
            return View();
        }


    }
}
