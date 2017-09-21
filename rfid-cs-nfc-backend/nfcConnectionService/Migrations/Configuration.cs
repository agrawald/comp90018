namespace NFCConnectionService.Migrations
{
    using Microsoft.Azure.Mobile.Server.Tables;
    using NFCConnectionService.DataObjects;
    using System;
    using System.Data.Entity;
    using System.Collections.Generic;
    using System.Data.Entity.Migrations;
    using System.Linq;

    internal sealed class Configuration : DbMigrationsConfiguration<NFCConnectionService.Models.NFCConnectionContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
           // SetSqlGenerator("System.Data.SqlClient", new EntityTableSqlGenerator());
        }

        protected override void Seed(NFCConnectionService.Models.NFCConnectionContext context)
        {
            //  This method will be called after migrating to the latest version.

             List<AutoTag>  autoTags = new List<AutoTag>
             {
                  new AutoTag { Id = Guid.NewGuid().ToString(), Authorized = false },
                  new AutoTag { Id = Guid.NewGuid().ToString(), Authorized = false },
              };

             foreach (AutoTag autoTag in  autoTags)
             {
                  context.Set<AutoTag>().Add(autoTag);
              }

               base.Seed(context);
        }
    }
}
