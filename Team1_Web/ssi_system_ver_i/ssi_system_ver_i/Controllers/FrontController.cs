using ssi_system_ver_i.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace ssi_system_ver_i.Controllers
{
    [RoutePrefix("ssi")]
    public class FrontController : Controller
    {

        // -----------------------------------------------------------------------------------//
        // ------------------------------------  LOG OUT -------------------------------------//
        public ActionResult Log_Out()
        {
            Session.Clear();
            return RedirectToAction("Log_In", "Front");
        }



        public ActionResult Log_In()
        {
            return View();
        }

        [HttpPost]
        public ActionResult SignIn(FormCollection sign_in_data)
        {
            string email_val = sign_in_data["log_email_bx"];
            string password_val = sign_in_data["login_passwd_bx"];

            using (var db = new DataBaseContext())
            {

                staff staff_obj = db.staff_repository.Where(x => x.email == email_val).FirstOrDefault<staff>();

                if (staff_obj == null)
                {
                    return RedirectToAction("Log_In", "Front");
                }

                else
                {
                    if (staff_obj.password.Equals(password_val))
                    {

                        staff_representative staff_repre_obj = db.staff_representative_repository.Where(s_re => s_re.representative_staff_obj.staffId == staff_obj.staffId).FirstOrDefault();

                        if (staff_repre_obj != null)
                        {
                            if (staff_repre_obj.position == "Temporary_Head")
                            {


                                Session["UserName"] = staff_obj.name;
                                Session["Role"] = "Temporary_Head";
                                return RedirectToAction("View_Staff_Request", "Head");
                            }
                        }

                        else if (staff_obj.position.Equals("Employee"))
                        {
                            Session["UserName"] = staff_obj.name;
                            Session["Role"] = "Employee";
                            return RedirectToAction("View_Request_Item", "Staff");
                        }

                        else if (staff_obj.position.Equals("Head"))
                        {
                            Session["UserName"] = staff_obj.name;
                            Session["Role"] = "Head";
                            return RedirectToAction("View_Staff_Request", "Head");
                        }

                        else if (staff_obj.position.Equals("Clerk"))
                        {
                            Session["UserName"] = staff_obj.name;
                            Session["Role"] = "Clerk";

                            return RedirectToAction("View_Department_Request", "Clerk");
                        }

                        else if (staff_obj.position.Equals("Manager"))
                        {
                            Session["UserName"] = staff_obj.name;
                            Session["Role"] = "Manager";
                            return RedirectToAction("View_Manager_Adjustment_Voucher", "Manager");
                        }

                        else if (staff_obj.position.Equals("Supervisor"))
                        {
                            Session["UserName"] = staff_obj.name;
                            Session["Role"] = "Supervisor";
                            return RedirectToAction("View_Supervisor_Adjustment_Voucher", "Supervisor");
                        }

                        else if (staff_obj.position.Equals("Representative"))
                        {
                            Session["UserName"] = staff_obj.name;
                            Session["Role"] = "Representative";
                            return RedirectToAction("View_Collect_Order", "Representative");
                        }
                    }

                    else
                    {
                        return RedirectToAction("Log_In", "Front");
                    }
                }

            }
            return RedirectToAction("Log_In", "Front");
        }


    }
}