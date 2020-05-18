using ssi_system_ver_i.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ssi_system_ver_i.DAL
{
    public class StockcardData
    {
        internal static int GetStockBalanceByItemId(int itemId)
        {
            int stockbalance = 0;
            List<stock_card> list = new List<stock_card>();
            using (var db = new DataBaseContext())
            {

                if (db.stock_card_repository.Where(x => x.item_obj.itemId == itemId).Any())
                    list = db.stock_card_repository.Where(x => x.item_obj.itemId == itemId).ToList();
                stockbalance = list[list.Count - 1].stockbalance;
            }
            return stockbalance;
        }

        internal static List<stock_card> GetStockCardByItemId(int itemId)
        {

            List<stock_card> list = new List<stock_card>();
            using (var db = new DataBaseContext())
            {
                if (db.stock_card_repository.Where(x => x.item_obj.itemId == itemId).Any())
                    list = db.stock_card_repository.Where(x => x.item_obj.itemId == itemId).ToList();

            }
            return list;
        }
    }
}