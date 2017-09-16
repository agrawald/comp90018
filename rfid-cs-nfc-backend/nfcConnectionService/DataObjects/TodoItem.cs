using Microsoft.Azure.Mobile.Server;

namespace NFCConnectionService.DataObjects
{
    public class TodoItem : EntityData
    {
        public string Id { get; set; }
        public string Text { get; set; }

        public bool Complete { get; set; }
    }
}