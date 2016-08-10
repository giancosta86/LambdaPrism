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

/**
  * Internal ClassificationView implementation
  *
  * @param problem
  * @param attribute
  * @param subtableMap
  */
private case class DefaultClassificationView(
                                              problem: ClassificationProblem,
                                              attribute: Attribute,
                                              subtableMap: Map[Value, Table]
                                            ) extends ClassificationView {

  private val (
    knownSubtableMap: Map[Value, Table],
    unknownSubtable: Table
    ) = {
    val (knownSubtableMap: Map[Value, Table], unknownSubtableMap: Map[Value, Table]) =
      subtableMap.partition {
        case (attributeValue, table) =>
          attributeValue != ClassificationProblem.UnknownValue
      }

    (
      knownSubtableMap,
      unknownSubtableMap.getOrElse(
        "?",
        List()
      )
      )
  }


  private val knownCountMap: Map[Value, Map[Value, Int]] =
    knownSubtableMap.map {
      case (attributeValue, subtable) =>
        val outputAttributeCountMap: Map[Value, Int] =
          subtable
            .groupBy(row => row.last)
            .map {
              case (outputValue, outputSubtable) =>
                outputValue -> outputSubtable.length
            }

        attributeValue -> outputAttributeCountMap
    }


  private val unknownCountMap: Map[Value, Int] =
    unknownSubtable
      .groupBy(_.last)
      .map {
        case (outputValue, outputSubtable) =>
          outputValue -> outputSubtable.length
      }


  override val attributeValues: Set[Value] =
    knownCountMap
      .keySet


  override def getKnownItemsCount(attributeValue: Value)(outputValue: Value): Int =
    knownCountMap
      .getOrElse(attributeValue, Map())
      .getOrElse(outputValue, 0)


  override def getKnownItemsAggregateCount(attributeValue: Value): Int =
    knownCountMap
      .getOrElse(attributeValue, Map())
      .map {
        case (_, count) =>
          count
      }
      .sum


  override def getUnknownItemsCount(outputValue: Value): Int =
    unknownCountMap
      .getOrElse(outputValue, 0)


  override val unknownItemsTotalCount: Int =
    unknownSubtable.length


  override val knownItemsTotalCount: Int =
    problem.table.length - unknownItemsTotalCount


  override val entropy: Double =
    knownSubtableMap
      .foldLeft(0.0) {
        case (cumulatedEntropy, (_, subtable)) =>
          cumulatedEntropy +
            subtable.length.toDouble / knownItemsTotalCount * Entropy.compute(subtable)
      }


  override val gain: Double =
    (knownItemsTotalCount.toDouble / problem.table.length) * (problem.entropy - entropy)


  override def createDecisionTree(): DefaultDecisionTree = {
    val treeLeaves: Map[Value, DefaultDecisionTreeLeaf] =
      knownCountMap
        .map {
          case (attributeValue, outputCountMap) =>
            val leafWeight: Double =
              getKnownItemsAggregateCount(attributeValue).toDouble / knownItemsTotalCount

            val probabilisticOutputCountMap: Map[Value, Double] =
              problem.outputValues
                .map(outputValue => {
                  val outputCount =
                    getKnownItemsCount(attributeValue)(outputValue)

                  val probabilisticCount: Double =
                    outputCount + leafWeight * getUnknownItemsCount(outputValue)

                  outputValue -> probabilisticCount
                }).toMap

            attributeValue -> DefaultDecisionTreeLeaf(
              problem,
              attributeValue,
              leafWeight,
              probabilisticOutputCountMap
            )
        }

    DefaultDecisionTree(
      problem,
      attribute,
      treeLeaves
    )
  }
}
