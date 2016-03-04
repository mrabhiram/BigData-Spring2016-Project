
name := "FeatureExtractionText1"

version := "1.0"

scalaVersion := "2.11.7"


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0" % "provided",
  "org.apache.spark" %% "spark-streaming" % "1.6.0",
  "org.apache.spark" %% "spark-streaming-twitter" % "1.6.0",
  "org.apache.spark" %% "spark-mllib" % "1.6.0",
  "org.apache.commons" % "commons-lang3" % "3.0",
  "org.scalaj" %% "scalaj-http" % "1.1.5",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.3.0",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.3.0" classifier "models"
)