using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Mail;
using System.Text;
using System.Web;

namespace ssi_system_ver_i.Service
{
    public class EmailNotification
    {
        public static void SendNotificationEmailToEmployee(string toAddress, string subject, string content)
        {
            string to = "test99999999999@mail.com";
            string from = "shawn.won@hotmail.com";
            MailMessage message = new MailMessage(from, to);

            string mailbody = content;
            message.Subject = subject;
            message.Body = mailbody;
            message.BodyEncoding = Encoding.UTF8;
            message.IsBodyHtml = true;

            SmtpClient client = new SmtpClient("smtp.gmail.com", 587);
            System.Net.NetworkCredential basicCredential1 = new System.Net.NetworkCredential("shawn.wang12345@gmail.com", "unbearablelightness");
            client.EnableSsl = true;
            client.UseDefaultCredentials = false;
            client.Credentials = basicCredential1;


            try
            {
                client.Send(message);
            }
            catch (Exception ex)
            { throw ex; }

        }
    }
}