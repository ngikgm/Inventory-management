using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ssi_system_ver_i.Models;

namespace ssi_system_ver_i.DAL
{
    public class OrdersData
    {
        internal static List<orders> GetAllDeliveredOrders()
        {
            List<orders> list = new List<orders>();
            using (var db = new DataBaseContext())
            {

                if (db.orders_repository.Where(x => x.order_status.Equals("Approved_by_Representative")).Any())
                    list = db.orders_repository.Include("staff_obj.department_obj").Where(x => x.order_status.Equals("Approved_by_Representative")).ToList();
            }

            return list;
        }

        internal static orders GetOrderById(int id)
        {
            orders o = new orders();
            using (var db = new DataBaseContext())
            {

                if (db.orders_repository.Where(x => x.ordersId == id).Any())
                    o = db.orders_repository.Include("staff_obj").Where(x => x.ordersId == id).FirstOrDefault();
            }

            return o;
        }
    }
}