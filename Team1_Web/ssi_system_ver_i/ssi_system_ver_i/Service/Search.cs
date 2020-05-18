using ssi_system_ver_i.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ADprojectteam1.Service
{
    public class Search
    {
        public static searchResult Found(string ba, string ta)
        {

            string s = ba;
            int index = ba.IndexOf(ta, StringComparison.CurrentCultureIgnoreCase);
            if (index != -1)
            {

                s = ba.Substring(0, index) + "<span class='font-red'>" + ba.Substring(index, ta.Length) + "</span>" + ba.Substring(index + ta.Length);
            }

            return new searchResult { fit = (index != -1), str = s };

        }
    }
}