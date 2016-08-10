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
  * Internal DecisionTree implementation
  *
  * @param problem
  * @param attribute
  * @param leaves
  */
private case class DefaultDecisionTree(
                                        problem: ClassificationProblem,
                                        attribute: Attribute,
                                        leaves: Map[Value, DecisionTreeLeaf]
                                      ) extends DecisionTree {

  override def classify(attributeValue: Value): Map[Value, Double] = {
    attributeValue match {
      case ClassificationProblem.UnknownValue =>
        problem.outputValues.map((outputValue) => {
          val weightedOutputProbability: Double =
            leaves.foldLeft(0.0) {
              case (cumulatedOutputProbability, (_, leaf)) =>
                cumulatedOutputProbability +
                  leaf.weight * leaf.getOutputProbability(outputValue)
            }

          outputValue -> weightedOutputProbability
        })
          .toMap


      case value: Value =>
        val leafOption =
          leaves.get(value)

        leafOption match {
          case Some(leaf) =>
            problem.outputValues.map((outputValue) =>
              outputValue ->
                leaf.getOutputProbability(outputValue)
            ).toMap

          case None =>
            problem.outputValues.map((outputValue) =>
              outputValue -> 0.0
            ).toMap
        }

    }
  }
}
