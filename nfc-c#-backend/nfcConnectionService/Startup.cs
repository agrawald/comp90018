using Microsoft.Owin;
using Owin;

[assembly: OwinStartup(typeof(nfcConnectionService.Startup))]

namespace nfcConnectionService
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureMobileApp(app);
        }
    }
}