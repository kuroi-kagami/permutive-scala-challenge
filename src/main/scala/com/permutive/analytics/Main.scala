package com.permutive.analytics

import java.nio.file.{Path, Paths}
import java.util.concurrent.Executors

import cats.effect._
import cats.implicits._
import com.permutive.analytics.messaging.Queue
import fs2._

import scala.concurrent.ExecutionContext

object Main extends IOApp {
  private val blockingExecutionContext =
    Resource.make(IO(
      ExecutionContext.fromExecutorService(
        Executors.newFixedThreadPool(2))))(ec => IO(ec.shutdown()))

  def app(path: Path) =
    Stream.resource(blockingExecutionContext).flatMap { blockingEC =>
      Queue.linesFromFile[IO](path, blockingEC, 4096).messages
        .evalMap[IO, Unit](s => IO { println("Received message: " + s) })
    }

  def run(args: List[String]): IO[ExitCode] = {
    val path =
      args.headOption
        .map(s => Paths.get(s))
        .getOrElse(throw new Exception("Please provide a valid input file"))

    app(path).compile.drain.as(ExitCode.Success)
  }
}