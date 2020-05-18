using ssi_system_ver_i.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ssi_system_ver_i.DAL
{
    public class StaffRepData
    {

        public static List<staff_representative> GetAllNotExpired()
        {
            List<staff_representative> list = new List<staff_representative>();
            using (var db = new DataBaseContext())
            {

                if (db.staff_representative_repository.Where(x => !x.status.Equals("Expired")).Any())
                    list = db.staff_representative_repository.Include("representative_staff_obj").Where(x => !x.status.Equals("Expired")).ToList();
            }
            return list;
        }


        public static void StartDelegation(int empId)
        {
            staff_representative staffrep = new staff_representative();
            using (var db = new DataBaseContext())
            {

                if (db.staff_representative_repository.Where(x => x.representative_staff_obj.staffId == empId).Any())
                    staffrep = db.staff_representative_repository.Where(x => x.representative_staff_obj.staffId == empId).FirstOrDefault();
                staffrep.position = "Temporary_Head";
                staffrep.status = "Ongoing";
                db.SaveChanges();
            }

        }

        public static void EndDelegation(int empId)
        {
            staff_representative staffrep = new staff_representative();
            using (var db = new DataBaseContext())
            {

                if (db.staff_representative_repository.Where(x => x.representative_staff_obj.staffId == empId).Any())
                    staffrep = db.staff_representative_repository.Where(x => x.representative_staff_obj.staffId == empId).FirstOrDefault();
                staffrep.status = "Expired";
                db.SaveChanges();
            }

        }
    }
}