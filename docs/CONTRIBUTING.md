# Contribute to Stryker4s

This is the contribution guide for Stryker4s. Great to have you here! Here are a few ways you can help to make this project better.

## Adding a new feature

New features are welcome! Both as ideas or in the form of a pull request.

1. Please [create an issue](https://github.com/stryker-mutator/stryker4s/issues/new) with your idea first or let us know via [Gitter](https://gitter.im/stryker-mutator/stryker4s).
2. Create a fork on your GitHub account.
3. When writing your code, please conform the existing coding style. We use Scalafmt as a code formatter. You can format your code by running `./bin/scalafmt`, or with editor-specific settings. It also helps to take a moment to review the [Scala style guide](https://docs.scala-lang.org/style/).
4. Please create or edit unit/integration tests for any changed or added code.
5. Confirm everything still works by running `sbt test` (or let the CI do the work for you).
6. Submit the pull request!

Don't hesitate or get discouraged to get in touch! We are always happy to help you if you get stuck or have a question. Even if you don't finish something it can still be a good contribution.

## Running Stryker4s on Stryker4s

We support mutation testing Stryker4s with Stryker4s! The easiest way is to follow our guide in the [readme](../README.md#sbt-plugin). If you want to test any local changes, follow these steps:

1. Run `sbt sbt-stryker4s/publishLocal` to publish the snapshot version to your local ivy repository.
2. Note the version outputted by the log. Sbt will output something like `[info]  published sbt-stryker4s to $HOME/.ivy2/local/io.stryker-mutator/sbt-stryker4s/scala_2.12/sbt_1.0/0.6.1+31-85939087-SNAPSHOT/jars/sbt-stryker4s.jar`. In this case `0.6.1+31-85939087-SNAPSHOT` is the version you need (based on the commit hash and some other information, gathered by [`sbt-dynver`](https://github.com/dwijnand/sbt-dynver)).
   1. Alternatively, set the `version` in sbt to something easier before calling `sbt-stryker4s/publishLocal`
3. Add the sbt plugin to `project/plugins.sbt` with the new version number
4. Run stryker4s as described in the readme.

## Learning resources

Here are some resources you can use if you are new to mutation testing:

- [What is mutation testing?](https://stryker-mutator.io/) (and the rest of the website). On the Stryker mutator website.
- [Mutation Testing: Complete Guide - Guru99](https://www.guru99.com/mutation-testing.html)
- [Scala Days 2019 - Daniel Westheide - Testing in the postapocalyptic future](https://portal.klewel.com/watch/webcast/scala-days-2019/talk/18/)
- [TechDays 2017 - Simon de Lang - Using Mutation Testing to Improve your JavaScript Tests](https://youtu.be/ba_86FlRiKg)

## Mutation switching

Stryker4s uses a technique called 'mutation switching' to perform mutations. It does this by adding all mutations into a single pattern match, and activating the correct mutation via an environment variable. This would change the following code:

```scala
def isAdult(person: Person) = {
  person.age >= 18
}
```

To:

```scala
def isAdult(person: Person) = {
  sys.env.get("ACTIVE_MUTATION") match {
    case Some("1") => person.age > 18
    case Some("2") => person.age < 18
    case Some("3") => person.age == 18
    case _         => person.age >= 18 // Original
  }
}
```

The effect is the same as compiling each mutation seperately, but instead we only have to do it once. This is a big performance improvement, but does mean we have to be more careful about compile errors. Read more about mutation switching on [our blog](https://stryker-mutator.io/blog/2018-10-6/mutation-switching)

## Community

Want to help in some other ways? Great! Here are some things you could do:

- Evangelize mutation testing
  - Mutation testing is still relatively new, especially in Scala. Please help us get the word out there!
  - Share your stories in blog posts and on social media. And please let us know about it!
- Did you use Stryker4s? Your feedback is very valuable to us. Both good and bad! Please [contact us](https://gitter.im/stryker-mutator/stryker4s) to let us know what you think.
