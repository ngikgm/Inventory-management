using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ssi_system_ver_i.Models;

namespace ssi_system_ver_i.DAL
{
    public class ItemWarehouseData
    {
        internal static items_warehouse FindByItemId(int itemId)
        {
            items_warehouse it = new items_warehouse();
            using (var db = new DataBaseContext())
            {

                if (db.item_warehouses_repository.Where(x => x.item.itemId == itemId).Any())
                    it = db.item_warehouses_repository.Where(x => x.item.itemId == itemId).FirstOrDefault();
            }
            return it;
        }

        internal static void UpdateReOrderQuantByItemId(int itemId, int newreorderquant)
        {
            items_warehouse itw = new items_warehouse();
            using (var db = new DataBaseContext())
            {

                if (db.item_warehouses_repository.Where(x => x.item.itemId == itemId).Any())
                    itw = db.item_warehouses_repository.Where(x => x.item.itemId == itemId).FirstOrDefault();
                itw.reorder_quantity = newreorderquant;
                db.SaveChanges();
            }
        }

        internal static void UpdateReOrderLevelByItemId(int itemId, int newreorderlevel)
        {
            items_warehouse itw = new items_warehouse();
            using (var db = new DataBaseContext())
            {

                if (db.item_warehouses_repository.Where(x => x.item.itemId == itemId).Any())
                    itw = db.item_warehouses_repository.Where(x => x.item.itemId == itemId).FirstOrDefault();
                itw.reorder_level = newreorderlevel;
                db.SaveChanges();
            }
        }
    }
}