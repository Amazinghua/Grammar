using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace JMPOShow02
{
    public partial class getdata : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            string postbackdata = "";
            string poststr = getpost();
            string appimgname = "";
            if (!string.IsNullOrEmpty(poststr))
            {
                JObject jobj = JObject.Parse(poststr);
                string types = jobject(jobj, "type");
                appimgname = jobject(jobj, "appimgname");
                switch (types)
                {
                    case "appimgname":
                        postbackdata = imgname();
                        break;
                }

            }
            Response.Write(postbackdata);
        }

        public static string imgname()
        {
            string result = "";
            //string imgurlfile = System.Configuration.ConfigurationManager.AppSettings["indexPic"];
            string filepath = "~/images/";
            string severpath = HttpContext.Current.Server.MapPath(filepath);
            //imgurlfile = System.Web.HttpContext.Current.Server.MapPath(imgurlfile);
            try
            {
                //string[] rootFiles = Directory.GetFiles(severpath);
                //foreach (string s2 in rootFiles)
                //{
                //    result += Path.GetFileName(s2) + "|";
                //}
                DirectoryInfo folder = new DirectoryInfo(severpath);
                FileInfo[] files = folder.GetFiles();
                int imgLen = files.Length;
                for (int i = 0; i <= imgLen - 1; i++)
                {
                    string n = files[i].Name;
                    if (i < imgLen - 1)
                    {
                        result += n + "|";
                    }
                    else
                    {
                        result += n;
                    }
                }
            }

            catch (Exception ex)
            {
                result = ex.Message.ToString();
            }
            return result;
        }

        #region

        /// <summary>
        /// 这个也是转换为json不过是用系统的
        /// </summary>
        /// <param name="dt"></param>
        /// <returns></returns>
        public string DataTableToJson(DataTable dt)
        {
            string result = "";
            result = JsonConvert.SerializeObject(dt, new DataTableConverter());
            //new DataTableConverter()一般是固定的
            return result;
        }
        /// <summary>     
        /// dataTable转换成Json格式     
        /// </summary>     
        /// <param name="dt"></param>     
        /// <returns></returns>     
        public static string tableToJson(DataTable dt, string tablename)
        {
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.Append("\"");
            jsonBuilder.Append(tablename);
            jsonBuilder.Append("\":[");
            if (dt.Rows.Count > 0)
            {
                for (int i = 0; i < dt.Rows.Count; i++)
                {
                    jsonBuilder.Append("{");
                    for (int j = 0; j < dt.Columns.Count; j++)
                    {
                        jsonBuilder.Append("\"");
                        jsonBuilder.Append(dt.Columns[j].ColumnName.ToLower());
                        jsonBuilder.Append("\":\"");
                        if (dt.Columns[j].DataType == typeof(DateTime))
                        {
                            String v = dt.Rows[i][j].ToString().Trim();
                            if (v != "")
                            {
                                DateTime d = DateTime.Now;
                                DateTime.TryParse(v, out d);
                                v = d.ToString("yyyy-MM-dd HH:mm:ss");
                            }
                            jsonBuilder.Append(v);
                        }
                        else
                        {
                            String v = dt.Rows[i][j].ToString().Trim();
                            v = v.Replace("\"", "\\\"").Replace("\r", "").Replace("\n", "").Replace("\\", "\\\\");
                            jsonBuilder.Append(v);
                        }
                        jsonBuilder.Append("\",");
                    }

                    jsonBuilder.Remove(jsonBuilder.Length - 1, 1);
                    jsonBuilder.Append("},");
                }
            }
            else
            {
                jsonBuilder.Append(",");
            }
            jsonBuilder.Remove(jsonBuilder.Length - 1, 1);

            jsonBuilder.Append("]");
            //jsonBuilder.Append("}");
            return jsonBuilder.ToString();
        }

        /// <summary>
        /// 获取数据流
        /// </summary>
        /// <returns></returns>
        public string getpost()
        {
            string g = "";
            if (HttpContext.Current.Request.InputStream != null)
            {
                System.IO.StreamReader sr = new System.IO.StreamReader(HttpContext.Current.Request.InputStream, System.Text.Encoding.UTF8);
                g = sr.ReadToEnd();
            }
            return g;
        }
        /// <summary>
        /// 从jobject中获取相应的数据 
        /// </summary>
        /// <param name="jobj">jobject对象</param>
        /// <param name="key">要获取的值</param>
        /// <returns></returns>
        public string jobject(JObject jobj, string key)
        {
            string hh = "";
            if (jobj[key] != null)
            {
                hh = jobj[key].ToString().Trim();
            }
            return hh;
        }

        #endregion
    }
}