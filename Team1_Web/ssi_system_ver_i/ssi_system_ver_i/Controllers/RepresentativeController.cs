using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using ssi_system_ver_i.Filter;
using ssi_system_ver_i.Models;

namespace ssi_system_ver_i.Controllers
{
    [DepRepFilter]
    public class RepresentativeController : Controller
    {

        public ActionResult View_Collect_Order()
        {
            string user_name = Session["UserName"].ToString();

            using (var db = new DataBaseContext())
            {
                staff current_staff_obj = db.staff_repository.Where(x => x.name == user_name).Select(x => x).FirstOrDefault();

                List<orders> order_lis = db.orders_repository.Where(i => i.staff_obj.department_obj.department_name == current_staff_obj.department_obj.department_name && i.order_status == "Approved_by_Clerk").ToList();
                department dept = db.department_repository.Where(y => y.departmentId == current_staff_obj.department_obj.departmentId).Select(y => y).FirstOrDefault();
                for (int i = 0; i < order_lis.Count; i++)
                {
                    orders temp_order = order_lis[i];

                    order_lis[i].item_obj = temp_order.item_obj;
                }

                ViewBag.Order_List_Approved_by_Clerk = order_lis;
                ViewBag.dept = dept;
            }
            return View();
        }

        [HttpPost]
        public ActionResult Actual_Quantity_Received_by_Representative(FormCollection form_data)
        {
            int total_quantity_bx = Int32.Parse(form_data["total_actual_delivered_quantity_bx"]);
            
            Dictionary<string, string> order_id_and_actual_received_quantity = new Dictionary<string, string>();

            for (int j=0; j < total_quantity_bx; j++)
            {
                string order_id = form_data["order_id_bx_" + j];
                string actual_received_quantity = form_data["actual_quantity_received_by_representative_" + j];

                order_id_and_actual_received_quantity.Add(order_id, actual_received_quantity);
            }

            using (var db = new DataBaseContext())
            {
                foreach (KeyValuePair<string, string> temp_data in order_id_and_actual_received_quantity)
                {
                    int temp_order_id = Int32.Parse(temp_data.Key);
                    orders temp_order_obj = db.orders_repository.Where(x => x.ordersId == temp_order_id).FirstOrDefault();
                    temp_order_obj.actual_received_quantity_by_representative = Int32.Parse(temp_data.Value);
                    temp_order_obj.order_status = "Approved_by_Representative";

                    db.SaveChanges();
                }
            }
            
            return RedirectToAction("View_Collect_Order", "Representative");
        }
        public ActionResult Change_Collection_Point()
        {

            return View();
        }
        [HttpPost]
        public ActionResult Change_Col_Point(FormCollection form_data)
        {
            string collection_point_name = form_data["collection_point_list"];

            using (var db = new DataBaseContext())
            {
                String username = Session["UserName"].ToString();
                staff staff_obj = db.staff_repository.Where(s => s.name == username).Select(x => x).FirstOrDefault();

                string department_name = staff_obj.department_obj.department_name;

                department depart_obj = db.department_repository.Where(d => d.department_name == department_name).FirstOrDefault();
                depart_obj.collection_point = collection_point_name;

                db.SaveChanges();

            }
            return RedirectToAction("Change_Collection_Point", "Representative");
        }


    }
}