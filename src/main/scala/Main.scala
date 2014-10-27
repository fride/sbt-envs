object Main {

       def main(args: Array[String]): Unit = {
         println("Hello world, I got the following arguments: " + args.mkString(", "))
         println(f""" Stage: ${System.getProperty("stage")}""")        
       }
}
