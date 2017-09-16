namespace nfcConnectionService.Migrations
{
    using nfcConnectionService.DataObjects;
    using System;
    using System.Collections.Generic;

    internal sealed class Configuration : DbMigrationsConfiguration<nfcConnectionService.Models.nfcConnectionContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
      //      SetSqlGenerator("System.Data.SqlClient", new EntityTableSqlGenerator());
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

            //    context.TodoItems.AddOrUpdate(
           //      p => p.Id,
            //      new TodoItem { Id = Guid.NewGuid().ToString(), Text = "Andrew Peters", Complete=true }
             //  );

            List<TodoItem> todoItems = new List<TodoItem>
            {
                new TodoItem { Id = Guid.NewGuid().ToString(), Text = "First item", Complete = false },
                new TodoItem { Id = Guid.NewGuid().ToString(), Text = "Second item", Complete = false },
            };

            foreach (TodoItem todoItem in todoItems)
            {
                context.Set<TodoItem>().Add(todoItem);
            }

            base.Seed(context);

        }
    }
}
