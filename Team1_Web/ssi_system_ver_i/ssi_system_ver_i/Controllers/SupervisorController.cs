using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;
using ADprojectteam1.Service;
using PagedList;
using ssi_system_ver_i.DAL;
using ssi_system_ver_i.Filter;
using ssi_system_ver_i.Models;

namespace ssi_system_ver_i.Controllers
{
    [StoreSupFilter]
    public class SupervisorController : Controller
    {
        // GET: Supervisor
        // ------------------------------- View Adjustment_Voucher -------------------------------------------
        public ActionResult View_Supervisor_Adjustment_Voucher()
        {
            using (var db = new DataBaseContext())
            {

                var adj_gt = db.adjustment_voucher_repository.Join(
                    db.item_warehouse_repository,
                    adjust => adjust.item_obj.itemId,
                    item => item.itemId,
                    (adjust, item) => new
                    {
                        adjust_obj = adjust,
                        item_obj = item

                    }
                    ).Where(x => x.adjust_obj.status == "Added_by_Clerk").ToList();

                List<adjustment_voucher> temp_adj_lis = new List<adjustment_voucher>();
                List<item> temp_item_lis = new List<item>();

                foreach (var temp in adj_gt)
                {
                    temp_adj_lis.Add(temp.adjust_obj);
                    temp_item_lis.Add(temp.item_obj);
                }

                ViewBag.Adjust_List = temp_adj_lis;
                ViewBag.Item_List = temp_item_lis;

            }
            return View();
        }

        public JsonResult Ajax_Approve_Adjustment_ID(ajax_model ajax_data)
        {
            using (var db = new DataBaseContext())
            {
                int adjust_id = Int32.Parse(ajax_data.name);

                adjustment_voucher adjust_obj = db.adjustment_voucher_repository.Where(x => x.adjustment_voucherId == adjust_id).FirstOrDefault();
                adjust_obj.status = "Approved_by_Supervisor";

                db.SaveChanges();
            }
            object reply_to_client = new
            {
                key_itemname_lis = "SUCCESS",
            };

            return Json(reply_to_client, JsonRequestBehavior.AllowGet);
        }

        public JsonResult Ajax_Reject_Adjustment_ID(ajax_model ajax_data)
        {
            using (var db = new DataBaseContext())
            {
                int adjust_id = Int32.Parse(ajax_data.name);

                adjustment_voucher adjust_obj = db.adjustment_voucher_repository.Where(x => x.adjustment_voucherId == adjust_id).FirstOrDefault();
                adjust_obj.status = "Rejected_by_Supervisor";

                db.SaveChanges();
            }
            object reply_to_client = new
            {
                key_itemname_lis = "SUCCESS",
            };

            return Json(reply_to_client, JsonRequestBehavior.AllowGet);
        }



        /////////////////////////////////////////////////////////Trend Report
       public async Task<ActionResult> View_Trend_Analysis_Detail(int itemId, int? depId)
        {
            List<orders> listorders = new List<orders>();
            listorders = OrdersData.GetAllDeliveredOrders();

            int dId = depId ?? 0;
            List<orders> listorders1 = new List<orders>();
            if (dId != 0)
            {
                listorders1 = listorders.Where(x => x.staff_obj.department_obj.departmentId == dId).ToList();
                ViewBag.depart = DepartmentData.GetDepartmentById(dId);
            }
            else
                listorders1 = listorders;

            
                Dictionary<string, int> itemsbtrend = new Dictionary<string, int>();
                Dictionary<string, int> itemtrend = new Dictionary<string, int>();

                
            




            Dictionary<int, Dictionary<string, int>> trendlist = (Dictionary<int, Dictionary<string, int>>)Session["trendlist"];
            item item = ItemData.GetItemById(itemId);  // get the item by given itemId
            List<string> monlist = new List<string>();
            

            for (int i = 11; i >= 0; i--)
            {

                //string dt = string.Format("{0}/{1}", DateTime.Today.AddMonths(-i).Month, DateTime.Today.AddMonths(-i).Year);
                string dt = DateTime.Today.AddMonths(-i).ToString("MM/yyyy");
                monlist.Add(dt);//list of last 12 months
                                /////////////////////////////////////////////////////////////////////////
                                //Get stockbalance on given month, put in dictionary "itemsbtrend", key is month string, value is stockbalance of the month.
                                //////////////////////////////////////////////////////////////////    

                char[] separator = { '/', ' ' };

                

                List<stock_card> list = StockcardData.GetStockCardByItemId(itemId);//give proper value here.


                List<stock_card> mlist = list.Where(x => x.Arrival_Date.Split(separator)[0].Equals(dt.Split(separator)[0]) && x.Arrival_Date.Split(separator)[2].Equals(dt.Split(separator)[1])).ToList();
                int stockbalance = mlist[mlist.Count - 1].stockbalance;
                itemsbtrend.Add(dt, stockbalance);
            }

            int[] cons = trendlist[itemId].Values.ToArray();


            foreach (string m in monlist)
            {
                char[] separator = { '/', ' ' };
                string[] dtmonthyear = m.Split(separator);
                //Get monthly consumption quant on given month
                int consumedquant = listorders1.Where(x => x.delivered_order_date.Split(separator)[0].Equals(dtmonthyear[0]) && x.delivered_order_date.Split(separator)[2].Equals(dtmonthyear[1])).Select(x => x.actual_received_quantity_by_representative).Sum();//Get correct value here
                itemtrend.Add(m, consumedquant);

            }
            

            // send request and get response from python server
            int[] prelist = new int[4];
            int[] paralist = new int[13];
            paralist[0] = itemId;
            for (int i = 1; i < 13; i++)
                paralist[i] = cons[i - 1];
            string conshist = string.Join(", ", paralist);


            using (var client = new HttpClient())
            {
                string xValue = conshist;


                // send a GET request to the server uri with the data and get the response as HttpResponseMessage object
                HttpResponseMessage res = await client.GetAsync("http://127.0.0.1:5000/model1?x=" + xValue);

                // Return the result from the server if the status code is 200 (everything is OK)
                // should raise exception or error if it's not
                if (res.IsSuccessStatusCode)
                {
                    // pass the result to update the user preference
                    // have to read as string for the data in response.body
                    //pre = Convert.ToInt32(res.Content.ReadAsStringAsync().Result); //if only display one month prediction.
                    string arr = res.Content.ReadAsStringAsync().Result;
                    prelist = arr.Split(',').Select(str => int.Parse(str)).ToArray();
                }
                else prelist = new int[4] { 0, 0, 0, 0 };
            }

            items_warehouse iwh = ItemWarehouseData.FindByItemId(itemId);

            ViewBag.departList = DepartmentData.GetAllDep();
            ViewBag.cons = itemtrend.Values.ToArray();
            ViewBag.months = monlist.ToArray();
            ViewBag.Item = item;
            ViewBag.sbalance = itemsbtrend.Values.ToArray();
            ViewBag.prediction = prelist;
            ViewBag.itemwarehouse = iwh;
            return View();
        }

        public ActionResult View_Trend_Analysis(string searchStr, int? page, int? depId)
        {


            Dictionary<int, Dictionary<string, int>> trendlist = new Dictionary<int, Dictionary<string, int>>();


            List<string> monlist = new List<string>();
            for (int i = 11; i >= 0; i--)
            {
                //string dt = string.Format("{0}/{1}", DateTime.Today.AddMonths(-i).Month, DateTime.Today.AddMonths(-i).Year);
                string dt = DateTime.Today.AddMonths(-i).ToString("MM/yyyy");
                monlist.Add(dt);
            }


            List<item> listitem = ItemData.FindAll(); //get all items
            List<item> resultlist = new List<item>();

            


            IPagedList<item> resultlist1;
            bool match = false;

            if (searchStr == null || searchStr == "")
            {
                searchStr = "";
                resultlist1 = listitem.ToPagedList(page ?? 1, 7);
            }
            else
            {
                foreach (item Pro in listitem)
                {
                    bool fit = false;
                    if (Search.Found(Pro.item_description, searchStr).fit)
                    {
                        fit = true;
                        Pro.item_description = Search.Found(Pro.item_description, searchStr).str;
                    }

                    if (fit) { match = true; resultlist.Add(Pro); }
                }
                resultlist1 = resultlist.ToPagedList(page ?? 1, 7);
            }

            ViewBag.listitem = resultlist1;

            ViewData["searchStr"] = searchStr;
            ViewData["match"] = match;

            /////////////////////////////////////////////////////////////



            /////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //get the consumed quant of last 12 months in searched list, put in a dictionary, key is month, value is quant.
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            List<orders> listorders = new List<orders>();
            listorders = OrdersData.GetAllDeliveredOrders();

            int dId = depId ?? 0;
            List<orders> listorders1 = new List<orders>();
            if (dId != 0)
            { listorders1 = listorders.Where(x => x.staff_obj.department_obj.departmentId == dId).ToList();
                ViewBag.depart = DepartmentData.GetDepartmentById(dId);
            }
            else
                listorders1 = listorders;

            foreach (item item in resultlist1)
            {
                Dictionary<string, int> itemsbtrend = new Dictionary<string, int>();
                Dictionary<string, int> itemtrend = new Dictionary<string, int>();

                foreach (string m in monlist)
                {
                    char[] separator = { '/', ' ' };
                    string[] dtmonthyear = m.Split(separator);
                    //Get monthly consumption quant on given month
                    int consumedquant = listorders1.Where(x => x.delivered_order_date.Split(separator)[0].Equals(dtmonthyear[0]) && x.delivered_order_date.Split(separator)[2].Equals(dtmonthyear[1])).Select(x => x.actual_received_quantity_by_representative).Sum();//Get correct value here
                    itemtrend.Add(m, consumedquant);

                }
                trendlist.Add(item.itemId, itemtrend);
            }


            ViewBag.departList = DepartmentData.GetAllDep();
            ViewBag.trendlist = trendlist;
            ViewBag.monthslist = monlist;
            Session["trendlist"] = trendlist;

            return View();
        }

        [HttpPost]
        public JsonResult UpdateReOrderLevel(int itemId, int newreorderlevel)
        {

            ItemWarehouseData.UpdateReOrderLevelByItemId(itemId, newreorderlevel);



            ///
            object n = new { newlevel = newreorderlevel };
            return Json(n, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult UpdateReOrderQuant(int itemId, int newreorderquant)
        {

            ItemWarehouseData.UpdateReOrderQuantByItemId(itemId, newreorderquant);



            ///
            object n = new { newquant = newreorderquant };
            return Json(n, JsonRequestBehavior.AllowGet);
        }


    }
}