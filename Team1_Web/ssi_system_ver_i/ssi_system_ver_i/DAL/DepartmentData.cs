using ssi_system_ver_i.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ssi_system_ver_i.DAL
{
    public class DepartmentData
    {
        internal static string GetRepresentativebyDepName(string name)
        {
            string repname = "";
            using (var db = new DataBaseContext())
            {

                if (db.department_repository.Where(x => x.department_name.Equals(name)).Any())
                    repname = db.department_repository.Where(x => x.department_name.Equals(name)).FirstOrDefault().representative_name;
            }
            return repname;
        }

        internal static dynamic GetAllDep()
        {
            List<department> list=new List<department>();
            using (var db = new DataBaseContext())
            {

                if (db.department_repository.Any())
                    list = db.department_repository.ToList();
            }
            return list;
        }

        internal static dynamic GetDepartmentById(int dId)
        {
            department d = new department();
            using (var db = new DataBaseContext())
            {

                if (db.department_repository.Where(x=>x.departmentId==dId).Any())
                    d = db.department_repository.Where(x=>x.departmentId==dId).FirstOrDefault();
            }
            return d;
        }
    }
}