using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ssi_system_ver_i.Models;

namespace ssi_system_ver_i.DAL
{
    public class StaffData
    {
        internal static staff GetStaffByName(string user_name)
        {

            staff s = new staff();
            using (var db = new DataBaseContext())
            {

                if (db.staff_repository.Where(x => x.name.Equals(user_name)).Any())
                    s = db.staff_repository.Include("department_obj").Where(x => x.name.Equals(user_name)).FirstOrDefault();
            }
            return s;

        }

        internal static staff GetStaffById(int staff_representativeId)
        {
            staff s = new staff();
            using (var db = new DataBaseContext())
            {

                if (db.staff_repository.Where(x => x.staffId==staff_representativeId).Any())
                    s = db.staff_repository.Include("department_obj").Where(x => x.staffId==staff_representativeId).FirstOrDefault();
            }
            return s;
        }
    }
}