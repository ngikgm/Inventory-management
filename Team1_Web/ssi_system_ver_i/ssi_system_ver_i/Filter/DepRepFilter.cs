using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;

namespace ssi_system_ver_i.Filter
{
    public class DepRepFilter : ActionFilterAttribute, IAuthorizationFilter
    {
        public void OnAuthorization(AuthorizationContext ac)
        {
            string sessionRole = (string)HttpContext.Current.Session["Role"];

            if (!IsValidSessionRole(sessionRole))
                ac.Result = new RedirectToRouteResult(
                    new RouteValueDictionary {
                        {"controller","Front" },
                        {"action","Log_In" }
                    });

        }

        public bool IsValidSessionRole(string sessionRole)
        {

            if (sessionRole == null)
                return false;
            if (sessionRole.Equals("Representative"))
                return true;
            if (sessionRole.Equals("Temporary_Rep"))
                return true;
            return false;
        }
    }
}