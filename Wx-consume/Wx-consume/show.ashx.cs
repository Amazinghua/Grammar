using Aspose.Words;
using Aspose.Words.Saving;
using Newtonsoft.Json.Linq;
using System;
using System.IO;
using System.Text;
using System.Text.RegularExpressions;
using System.Web;


namespace Wx_consume
{
    /// <summary>
    /// show 的摘要说明
    /// </summary>
    public class show : IHttpHandler
    {

        public void ProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "text/plain";
            string result = "";
            string type = "";
            string name = "";
            try
            {
                string poststr = getpost();
                if (!string.IsNullOrEmpty(poststr))
                {
                    JObject json = new JObject();

                    JObject jobj = JObject.Parse(poststr);
                    type = jobject(jobj, "type");
                    name = jobject(jobj, "name");
                    switch (type)
                    {
                        case "getdata":

                            string filepath = System.Web.HttpContext.Current.Server.MapPath("~/examples/txt");//获取文件夹所在的地址
                            string str = GetTxt(filepath);
                            result = "{\"result\":\"000\",\"show\":\"" + str + "\"}";
                            break;
                        case "getpage":
                            string filepath2 = System.Web.HttpContext.Current.Server.MapPath("~/examples/txt/");//获取文件夹所在的地址
                            string showPage = TxtPage(filepath2, name);
                            result = "{\"result\":\"111\",\"show\":\"" + showPage + "\"}";
                            break;

                        case "getNews":
                            string Newspath = System.Web.HttpContext.Current.Server.MapPath("~/examples/news");
                            string News = GetTxt(Newspath);
                            result = "{\"result\":\"222\",\"show\":\"" + News + "\"}";
                            break;
                        case "Newshow":
                            string filepath1 = System.Web.HttpContext.Current.Server.MapPath("~/examples/news/");//获取文件夹所在的地址
                            string newsPage = TxtPage(filepath1, name);
                            result = "{\"result\":\"333\",\"show\":\"" + newsPage + "\"}";
                            break;

                        case "getWarn":
                            string Warnpath = System.Web.HttpContext.Current.Server.MapPath("~/examples/warn");
                            string Warns = GetTxt(Warnpath);
                            result = "{\"result\":\"444\",\"show\":\"" + Warns + "\"}";
                            break;
                        case "Warnshow":
                            string  filepath3 = System.Web.HttpContext.Current.Server.MapPath("~/examples/warn/");
                            string warnPage = TxtPage(filepath3, name);
                            result = "{\"result\":\"555\",\"show\":\"" + warnPage + "\"}";
                            break;

                        case "getConsume":
                            string Consumepath = System.Web.HttpContext.Current.Server.MapPath("~/examples/consume");
                            string Consumes = GetTxt(Consumepath);
                            result = "{\"result\":\"666\",\"show\":\"" + Consumes + "\"}";
                            break;
                        case "Consumeshow":
                            string filepath4 = System.Web.HttpContext.Current.Server.MapPath("~/examples/consume/");
                            string ConsumePage = TxtPage(filepath4, name);
                            result = "{\"result\":\"777\",\"show\":\"" + ConsumePage + "\"}";
                            break;

                        case "getLaws":
                            string Lawspath = System.Web.HttpContext.Current.Server.MapPath("~/examples/law");
                            string laws = GetTxt(Lawspath);
                            result = "{\"result\":\"888\",\"show\":\"" + laws + "\"}";
                            break;
                        case "Lawshow":
                            string filepath5 = System.Web.HttpContext.Current.Server.MapPath("~/examples/law/");
                            string lawsPage = TxtPage(filepath5, name);
                            result = "{\"result\":\"999\",\"show\":\"" + lawsPage + "\"}";
                            break;

                        case "getComp":
                            string compath = System.Web.HttpContext.Current.Server.MapPath("~/examples/complaints");
                            string comps = GetTxt(compath);
                            result = "{\"result\":\"200\",\"show\":\"" + comps + "\"}";
                            break;
                        case "Compshow":
                            string filepath6 = System.Web.HttpContext.Current.Server.MapPath("~/examples/complaints/");
                            string comPage = TxtPage(filepath6, name);
                            result = "{\"result\":\"300\",\"show\":\"" + comPage + "\"}";
                            break;
                        //以下是商家简讯
                        case "getcarr":
                            string carr = System.Web.HttpContext.Current.Server.MapPath("~/examples/business/jr");
                            string carrs = GetTxt(carr);
                            result = "{\"result\":\"00\",\"show\":\"" + carrs + "\"}";
                            break;
                        case"getrh":
                            string rh = System.Web.HttpContext.Current.Server.MapPath("~/examples/business/rh");
                            string rhs = GetTxt(rh);
                            result = "{\"result\":\"01\",\"show\":\"" + rhs + "\"}";
                            break;
                        case "getjx":
                            string jx = System.Web.HttpContext.Current.Server.MapPath("~/examples/business/jx");
                            string jxs = GetTxt(jx);
                            result = "{\"result\":\"02\",\"show\":\"" + jxs + "\"}";
                            break;
                        case "getyl":
                            string yl = System.Web.HttpContext.Current.Server.MapPath("~/examples/business/yl");
                            string yls = GetTxt(yl);
                            result = "{\"result\":\"03\",\"show\":\"" + yls + "\"}";
                            break;
                        case "getnl":
                            string nl = System.Web.HttpContext.Current.Server.MapPath("~/examples/business/nl");
                            string nls = GetTxt(nl);
                            result = "{\"result\":\"04\",\"show\":\"" + nls + "\"}";
                            break;
                        case "getls":
                            string ls = System.Web.HttpContext.Current.Server.MapPath("~/examples/business/ls");
                            string lss = GetTxt(ls);
                            result = "{\"result\":\"05\",\"show\":\"" + lss + "\"}";
                            break;
                        case "getwy":
                            string wy = System.Web.HttpContext.Current.Server.MapPath("~/examples/business/wy");
                            string wys = GetTxt(wy);
                            result = "{\"result\":\"06\",\"show\":\"" + wys + "\"}";
                            break;
                        case "getbc":
                            string bc = System.Web.HttpContext.Current.Server.MapPath("~/examples/business/bc");
                            string bcs = GetTxt(bc);
                            result = "{\"result\":\"07\",\"show\":\"" + bcs + "\"}";
                            break;
                        case "getbh":
                            string bh = System.Web.HttpContext.Current.Server.MapPath("~/examples/business/bh");
                            string bhs = GetTxt(bh);
                            result = "{\"result\":\"08\",\"show\":\"" + bhs + "\"}";
                            break;
                        case "getyc":
                            string yc = System.Web.HttpContext.Current.Server.MapPath("~/examples/business/yc");
                            string ycs = GetTxt(yc);
                            result = "{\"result\":\"09\",\"show\":\"" + ycs + "\"}";
                            break;
                        case "getht":
                            string ht = System.Web.HttpContext.Current.Server.MapPath("~/examples/business/ht");
                            string hts = GetTxt(ht);
                            result = "{\"result\":\"10\",\"show\":\"" + hts + "\"}";
                            break;
                        case "getcx":
                            string cx = System.Web.HttpContext.Current.Server.MapPath("~/examples/business/cx");
                            string cxs = GetTxt(cx);
                            result = "{\"result\":\"11\",\"show\":\"" + cxs + "\"}";
                            break;
                        case "getjz":
                            string jz = System.Web.HttpContext.Current.Server.MapPath("~/examples/business/jz");
                            string jzs = GetTxt(jz);
                            result = "{\"result\":\"12\",\"show\":\"" + jzs + "\"}";
                            break;
                        //读取商家简讯
                        case "gethtml":
                            string gethtml = System.Web.HttpContext.Current.Server.MapPath("~/examples/business/all/");
                            string getall = TxtPage(gethtml, name);
                            result = "{\"result\":\"100\",\"show\":\"" + getall + "\"}";
                            break;
                    }
                }

            }


            catch (Exception ex)
            {
                result = "{\"show\":" + ex.Message + "}";
            }
            context.Response.Write(result);
            context.Response.End();
        }

        public bool IsReusable
        {
            get
            {
                return false;
            }
        }
        public string GetTxt(string filepath)
        {
            string[] filenames = Directory.GetFiles(filepath, ("*.docx"));  //获取该文件夹下面的所有文件路径
            string[] filenames2 = new string[filenames.Length];
            for (int i = 0; i < filenames.Length; i++)
            {
                filenames2[i] = Path.GetFileNameWithoutExtension(filenames[i]);//获取文件名存入另一个数组
            }
            string str = "";
            str = string.Empty;
            str = string.Join(",", filenames2);//数组转成字符串
            return str;
        }
        public string TxtPage(string filepath, string name)
        {
            Aspose.Words.Document awd = new Aspose.Words.Document(filepath + name + ".docx");
            string savePath = System.Web.HttpContext.Current.Server.MapPath("~/html/");
            string saveFilePath = savePath + name + ".html";
            awd.Save(saveFilePath, Aspose.Words.SaveFormat.Html);

            string resultStr = string.Empty;
            string datas = string.Empty;
            string showPage = string.Empty;

            using (StreamReader sr = new StreamReader(saveFilePath, Encoding.UTF8))
            {
                resultStr = sr.ReadToEnd();
                resultStr = resultStr.Replace("\"", "\\\"");
                sr.Close();
                string[] data = resultStr.Split(new char[] { '\n', '\r' });
                datas = string.Join(",", data);//数组转成字符串
                Regex r = new Regex(@"<body.*>.*</body>");
                Match m = r.Match(datas);
                showPage = m.Value;
            }
            return showPage;
        }

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
        public string jobject(JObject jobj, string key)
        {
            string hh = "";
            if (jobj[key] != null)
            {
                hh = jobj[key].ToString().Trim();
            }
            return hh;
        }
    }
}