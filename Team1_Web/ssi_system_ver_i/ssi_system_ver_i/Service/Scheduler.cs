using ssi_system_ver_i.DAL;
using ssi_system_ver_i.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FluentScheduler;


namespace ssi_system_ver_i.Service
{
    public class MyScheduler : Registry
    {
        public MyScheduler()
        {


            // Schedule a simple job to run at a specific time
            Schedule(() => DailyCheckDelegation()).ToRunEvery(1).Days().At(01, 00);
        }

        public static void DailyCheckDelegation()
        {


            List<staff_representative> delelist = StaffRepData.GetAllNotExpired().ToList();

            foreach (staff_representative rep in delelist)
            {
                string datetoday = DateTime.Today.Date.ToString("yyyy-MM-dd");

                if (rep.start_date.Equals(datetoday))
                {
                    StaffRepData.StartDelegation(rep.representative_staff_obj.staffId);
                }
                if (rep.end_date.Equals(datetoday))
                {
                    StaffRepData.EndDelegation(rep.representative_staff_obj.staffId); ;
                }
            }

        }
    }


}