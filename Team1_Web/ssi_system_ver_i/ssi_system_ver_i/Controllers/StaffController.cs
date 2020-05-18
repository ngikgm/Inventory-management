using ssi_system_ver_i.DAL;
using ssi_system_ver_i.Filter;
using ssi_system_ver_i.Models;
using ssi_system_ver_i.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;

namespace ssi_system_ver_i.Controllers
{
    [DepEmpFilter]
    public class StaffController : Controller
    {
        
        // ------------------------------------------------------------------------------------
        // --------------------------  CREATE REQUEST ITEM ------------------------------------------
        public ActionResult View_Request_Item()
        {
            string user_name = Session["UserName"].ToString();

            using(var db = new DataBaseContext())
            {
                staff staff_obj = db.staff_repository.Where(x => x.name == user_name).Select(x => x).FirstOrDefault();

                List<orders> orders_lis = db.orders_repository.Where(x => x.staff_obj.staffId == staff_obj.staffId && (x.order_status=="pending" || x.order_status == "Reject_by_Head")).ToList();

                List<string> item_description_lis = new List<string>();
                foreach(orders or in orders_lis)
                {
                    item_description_lis.Add(or.item_obj.item_description);
                }

                ViewBag.item_description_lis = item_description_lis;
                ViewBag.order_lis = (List<orders>)orders_lis;
            }

            //Email Notification
            staff s = StaffData.GetStaffByName((string)Session["UserName"]);
            string emailadd = StaffData.GetStaffByName(s.department_obj.head_name).email;
            Task task = Task.Run(() => {
                EmailNotification.SendNotificationEmailToEmployee(emailadd, "New Requisition Reminder", "There is a new requistion for your approval, please check.");
            });


            return View();
        }

        [HttpPost]
        public ActionResult Create_New_Request_Item(FormCollection form_data)
        {
            int num_of_item_request = Int32.Parse(form_data["number_of_request_items"]);

             if(num_of_item_request == 3)
            {
                string item_description = form_data["item_code_list_3"];
                string quantity_bx = form_data["quantity_bx_3"];
                string user_name = Session["UserName"].ToString();

                using (var db = new DataBaseContext())
                {
                    staff staff_obj = db.staff_repository.Where(x => x.name == user_name).Select(x => x).FirstOrDefault();

                        item item_obj = db.item_warehouse_repository.Where(x => x.item_description == item_description).FirstOrDefault();

                        orders order_obj = new orders(staff_obj, item_obj, Int32.Parse(quantity_bx), DateTime.Now.ToString());

                        db.orders_repository.Add(order_obj);
                        db.SaveChanges();
                }
            }
             else
            {
                List<String> item_code_lis = new List<String>();
                List<String> quantity_lis = new List<String>();

                for (int j = 3; j <= num_of_item_request; j++)
                {
                    string item_description = form_data["item_code_list_" + j];
                    string quantity_bx = form_data["quantity_bx_" + j];

                    item_code_lis.Add(item_description);
                    quantity_lis.Add(quantity_bx);
                }

                string user_name = Session["UserName"].ToString();

                using (var db = new DataBaseContext())
                {
                    staff staff_obj = db.staff_repository.Where(x => x.name == user_name).Select(x => x).FirstOrDefault();

                    for (int j = 0; j < item_code_lis.Count; j++)
                    {
                        string item_description = item_code_lis[j];
                        string quantity_bx = quantity_lis[j];

                        item item_obj = db.item_warehouse_repository.Where(x => x.item_description == item_description).FirstOrDefault();

                        orders order_obj = new orders(staff_obj, item_obj, Int32.Parse(quantity_bx), DateTime.Now.ToString());

                        db.orders_repository.Add(order_obj);
                        db.SaveChanges();
                    }
                }
            }

            return RedirectToAction("View_Request_Item", "Staff");
        }

 

        // -----------------------------------------------------------------------------------//
        // ------------------------------------  EDIT REQUEST ITEM -------------------------------------//
        [HttpPost]
        public ActionResult Edit_New_Request_Item(FormCollection form_data)
        {
            string order_id = form_data["edit_order_id"];
            string item_code = form_data["edit_item_code"]; 
            string quantity_bx = form_data["edit_quantity_bx"];

            string user_name = Session["UserName"].ToString();

            using (var db = new DataBaseContext())
            {
                int target_id = Int32.Parse(order_id);
                int target_quantity = Int32.Parse(quantity_bx);

                orders order_obj = db.orders_repository.Where(x => x.ordersId == target_id).FirstOrDefault();
                order_obj.proposed_quantity = target_quantity;

                order_obj.order_status = "pending";
                db.SaveChanges();
            }

            return RedirectToAction("View_Request_Item", "Staff");
        }

        // -----------------------------------------------------------------------------------------------
        // ------------------------------------- AJAX -----------------------------------------------------
        [HttpPost]
        public JsonResult Ajax_Add_Request_Item(ajax_model ajax_model_obj)
        {
            List<String> item_name_lis = new List<String>();

            ajax_model data = new ajax_model
            {
                name = ajax_model_obj.name,
                main_data = ajax_model_obj.main_data,
            };

            using (var db = new DataBaseContext())
            {
                List<item> all_item_lis = db.item_warehouse_repository.ToList<item>();

                for (int i = 0; i < all_item_lis.Count; i++)
                {
                    item_name_lis.Add(all_item_lis[i].item_description);
                }
            }
            object reply_to_client = new
            {
                key_itemname_lis = item_name_lis,
            };

            return Json(reply_to_client, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult Ajax_Delete_Request_Item(ajax_model ajax_model_obj)
        {
            ajax_model data = new ajax_model
            {
                name = ajax_model_obj.name,
                main_data = ajax_model_obj.main_data,
            };

            using (var db = new DataBaseContext())
            {
                int target_order_item = Int32.Parse(data.name);

                orders order_item = db.orders_repository.Where(x => x.ordersId == target_order_item).FirstOrDefault();

                db.orders_repository.Remove(order_item);
                db.SaveChanges();

            }
            object reply_to_client = new
            {
                key_itemname_lis = "SUCCESS",
            };

            return Json(reply_to_client, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult Ajax_Edit_Request_Item(ajax_model ajax_model_obj)
        {
            orders order_item;

            ajax_model data = new ajax_model
            {
                name = ajax_model_obj.name,
                main_data = ajax_model_obj.main_data,
            };

            using (var db = new DataBaseContext())
            {
                int target_order_item = Int32.Parse(data.name);

                order_item = db.orders_repository.Where(x => x.ordersId == target_order_item).FirstOrDefault();


                object reply_to_client = new
                {
                    order_id = order_item.ordersId,
                    order_item_description = order_item.item_obj.item_description,
                    order_quantity = order_item.proposed_quantity
                };
                return Json(reply_to_client, JsonRequestBehavior.AllowGet);
            }
            
            //return Json(null, JsonRequestBehavior.AllowGet);
        }


        // -----------------------------------------------------------------------------------//
        // ------------------------------------  History_Request_Items-------------------------------------//

        public ActionResult View_History_Request_Items()
        {
            using(var db = new DataBaseContext())
            {
                string user_name = Session["UserName"].ToString();

                staff current_staff_obj = db.staff_repository.Where(x => x.name == user_name).Select(x => x).FirstOrDefault();

                List<orders> orders_lis = db.orders_repository.Where(or => or.order_status == "Approved_by_Head" && or.staff_obj.staffId == current_staff_obj.staffId).ToList();

                for(int i=0;i<orders_lis.Count;i++)
                {
                    orders temp_order = orders_lis[i];
                    orders_lis[i].item_obj = temp_order.item_obj;

                }
                ViewBag.order_lis = (List<orders>)orders_lis;
            }

                return View();
        }

    }


}