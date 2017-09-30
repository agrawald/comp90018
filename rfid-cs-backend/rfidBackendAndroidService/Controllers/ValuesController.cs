using System.Web.Http;
using System.Web.Http.Tracing;
using Microsoft.Azure.Mobile.Server;
using Microsoft.Azure.Mobile.Server.Config;
using Microsoft.Azure.Devices;
using rfidBackendAndroidService.DataObjects;
using Newtonsoft.Json;
using System.Text;
using System.Threading.Tasks;

namespace rfidBackendAndroidService.Controllers
{
    // Use the MobileAppController attribute for each ApiController you want to use  
    // from your mobile clients 
    [MobileAppController]
    public class ValuesController : ApiController
    {
        static string connectionString = "HostName=iotHubRFID.azure-devices.net;SharedAccessKeyName=iothubowner;SharedAccessKey=uqyXHUSOf55Q28B3SzeSEob0liXCXwjv5ZHrwqp70X4=";
        static ServiceClient serviceClient;

        public ValuesController()
        {
            serviceClient = ServiceClient.CreateFromConnectionString(connectionString);
        }

        // GET api/values
        public string Get()
        {
            MobileAppSettingsDictionary settings = this.Configuration.GetMobileAppSettingsProvider().GetMobileAppSettings();
            ITraceWriter traceWriter = this.Configuration.Services.GetTraceWriter();

            string host = settings.HostName ?? "localhost";
            string greeting = "Hello from " + host;

            traceWriter.Info(greeting);
            return greeting;
        }

        // POST api/values
        public async Task<string> Post(Payload item)
        {
            var json = JsonConvert.SerializeObject(item);
            var commandMessage = new Message(Encoding.ASCII.GetBytes(json));
            await serviceClient.SendAsync("rfid_raspberry_pi", commandMessage);
            return "Done";
        }
    }
}
