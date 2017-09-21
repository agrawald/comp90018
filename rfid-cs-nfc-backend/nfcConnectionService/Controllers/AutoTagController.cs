using System.Linq;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Controllers;
using System.Web.Http.OData;
using Microsoft.Azure.Mobile.Server;
using nfcConnectionService.DataObjects;
using nfcConnectionService.Models;


namespace NFCConnectionService.Controllers
{
    public class AutoTagController : TableController<AutoTag>
    {
        protected override void Initialize(HttpControllerContext controllerContext)
        {
            base.Initialize(controllerContext);
            nfcConnectionContext context = new nfcConnectionContext();
            DomainManager = new EntityDomainManager<AutoTag>(context, Request);
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

        // PATCH tables/AutoTag/48D68C86-6EA6-4C25-AA33-223FC9A27959
        public Task<AutoTag> PatchAutoTag(string id, Delta<AutoTag> patch)
        {
            return UpdateAsync(id, patch);
        }

        // POST tables/AutoTag
        public async Task<IHttpActionResult> PostAutoTag(AutoTag item)
        {
            AutoTag current = await InsertAsync(item);
            return CreatedAtRoute("Tables", new { id = current.Id }, current);
        }

        // DELETE tables/AutoTag/48D68C86-6EA6-4C25-AA33-223FC9A27959
        public Task DeleteAutoTag(string id)
        {
            return DeleteAsync(id);
        }
    }
}