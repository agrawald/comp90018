using System.Linq;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Controllers;
using System.Web.Http.OData;
using Microsoft.Azure.Mobile.Server;
using rfidBackendAndroidService.DataObjects;
using rfidBackendAndroidService.Models;
using Microsoft.Azure.Devices;
using Newtonsoft.Json;
using System.Text;

namespace rfidBackendAndroidService.Controllers
{
    public class RfidController : TableController<Payload>
    {
        static string connectionString = "HostName=iotHubRFID.azure-devices.net;DeviceId=rfid_raspberry_pi;SharedAccessKey=W3r5MdNny6SIgcTveH2ec2JZtWAO6WhXg6yh9Bp6+9M=";
        static ServiceClient serviceClient;

        protected override void Initialize(HttpControllerContext controllerContext)
        {
            base.Initialize(controllerContext);
            rfidBackendAndroidContext context = new rfidBackendAndroidContext();
            DomainManager = new EntityDomainManager<Payload>(context, Request);
            serviceClient = ServiceClient.CreateFromConnectionString(connectionString);
        }

        // GET tables/Rfid
        public IQueryable<Payload> GetAllRfids()
        {
            return Query();
        }

        // GET tables/Rfid/48D68C86-6EA6-4C25-AA33-223FC9A27959
        public SingleResult<Payload> GetRfid(string id)
        {
            return Lookup(id);
        }

        // PATCH tables/Rfid/48D68C86-6EA6-4C25-AA33-223FC9A27959
        public Task<Payload> PatchRfid(string id, Delta<Payload> patch)
        {
            return UpdateAsync(id, patch);
        }

        // POST tables/Rfid
        public async Task<IHttpActionResult> PostRfid(Payload item)
        {
            Payload current = await InsertAsync(item);
            //var json = JsonConvert.SerializeObject(current);
            //var commandMessage = new Message(Encoding.ASCII.GetBytes(json));
            //await serviceClient.SendAsync("rfid_raspberry_pi", commandMessage);
            return CreatedAtRoute("Tables", new { id = current.Id }, current);
        }

        // DELETE tables/Rfid/48D68C86-6EA6-4C25-AA33-223FC9A27959
        public Task DeleteRfid(string id)
        {
            return DeleteAsync(id);
        }
    }
}