using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.IO;
using System.Text;
using System.Data.SqlClient;
using System.Configuration;
using System.Data;
using System.Net;




namespace BAGConceptFinal
{
    public partial class RedirectRewrite : System.Web.UI.Page
    {
        //string array that stores parsed csv file
        string[] gErrorsURLs;

        string conString = ConfigurationManager.ConnectionStrings["Bvc5Database"].ConnectionString;

        string skuLookup_sp = "sp_mproc_CustomURL_bySKU_s";
        string[] combinedurls;

        string DateFormat = "yyyy-MM-d-h_m";

        string gErrorOutfName = "Google_Unprocessable";



        protected void Page_Load(object sender, EventArgs e)
        {
            string[] googleErrors = openFile(Path.GetFullPath(Server.MapPath("~/baduris.csv")));
            string[] redirectRules = openFile(Path.GetFullPath(Server.MapPath("~/redirects.txt")));
            InitialCountGE.Text = (googleErrors.Length).ToString();
            InitialCountRW.Text = (redirectRules.Length).ToString();
            combinedurls = mergeFiles(googleErrors, redirectRules);
        }

        //Opens file and parses, creates a string of each comma delimited token and inserts into an array. The parse token can be changed by changing the value of the "del" char array
        public string[] openFile(string fileName)
        {
            try
            {
                using (StreamReader rdr = new StreamReader(fileName))
                {
                    List<string> templist = new List<string>();
                    //checks if the file is a csv
                    if (fileName.Contains(".csv"))
                    {
                        string gErrorsFile = rdr.ReadToEnd();
                        gErrorsURLs = gErrorsFile.Split(',');

                        foreach (string s in gErrorsURLs)
                        {
                            if (s == "")
                            {
                            }
                            else
                            {
                                templist.Add(s);
                            }

                        }
                        gErrorsURLs = templist.ToArray();
                    }
                    else
                    {
                        while (!rdr.EndOfStream)
                        {

                            templist.Add(rdr.ReadLine());

                        }
                        gErrorsURLs = cleanRedirectRules(templist.ToArray());
                    }

                    return gErrorsURLs;

                }
            }

            finally
            {
            }

        }
        //Strips redirect rules from file and returns an array of the original rules
        public string[] cleanRedirectRules(string[] redirectRules)
        {
            List<string> cleanRedirects = new List<string>();

            foreach (string s in redirectRules)
            {
                try
                {
                    cleanRedirects.Add(s.Substring(s.IndexOf("RewriteRule ^/"), s.IndexOf(".aspx")));
                }
                finally
                {
                }
            }
            return cleanRedirects.ToArray();
        }

        public string[] cleanGoogleErrors(string[] gErrors)
        {
            List<string> gUnableToProcess = new List<string>();
            List<string> gToProcess = new List<string>();

            foreach (string s in gErrors)
            {
                if (s.Contains("__") & s.Contains(".com") & s.Contains(".aspx"))
                {

                    gToProcess.Add(s.Substring(s.IndexOf(".com") + 4, s.IndexOf(".aspx") - s.IndexOf(".com") - 4));
                }
                else
                {
                    gUnableToProcess.Add(s);
                }
            }
            createOutPutFiles(gUnableToProcess, gErrorOutfName);
            return gToProcess.ToArray();
        }



        //takes an array of strings and checks to see if they contain a sku, if they contain a sku the sku is parsed out of the string and the SKU is used to call the stored procedure to return the conanical url from the database. Once this url is returned the old url and the new URL are used to write a redirect rule. If the URL passed in does not contain a sku, or if an empty value is returned from the stored procedure the url is added to the unhandled url stringbuilder and written to the "UnableToResolve.txt" file. A count of the successful urls is also returned to the view upon completion. The handled urls are wriiten to the "redirects.txt" file.
        private void createRedirects(string[] urlErrors)
        {
            int completeCount = 0;

            StringBuilder redirects = new StringBuilder();

            foreach (string s in urlErrors)
            {

                //checks to see if a sku is present
                if (s.Contains("__"))
                {
                    //parses the sku out of the url and calls the stored procedure
                    string newurl = lookupURL(s.Substring(s.IndexOf("__") + 2, s.IndexOf(".aspx") - 2 - (s.IndexOf("__"))));
                    //checks to make sure the stored procedure does not return and empty value
                    if (newurl.Equals(""))
                    {

                        //unhandledUrls.Append(s);

                    }

                    else
                    {
                        string oldurl = s.Substring(0, s.IndexOf(".aspx"));
                        //checks to make sure the original url is valid and writes the redirect
                        if (oldurl.Contains(".com"))
                        {
                            oldurl = oldurl.Substring(s.IndexOf(".com") + 5, s.IndexOf(".aspx") - 5 - (s.IndexOf(".com")));
                            redirects.Append("RewriteRule ^/" + oldurl + "\\.aspx$ " + newurl + "[I,R=301]" + Environment.NewLine);
                            completeCount++;
                        }

                    }
                }
                else
                {

                    //unhandledUrls.Append(s);

                }
            }

           // createOutPutFiles(redirects, unhandledUrls);

            complete.Text = completeCount.ToString();

        }
        public string[] mergeFiles(string[] googleErrors, string[] redirectRules)
        {

            IEnumerable<string> combinedErrors = googleErrors.Union(redirectRules);

            return combinedErrors.ToArray();

        }
        private void createOutPutFiles(ICollection<string> fileToWrite, string fileName)
        {
            StreamWriter fs = new System.IO.StreamWriter(Path.GetFullPath(Server.MapPath(System.DateTime.Now.ToString(DateFormat) + fileName)), true);
            fs.Write(fileToWrite.ToString());
            fs.Close();
        }

        //calls stored procedure
        protected string lookupURL(string sku)
        {
            string url;
            try
            {
                using (SqlConnection conn = new SqlConnection(conString))
                {
                    conn.Open();
                    using (SqlCommand cmd = conn.CreateCommand())
                    {
                        cmd.CommandType = CommandType.StoredProcedure;
                        cmd.CommandText = skuLookup_sp;
                        cmd.Parameters.Add("@SKU", SqlDbType.NVarChar, 50).Value = sku;
                        SqlDataReader rdr = cmd.ExecuteReader();
                        rdr.Read();
                        if (rdr.HasRows)
                        {
                            url = rdr.GetString(0);
                        }
                        else
                        {
                            url = "";
                        }
                    }
                }
            }
            finally
            {
            }
            return url;
        }
        protected void Button2_Click(object sender, EventArgs e)
        {
            createRedirects(combinedurls);
        }
    }
}