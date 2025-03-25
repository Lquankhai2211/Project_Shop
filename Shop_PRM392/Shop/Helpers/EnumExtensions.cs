using System;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Reflection;

namespace Shop.Helpers
{
    public static class EnumExtensions
    {
        public static string GetDisplayName(Enum value)
        {
            return value.GetType()
                        .GetMember(value.ToString())
                        .First()
                        .GetCustomAttribute<DisplayAttribute>()
                        ?.Name ?? value.ToString();
        }
    }
}
