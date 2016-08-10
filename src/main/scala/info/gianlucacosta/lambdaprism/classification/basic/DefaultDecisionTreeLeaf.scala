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
  * Default DecisionTreeLeaf implementation
  *
  * @param problem
  * @param attributeValue
  * @param weight
  * @param probabilisticOutputCountMap
  */
private case class DefaultDecisionTreeLeaf(
                                            problem: ClassificationProblem,
                                            attributeValue: Value,
                                            weight: Double,
                                            probabilisticOutputCountMap: Map[Value, Double]
                                          ) extends DecisionTreeLeaf {
  override val outputLabel: Value =
    probabilisticOutputCountMap
      .toList
      .sortBy(_._2)
      .last
      ._1


  override def getProbabilisticCount(outputValue: Value): Double =
    probabilisticOutputCountMap.getOrElse(outputValue, 0)


  override val totalProbabilisticCount: Double =
    probabilisticOutputCountMap
      .foldLeft(0.0) {
        case (cumulatedTotalProbabilisticCount, (_, probabilisticCount)) =>
          cumulatedTotalProbabilisticCount + probabilisticCount
      }


  override val probabilisticCorrectCount: Double =
    probabilisticOutputCountMap(outputLabel)


  override val probabilisticWrongCount: Double =
    totalProbabilisticCount - probabilisticCorrectCount


  override def getOutputProbability(outputValue: Value): Double =
    getProbabilisticCount(outputValue) / totalProbabilisticCount


  override val toString: Value = {
    val probabilisticOutputCountString =
      problem
        .outputValues
        .toList
        .sorted
        .map(outputValue =>
          s"Probabilistic #(${problem.outputAttribute} = ${outputValue}) = ${getProbabilisticCount(outputValue)}"
        )
        .mkString("\n")

    s"${probabilisticOutputCountString}\n\n" +
      s"\n${problem.outputAttribute} = ${outputLabel}\n\n" +
      s"Probabilistic correct # = ${probabilisticCorrectCount}\n" +
      s"Probabilistic wrong # = ${probabilisticWrongCount}\n" +
      s"Total probabilistic # = ${totalProbabilisticCount}"
  }
}
