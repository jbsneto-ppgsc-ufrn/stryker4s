lazy val root = (project withId "stryker4s" in file("."))
  .settings(
    Settings.buildLevelSettings,
    skip in publish := true,
    onLoad in Global ~= (_ andThen ("writeHooks" :: _))
  )
  .aggregate(stryker4sCore, sbtStryker4s, stryker4sCommandRunner)
  .dependsOn(stryker4sCommandRunner) // So 'run' command also works from root of project

lazy val stryker4sCore = newProject("stryker4s-core", "core")
  .settings(Settings.coreSettings)

lazy val stryker4sCommandRunner = newProject("stryker4s-command-runner", "runners/command-runner")
  .settings(
    Settings.commandRunnerSettings,
    mainClass in (Compile, run) := Some("stryker4s.run.Stryker4sCommandRunner")
  )
  .dependsOn(stryker4sCore, stryker4sCore % "test->test")

lazy val sbtStryker4s = newProject("sbt-stryker4s", "runners/sbt")
  .enablePlugins(SbtPlugin)
  .settings(Settings.sbtPluginSettings)
  .dependsOn(stryker4sCore)

def newProject(projectName: String, dir: String): Project =
  sbt
    .Project(projectName, file(dir))
    .settings(Settings.commonSettings)

lazy val writeHooks = taskKey[Unit]("Write git hooks")
Global / writeHooks := GitHooks(file("git-hooks"), file(".git/hooks"), streams.value.log)
