/*§
  ===========================================================================
  LambdaPrism
  ===========================================================================
  Copyright (C) 2016 Gianluca Costa
  ===========================================================================
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ===========================================================================
*/

package info.gianlucacosta.lambdaprism.classification.basic

import org.scalatest.{FlatSpec, Matchers}


class TestClassificationProblem_Example extends FlatSpec with Matchers {
  val temperatureView =
    ExampleProblem.getView("Temperature")

  val humidityView =
    ExampleProblem.getView("Humidity")


  val testTree =
    temperatureView.createDecisionTree()


  "Total items count" should "be correct" in {
    ExampleProblem.table.length should be(17)
  }


  "Output values" should "be correct" in {
    ExampleProblem.outputValues should be(Set(
      "Pos",
      "Neg"
    ))
  }


  "Items count" should "be correct" in {
    ExampleProblem.getOutputCount("Pos") should be(10)
    ExampleProblem.getOutputCount("Neg") should be(7)
  }


  "Entropy" should "be correct" in {
    ExampleProblem.entropy should be(0.9774 +- 0.0001)
  }


  /*------------
   * TEMPERATURE
   *------------*/

  "Known items count" should "be correct for Temperature" in {
    temperatureView.getKnownItemsCount("Low")("Pos") should be(6)
    temperatureView.getKnownItemsCount("Low")("Neg") should be(1)

    temperatureView.getKnownItemsCount("High")("Pos") should be(2)
    temperatureView.getKnownItemsCount("High")("Neg") should be(5)
  }


  "Unknown items count" should "be correct for Temperature" in {
    temperatureView.getUnknownItemsCount("Pos") should be(2)
    temperatureView.getUnknownItemsCount("Neg") should be(1)
  }


  "Known items aggregate count" should "be correct for Temperature" in {
    temperatureView.getKnownItemsAggregateCount("Low") should be(7)
    temperatureView.getKnownItemsAggregateCount("High") should be(7)
  }


  "Known items total count" should "be correct for Temperature" in {
    temperatureView.knownItemsTotalCount should be(14)
  }


  "Unknown items total count" should "be correct for Temperature" in {
    temperatureView.unknownItemsTotalCount should be(3)
  }

  "Known and unknown items total count for Temperature" should "equal the problem's table size" in {
    temperatureView.knownItemsTotalCount + temperatureView.unknownItemsTotalCount should be(ExampleProblem.table.size)
  }

  "Entropy" should "be correct for Temperature" in {
    temperatureView.entropy should be(0.7274 +- 0.0001)
  }


  "Gain" should "be correct for Temperature" in {
    temperatureView.gain should be(0.2059 +- 0.0001)
  }


  /*---------
   * HUMIDITY
   *---------*/


  "Known items count" should "be correct for Humidity" in {
    humidityView.getKnownItemsCount("Low")("Pos") should be(4)
    humidityView.getKnownItemsCount("Low")("Neg") should be(3)

    humidityView.getKnownItemsCount("Medium")("Pos") should be(3)
    humidityView.getKnownItemsCount("Medium")("Neg") should be(2)

    humidityView.getKnownItemsCount("High")("Pos") should be(3)
    humidityView.getKnownItemsCount("High")("Neg") should be(2)
  }


  "Unknown items count" should "be correct for Humidity" in {
    humidityView.getUnknownItemsCount("Pos") should be(0)
    humidityView.getUnknownItemsCount("Neg") should be(0)
  }


  "Known items aggregate count" should "be correct for Humidity" in {
    humidityView.getKnownItemsAggregateCount("Low") should be(7)
    humidityView.getKnownItemsAggregateCount("Medium") should be(5)
    humidityView.getKnownItemsAggregateCount("High") should be(5)
  }


  "Known items total count" should "be correct for Humidity" in {
    humidityView.knownItemsTotalCount should be(17)
  }


  "Unknown items total count" should "be correct for Humidity" in {
    humidityView.unknownItemsTotalCount should be(0)
  }

  "Known and unknown items total count for Humidity" should "equal the problem's table size" in {
    humidityView.knownItemsTotalCount + humidityView.unknownItemsTotalCount should be(ExampleProblem.table.size)
  }

  "Entropy" should "be correct for Humidity" in {
    humidityView.entropy should be(0.9768 +- 0.0001)
  }


  "Gain" should "be correct for Humidity" in {
    humidityView.gain should be(0.0006 +- 0.0001)
  }


  /*--------------
   * DECISION TREE
   *--------------*/

  "Temperature's gain" should "be higher than Humidity's gain" in {
    temperatureView.gain should be > humidityView.gain
  }


  "The tree attribute" should "be correct" in {
    testTree.attribute should be("Temperature")
  }


  "Attribute value" should "be correct" in {
    testTree.leaves("Low").attributeValue should be("Low")
    testTree.leaves("High").attributeValue should be("High")
  }


  "Weight" should "be correct" in {
    testTree.leaves("Low").weight should be(0.5 +- 0.1)
    testTree.leaves("High").weight should be(0.5 +- 0.1)
  }


  "Output label" should "be correct" in {
    testTree.leaves("Low").outputLabel should be("Pos")
    testTree.leaves("High").outputLabel should be("Neg")
  }


  "Probabilistic count" should "be correct" in {
    testTree.leaves("Low").getProbabilisticCount("Pos") should be(7.0 +- 0.1)
    testTree.leaves("Low").getProbabilisticCount("Neg") should be(1.5 +- 0.1)

    testTree.leaves("High").getProbabilisticCount("Pos") should be(3.0 +- 0.1)
    testTree.leaves("High").getProbabilisticCount("Neg") should be(5.5 +- 0.1)
  }


  "Probabilistic correct count" should "be correct" in {
    testTree.leaves("Low").probabilisticCorrectCount should be(7.0 +- 0.1)
    testTree.leaves("High").probabilisticCorrectCount should be(5.5 +- 0.1)
  }


  "Probabilistic wrong count" should "be correct" in {
    testTree.leaves("Low").probabilisticWrongCount should be(1.5 +- 0.1)
    testTree.leaves("High").probabilisticWrongCount should be(3.0 +- 0.1)
  }


  "Total probabilistic count" should "be correct" in {
    testTree.leaves("Low").totalProbabilisticCount should be(8.5 +- 0.1)
    testTree.leaves("High").totalProbabilisticCount should be(8.5 +- 0.1)
  }


  "Output probability" should "be correct" in {
    testTree.leaves("Low").getOutputProbability("Pos") should be(0.8235 +- 0.0001)
    testTree.leaves("Low").getOutputProbability("Neg") should be(0.1765 +- 0.0001)

    testTree.leaves("High").getOutputProbability("Pos") should be(0.3529 +- 0.0001)
    testTree.leaves("High").getOutputProbability("Neg") should be(0.6471 +- 0.0001)
  }



  /*---------------
   * CLASSIFICATION
   *---------------*/


  "Classification" should "be correct when the attribute value in the instance is Low" in {
    val outputProbability =
      testTree.classify("Low")

    outputProbability("Pos") should be(0.8235 +- 0.0001)
    outputProbability("Neg") should be(0.1765 +- 0.0001)
  }


  "Classification" should "be correct when the attribute value in the instance is High" in {
    val outputProbability =
      testTree.classify("High")

    outputProbability("Pos") should be(0.3529 +- 0.0001)
    outputProbability("Neg") should be(0.6471 +- 0.0001)
  }


  "Classification" should "be correct when the attribute value in the instance is unknown" in {
    val outputProbability =
      testTree.classify("?")

    outputProbability("Pos") should be(0.5882 +- 0.0001)
    outputProbability("Neg") should be(0.4118 +- 0.0001)
  }
}