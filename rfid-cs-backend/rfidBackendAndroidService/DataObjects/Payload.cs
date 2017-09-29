using System;
using System.Web.Http;
using Microsoft.Azure.Mobile.Server;

namespace rfidBackendAndroidService.DataObjects
{
    public class Payload : EntityData
    {
        public bool Authorized { get; set; }

        public static implicit operator Payload(SingleResult<Payload> v)
        {
            throw new NotImplementedException();
        }
    }
}