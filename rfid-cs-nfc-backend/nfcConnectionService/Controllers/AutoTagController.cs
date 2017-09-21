using System.Linq;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Controllers;
using System.Web.Http.OData;
using Microsoft.Azure.Mobile.Server;
using NFCConnectionService.DataObjects;
using NFCConnectionService.Models;
using Microsoft.Azure.Devices;
using System.Text;
using Newtonsoft.Json;

namespace NFCConnectionService.Controllers
{
    public class AutoTagController : TableController<AutoTag>
    {
        static ServiceClient serviceClient;
        static string connectionString = "HostName=iotHubRFID.azure-devices.net;DeviceId=rfid_raspberry_pi;SharedAccessKey=+0+mQi58+whcEiUpLbU/yRT/jQymBNoi+h1oRvEb3bU=";

        protected override void Initialize(HttpControllerContext controllerContext)
        {
            base.Initialize(controllerContext);
            NFCConnectionContext context = new NFCConnectionContext();
            DomainManager = new EntityDomainManager<AutoTag>(context, Request);
            serviceClient = ServiceClient.CreateFromConnectionString(connectionString);
        }

        // GET tables/AutoTag
        public IQueryable<AutoTag> GetAllAutoTags()
        {
            return Query();
        }

        // GET tables/AutoTag/48D68C86-6EA6-4C25-AA33-223FC9A27959
        public SingleResult<AutoTag> GetAutoTag(string id)
        {
            return base.Lookup(id);
        }

        // POST tables/AutoTag
        public async Task<IHttpActionResult> PostAutoTag(AutoTag item)
        {
            AutoTag current = await InsertAsync(item);
            var json = JsonConvert.SerializeObject(current);
            var commandMessage = new Message(Encoding.ASCII.GetBytes(json));
            await serviceClient.SendAsync("rfid-raspberry-pi", commandMessage);
            return CreatedAtRoute("Tables", new { id = current.Id }, current);
        }
    }
}