namespace nfcConnectionService.Migrations
{
    using Microsoft.Azure.Mobile.Server.Tables;
    using nfcConnectionService.DataObjects;
    using System;
    using System.Data.Entity;
    using System.Collections.Generic;
    using System.Data.Entity.Migrations;
    using System.Linq;

    internal sealed class Configuration : DbMigrationsConfiguration<nfcConnectionService.Models.nfcConnectionContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
           // SetSqlGenerator("System.Data.SqlClient", new EntityTableSqlGenerator());
        }

        protected override void Seed(nfcConnectionService.Models.nfcConnectionContext context)
        {
            //  This method will be called after migrating to the latest version.

            //  You can use the DbSet<T>.AddOrUpdate() helper extension method 
            //  to avoid creating duplicate seed data. E.g.
            //
            //    context.People.AddOrUpdate(
            //      p => p.FullName,
            //      new Person { FullName = "Andrew Peters" },
            //      new Person { FullName = "Brice Lambson" },
            //      new Person { FullName = "Rowan Miller" }
            //    );
            //

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
