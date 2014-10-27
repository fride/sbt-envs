import sbt._
import Keys._

object ExampleBuild extends Build {

  lazy val dev   = config("dev")   extend(Runtime)  describedAs("dev environment settings")   hide
  lazy val stage = config("stage") extend(Runtime)  describedAs("stage environment settings") hide
  lazy val prod  = config("prod")  extend(Runtime)  describedAs("prod environment settings")  hide

  // from te sbt faq: http://www.scala-sbt.org/0.12.2/docs/faq.html
  lazy val myRunTask = TaskKey[Unit]("my-run-task")
   
  def stagedRunSettings(stage: Configuration, prop: String, args: List[String]) = 
    inConfig(stage) (Seq(
    fork in myRunTask := true,
    javaOptions in myRunTask += s"-Dstage=${prop}",
    fullRunTask(myRunTask, Test, "Main", args : _*)
  ))

  lazy val root = Project("project", file("."))
    .configs(dev, stage, prod)
    .settings(
      organization := "org.gutencode",
      version := "0.8.15",
      javaOptions += "-XX:+PrintCommandLineFlags",
      fork in run := true
    )
    .settings(stagedRunSettings(dev, "dev", "dev" :: "example" :: Nil) : _*)
    .settings(stagedRunSettings(stage, "stage", "stage" :: "example" :: Nil) : _*)
    .settings(stagedRunSettings(prod, "prod", "prod" :: "example" :: "With more text" :: Nil) : _*)    
}