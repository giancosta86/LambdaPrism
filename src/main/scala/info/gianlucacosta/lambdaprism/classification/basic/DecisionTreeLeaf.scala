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
  * Leaf of a basic decision tree
  */
trait DecisionTreeLeaf {
  /**
    * The original problem
    *
    * @return
    */
  def problem: ClassificationProblem

  /**
    * The attribute on which the tree (and therefore the leaf) is based
    *
    * @return
    */
  def attributeValue: Value

  /**
    * The weight (in range [0; 1]) of the leaf
    *
    * @return
    */
  def weight: Double

  /**
    * The output label assigned to a leaf is the output value
    * appearing in the probabilistic majority of the items within the leaf
    */
  def outputLabel: Value

  /**
    * Returns the <i>probabilistic count</i> (that is, considering <i>unknown items</i> and <i>leaf weight</i>)
    * for the given output value.
    *
    * @param outputValue
    * @return The probabilistic # of items, or 0 if a missing output value was provided
    */
  def getProbabilisticCount(outputValue: Value): Double


  /**
    * The total probabilistic # of items in the leaf
    */
  def totalProbabilisticCount: Double


  /**
    * The probabilistic # of items whose output value matches the leaf's
    * output label
    */
  def probabilisticCorrectCount: Double


  /**
    * The probabilistic # of items whose output value does not match the
    * leaf's output label
    */
  def probabilisticWrongCount: Double


  /**
    * The probability of an output value for this leaf -
    * that is, the probabilistic count of such value over the total probabilistic count.
    *
    * @param outputValue
    * @return The probability (in range [0; 1]) or 0 if the output value is unknown
    */
  def getOutputProbability(outputValue: Value): Double
}
