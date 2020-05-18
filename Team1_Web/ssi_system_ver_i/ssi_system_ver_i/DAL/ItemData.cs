using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ssi_system_ver_i.Models;

namespace ssi_system_ver_i.DAL
{
    public class ItemData
    {
        public static item GetItemById(int itemId)
        {
            item it = new item();
            using (var db = new DataBaseContext())
            {

                if (db.item_warehouse_repository.Where(x => x.itemId == itemId).Any())
                    it = db.item_warehouse_repository.Where(x => x.itemId == itemId).FirstOrDefault();
            }
            return it;
        }

        internal static List<item> FindAll()
        {
            List<item> itlist = new List<item>();
            using (var db = new DataBaseContext())
            {

                if (db.item_warehouse_repository.Any())
                    itlist = db.item_warehouse_repository.ToList();
            }
            return itlist;
        }
    }
}