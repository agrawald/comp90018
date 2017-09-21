using Microsoft.Owin;
using Owin;

[assembly: OwinStartup(typeof(NFCConnectionService.Startup))]

namespace NFCConnectionService
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureMobileApp(app);
        }
    }
}