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

namespace NFCConnectionService.Controllers
{
    public class TodoItemController : TableController<TodoItem>
    {
        // const string connectionString = "MS_AzureStorageAccountConnectionString";
        //const string tableName = "TodoItems;
        static ServiceClient serviceClient;
        static string connectionString = "HostName=iotHubRFID.azure-devices.net;DeviceId=rfid_raspberry_pi;SharedAccessKey=+0+mQi58+whcEiUpLbU/yRT/jQymBNoi+h1oRvEb3bU=";


        protected override void Initialize(HttpControllerContext controllerContext)
        {
            base.Initialize(controllerContext);
            NFCConnectionContext context = new NFCConnectionContext();
            DomainManager = new EntityDomainManager<TodoItem>(context, Request);
            serviceClient = ServiceClient.CreateFromConnectionString(connectionString);
        }

        // GET tables/TodoItem
        public IQueryable<TodoItem> GetAllTodoItems()
        {
            return Query();
        }

        // GET tables/TodoItem/48D68C86-6EA6-4C25-AA33-223FC9A27959
        public SingleResult<TodoItem> GetTodoItem(string id)
        {
            return base.Lookup(id);
        }

        // PATCH tables/TodoItem/48D68C86-6EA6-4C25-AA33-223FC9A27959
        public Task<TodoItem> PatchTodoItem(string id, Delta<TodoItem> patch)
        {
            return UpdateAsync(id, patch);
        }

        // POST tables/TodoItem
        public async Task<IHttpActionResult> PostTodoItem(TodoItem item)
        {
            TodoItem current = await InsertAsync(item);
            return CreatedAtRoute("Tables", new { id = current.Id }, current);
        }

        // POST rfid/authorized
        private async static Task PostRfidAuthorized()
        {
            var commandMessage = new Message(Encoding.ASCII.GetBytes("Cloud to device message."));
            await serviceClient.SendAsync("rfid-raspberry-pi", commandMessage);
        }
        // DELETE tables/TodoItem/48D68C86-6EA6-4C25-AA33-223FC9A27959
        public Task DeleteTodoItem(string id)
        {
            return DeleteAsync(id);
        }
    }
}