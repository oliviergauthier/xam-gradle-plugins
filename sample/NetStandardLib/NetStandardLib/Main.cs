using System;
using Newtonsoft.Json;

namespace NetStandardLib
{
    public class Main
    {

        class Person {

            public string FirstName { get; set; }

            public string LastName { get; set; }

            public string FullName => $"{FirstName} {LastName}";

        }

        public static void Run(string[] args) {
            var person = new Person { FirstName = "John", LastName = "Doe" };
            var serialized = JsonConvert.SerializeObject(person);

            Console.WriteLine("Hello World !!! " + serialized);
        }
    }
}
